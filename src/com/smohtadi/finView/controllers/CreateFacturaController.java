package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.*;
import com.smohtadi.finView.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import utils.ComboBoxAutoComplete;
import utils.DateFormatter;
import utils.WindowName;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static utils.Validator.*;

public class CreateFacturaController implements Initializable {
  @FXML private AnchorPane cuotasAnchorPane;
  @FXML private AnchorPane parentAnchorPane;
  @FXML private ComboBox<String> modoPagoComboBox;
  private ComboBoxAutoComplete proveedorIdComboBox;
  private ComboBoxAutoComplete productoIdComboBox;
  private ContextMenu itemsContextMenu;
  private ContextMenu creditoContextMenu;
  @FXML private Label nombreProveedorLabel;
  @FXML private Label rucProveedorLabel;
  @FXML private Label sumaLabel;
  @FXML private Label descuentoLabel;
  @FXML private Label totalLabel;
  @FXML private Label saldoLabel;
  @FXML private Label productoDescripcionLabel;
  @FXML private Label facturaIdLabel;
  @FXML private TextField creditoDiasTextField;
  @FXML private TextField descuentoTextField;
  @FXML private TextField productoCantidadTextField;
  @FXML private TextField productoPrecioTextField;
  @FXML private TextField fechaTextField;
  @FXML private TextField cuotaFechaPagoTextField;
  @FXML private TextField cuotaFechaVctoTextField;
  @FXML private TextField cuotaMontoTextField;
  @FXML private TextField noControlTextField;
  @FXML private TableView<FacturaItem> itemsTableView;
  @FXML private TableView<Credito> cuotaTableView;

  private void createContextMenuItemsTable() {
    itemsContextMenu = new ContextMenu();
    MenuItem eliminarMI = new MenuItem("Elminar");
    eliminarMI.setOnAction((ActionEvent actionEvent) -> {
      FacturaItem facturaItem = itemsTableView.getSelectionModel().getSelectedItem();
      FacturaItemService.getInstance().deleteItemById(facturaItem.getFacturaItemId());
      List<FacturaItem> facturaItems = FacturaItemService.getInstance().fetchFacturaItemsByFid(facturaItem.getFacturaId());
      if (facturaItems == null)
        itemsTableView.getItems().setAll(new ArrayList<>());
      else
        itemsTableView.getItems().setAll(facturaItems);
      sumaLabel.setText(Double.toString(sumOfItems()));
    });
    itemsContextMenu.getItems().add(eliminarMI);
  }

  private void createContextMenuCreditoTable() {
    creditoContextMenu = new ContextMenu();
    MenuItem eliminarMI = new MenuItem("Elminar");
    eliminarMI.setOnAction((ActionEvent actionEvent) -> {
      Credito credito = cuotaTableView.getSelectionModel().getSelectedItem();
      CreditoService.getInstance().deleteById(credito.getCreditoId());
      List<Credito> creditos = CreditoService.getInstance().fetchCreditoByFid(credito.getFacturaId());
      if (creditos == null)
        cuotaTableView.getItems().setAll(new ArrayList<>());
      else
        cuotaTableView.getItems().setAll(creditos);
      saldoLabel.setText(Double.toString(getSaldo()));
    });
    creditoContextMenu.getItems().add(eliminarMI);
  }

  private double sumOfItems() {
    if (itemsTableView.getItems() == null) return 0;
    double sum = 0;
    for (FacturaItem facturaItem : itemsTableView.getItems()) {
      sum += (facturaItem.getCantidad() * facturaItem.getPrecio());
    }
    return sum;
  }

  private void createAutoCompleteCBs() {
    proveedorIdComboBox = new ComboBoxAutoComplete(ProveedorService.getInstance());
    productoIdComboBox = new ComboBoxAutoComplete(ProductoService.getInstance());
    proveedorIdComboBox.setLayout(parentAnchorPane,20, 167);
    proveedorIdComboBox.setPromptText("Proveedor Id");
    AnchorPane.setLeftAnchor(proveedorIdComboBox,10.0);
    productoIdComboBox.setLayout(parentAnchorPane, 10, 488);
    productoIdComboBox.setPromptText("Producto Id");
    productoIdComboBox.setPrefWidth(85);
    AnchorPane.setLeftAnchor(productoIdComboBox, 10.0);
  }

  private double getSaldo() {
    if (cuotaTableView.getItems() == null) return Double.parseDouble(totalLabel.getText());
    double sum = 0;
    for (Credito credito : cuotaTableView.getItems()) {
      if (credito.getFechaPago() != null && !credito.getFechaPago().equals(""))
        sum += credito.getMonto();
    }
    return Double.parseDouble(totalLabel.getText()) - sum;
  }

  private Credito findCreditoInTable(String fvcto, List<Credito> creditos) {
    for (Credito credito : creditos) {
      if (credito.getFechaVcto().equals(fvcto)) return credito;
    }
    return null;
  }

  private void fetchFacturaIdLabel() {
    Integer fid = FacturaService.getInstance().fetchLastRowId();
    fid = fid == null ? 1 : fid + 1;
    facturaIdLabel.setText(fid.toString());
  }

  public void onClickSaveFactura(ActionEvent actionEvent) {
    if (modoPagoComboBox.getValue().equals("Credito") && (cuotaTableView.getItems() == null || cuotaTableView.getItems().size() == 0)) {
      new Alert(Alert.AlertType.ERROR, "Complete la tabla de credito").show();
    } else if (!isDateValid(fechaTextField.getText())) {
      new Alert(Alert.AlertType.ERROR, "Fecha de la factura invalida. Use el formato dd/mm/yyyy").show();
    } else if (ProveedorService.getInstance().fetchProveedor(proveedorIdComboBox.getValue()) == null) {
      new Alert(Alert.AlertType.ERROR, "Proveedor Id invalido.").show();
    } else if (!isValidText(noControlTextField.getText())) {
      new Alert(Alert.AlertType.ERROR, "No. de Control invalido").show();
    } else {
      Factura factura = new Factura(Integer.parseInt(facturaIdLabel.getText()),
              Double.parseDouble(descuentoLabel.getText()), Double.parseDouble(totalLabel.getText()),
              Double.parseDouble(saldoLabel.getText()), fechaTextField.getText(),
              modoPagoComboBox.getValue(), noControlTextField.getText(), proveedorIdComboBox.getValue());
      String response;
      if (FacturaService.getInstance().fetchFacturaById(factura.getFacturaId()) == null) {
        response = FacturaService.getInstance().createFactura(factura);
      } else {
        response = FacturaService.getInstance().updateFactura(factura);
      }
      populateFields();
      new Alert(Alert.AlertType.INFORMATION, response).show();
    }
  }

  public void onClickTableDetails(MouseEvent mouseEvent) {
    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      itemsContextMenu.show(itemsTableView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }
  }

  public void onClickCreditoMenu(MouseEvent mouseEvent) {
    if (mouseEvent.getButton() == MouseButton.SECONDARY) {
      creditoContextMenu.show(cuotaTableView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }
  }

  public void onClickSetProduct(ActionEvent actionEvent) {
    String pid = productoIdComboBox.getValue();
    String cantidadTxt = productoCantidadTextField.getText();
    String precioTxt = productoPrecioTextField.getText();
    if (!isANumber(cantidadTxt) || !isANumber(precioTxt)) {
      new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
    } else {
      Producto p = ProductoService.getInstance().fetchProveedor(pid);
      if (p == null) {
        new Alert(Alert.AlertType.ERROR, "Invalid Product ID").show();
      } else {
        FacturaItem facturaItem = itemsSearch(itemsTableView.getItems(), pid);
        if (facturaItem == null) {
          facturaItem = new FacturaItem(Integer.parseInt(facturaIdLabel.getText()), p.getProductoId(), Integer.parseInt(cantidadTxt), Double.parseDouble(precioTxt), p.getDescripcion());
          FacturaItemService.getInstance().createFacturaItem(facturaItem);
        } else {
          facturaItem.setCantidad(Integer.parseInt(cantidadTxt));
          facturaItem.setPrecio(Double.parseDouble(precioTxt));
          FacturaItemService.getInstance().updateFactura(facturaItem);
        }
        List<FacturaItem> facturaItems = FacturaItemService.getInstance().fetchFacturaItemsByFid(Integer.parseInt(facturaIdLabel.getText()));
        if (facturaItems == null)
          sumaLabel.setText("0");
        else {
          itemsTableView.getItems().setAll(facturaItems);
          sumaLabel.setText(Double.toString(sumOfItems()));
        }
      }
    }
  }

  private FacturaItem itemsSearch(List<FacturaItem> facturaItems, String pid) {
    if (facturaItems == null) return null;
    for (FacturaItem facturaItem : facturaItems) {
      if (facturaItem.getProductoId().equals(pid)) return facturaItem;
    }
    return null;
  }

  private void toggleFields () {
    switch (modoPagoComboBox.getValue()) {
      case "Contado":
        creditoDiasTextField.setDisable(true);
        cuotasAnchorPane.setVisible(false);
        break;
      case "Credito":
        creditoDiasTextField.setDisable(false);
        cuotasAnchorPane.setVisible(true);
        break;
      case "Cuotas":
        creditoDiasTextField.setDisable(true);
        cuotasAnchorPane.setVisible(true);
        break;
    }
  }

  public void onClickModoPago(ActionEvent actionEvent) {
    toggleFields();
  }

  public void onClickAddCuota(ActionEvent actionEvent) {
    String montoText = cuotaMontoTextField.getText();
    String fechaPago = cuotaFechaPagoTextField.getText().trim();
    String fechaVcto = cuotaFechaVctoTextField.getText();
    if ((isANumber(montoText) || montoText.endsWith("%") && isANumber(montoText.substring(0, montoText.length() - 1)))  &&
            isDateValid(fechaVcto) && (fechaPago.length() == 0 || isDateValid(fechaPago))) {
      Credito credito = findCreditoInTable(fechaVcto, cuotaTableView.getItems());
      if (fechaPago.length() == 0) fechaPago = null;
      double monto = 0;
      if (montoText.endsWith("%")) {
        monto = Double.parseDouble(totalLabel.getText()) * Double.parseDouble(montoText.substring(0, montoText.length() - 1)) / 100;
      } else {
        monto = Double.parseDouble(cuotaMontoTextField.getText());
      }
      if (credito == null) {
        credito = new Credito(Integer.parseInt(facturaIdLabel.getText()), cuotaFechaVctoTextField.getText(), fechaPago, monto);
        CreditoService.getInstance().createCredito(credito);
      } else {
        credito.setFechaPago(fechaPago);
        credito.setMonto(monto);
        CreditoService.getInstance().updateCredito(credito);
      }
      List<Credito> creditos = CreditoService.getInstance().fetchCreditoByFid(credito.getFacturaId());
      if (creditos == null)
        saldoLabel.setText(totalLabel.getText());
      else {
        cuotaTableView.getItems().setAll(creditos);
        saldoLabel.setText(Double.toString(getSaldo()));
      }
    } else {
      new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
    }
  }

  private void descuentoListener(String newText) {
    int n = newText.length();
    if (n == 0) {
      descuentoLabel.setText("0");
      return;
    }
    String str = (newText.endsWith("%")) ? newText.substring(0, n - 1) : newText;
    if (isANumber(str)) {
      double d = Double.parseDouble(str);
      double dsc = (newText.charAt(n - 1) == '%') ? Double.parseDouble(sumaLabel.getText()) * (d / 100) : Double.parseDouble(newText);
      descuentoLabel.setText(Double.toString(dsc));
    } else {
      descuentoLabel.setText("0");
    }
  }

  private void populateFields() {
    Factura factura = FacturaService.getInstance().fetchFacturaById(Store.getInstance().getFacturaId());
    if (factura != null) {
      facturaIdLabel.setText(Integer.toString(factura.getFacturaId()));
      fechaTextField.setText(factura.getFecha());
      noControlTextField.setText(factura.getNoControl());
      proveedorIdComboBox.setValue(factura.getProveedorId());
      modoPagoComboBox.setValue(factura.getModo());
      toggleFields();
      descuentoTextField.setText(Double.toString(factura.getDescuento()));
      descuentoLabel.setText(Double.toString(factura.getDescuento()));
      double a = factura.getTotal() + factura.getDescuento();
      sumaLabel.setText(Double.toString(a));
      saldoLabel.setText(Double.toString(factura.getSaldo()));
      List<FacturaItem> facturaItems = FacturaItemService.getInstance().fetchFacturaItemsByFid(factura.getFacturaId());
      if (facturaItems != null) {
        itemsTableView.getItems().setAll(facturaItems);
      }
      if (!factura.getModo().equals("Contado")) {
        List<Credito> creditos = CreditoService.getInstance().fetchCreditoByFid(factura.getFacturaId());
        if (creditos != null) {
          cuotaTableView.getItems().setAll(creditos);
          if (factura.getModo().equals("Credito") && creditos.size() > 0) {
            String dias = DateFormatter.subtract(creditos.get(0).getFechaVcto(), factura.getFecha());
            creditoDiasTextField.setText(dias);
          }
        }
      }
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    createContextMenuItemsTable();
    createContextMenuCreditoTable();
    createAutoCompleteCBs();
    /* Add Listeners*/
    productoIdComboBox.setOnAction((ActionEvent event) -> {
      String value = productoIdComboBox.getValue();
      String descripcion = "", precio = "", cantidad = "0";
      if (value.length() > 0) {
        Producto p = ProductoService.getInstance().fetchProveedor(value);
        if (p != null) {
          descripcion = p.getDescripcion();
          precio = Double.toString(p.getPrecio());
          cantidad = Integer.toString(p.getCantidad());
        }
      }
      productoDescripcionLabel.setText(descripcion);
      productoPrecioTextField.setText(precio);
      productoCantidadTextField.setText(cantidad);
    });
    proveedorIdComboBox.setOnAction((ActionEvent event) -> {
      String value = proveedorIdComboBox.getValue();
      String nombre = "", ruc = "";
      if (value.length() > 0) {
        Proveedor p = ProveedorService.getInstance().fetchProveedor(value);
        if (p != null) {
          nombre = p.getNombre();
          ruc = p.getRuc();
        }
      }
      nombreProveedorLabel.setText(nombre);
      rucProveedorLabel.setText(ruc);
    });
    descuentoTextField.textProperty().addListener((observable, oldValue, newValue) -> descuentoListener(newValue));
    descuentoLabel.textProperty().addListener((obs, oldText, newText) -> totalLabel.setText(Double.toString(Double.parseDouble(sumaLabel.getText()) - Double.parseDouble(descuentoLabel.getText()))));
    sumaLabel.textProperty().addListener((obs, oldText, newText) -> {
      totalLabel.setText(Double.toString(Double.parseDouble(sumaLabel.getText()) - Double.parseDouble(descuentoLabel.getText())));
      descuentoListener(descuentoTextField.getText());
    });
    creditoDiasTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if(modoPagoComboBox.getValue().equals("Credito") && isANumber(newValue)) {
        cuotaFechaVctoTextField.setText(DateFormatter.addDays(fechaTextField.getText(), Integer.parseInt(newValue)));

      }
    });
    if (Store.getInstance().getWindowName().equals(WindowName.CREAR_FACTURA)) {
      fechaTextField.setText(DateFormatter.getCurrentDay());
      fetchFacturaIdLabel();
    } else {
      populateFields();
    }
  }
}
