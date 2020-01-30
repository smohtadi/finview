package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.Proveedor;
import com.smohtadi.finView.services.ProveedorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import utils.Message;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static utils.Validator.isValidText;

public class EditProveedorController implements Initializable {
  @FXML private TextField editarNombreTextField;
  @FXML private TextField editRucTextField;
  @FXML private TextField buscarNombreTextField;
  @FXML private TableView<Proveedor> proveedorTableView;
  private Proveedor proveedorSelected = null;

  public void onClickBuscarNombre(ActionEvent actionEvent) {
      List<Proveedor> proveedores = ProveedorService.getInstance().fetchProveedorByNombre(buscarNombreTextField.getText());
      if (proveedores != null)
        proveedorTableView.getItems().setAll(proveedores);
  }

  public void onClickEditar(ActionEvent actionEvent) {
    if (proveedorSelected != null && isValidText(editarNombreTextField.getText()) && isValidText(editRucTextField.getText())) {
      String response;
      proveedorSelected.setNombre(editarNombreTextField.getText());
      proveedorSelected.setRuc(editRucTextField.getText());
      response = ProveedorService.getInstance().updateProveedorById(proveedorSelected);
      new Alert(Alert.AlertType.INFORMATION, response).show();
    } else {
      new Alert(Alert.AlertType.ERROR, Message.INVALID_INPUT).show();
    }
  }

  public void onClickTable(MouseEvent mouseEvent) {
    proveedorSelected = proveedorTableView.getSelectionModel().getSelectedItem();
    if (proveedorSelected != null) {
      editarNombreTextField.setText(proveedorSelected.getNombre());
      editRucTextField.setText(proveedorSelected.getRuc());
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String[] colNames = { "Id", "Nombre", "RUC" };
    String[] propNames = { "proveedorId", "nombre", "ruc" };
    for (int i = 0; i < colNames.length; i++) {
      TableColumn<Proveedor, String> x = new TableColumn<Proveedor, String>(colNames[i]);
      x.setCellValueFactory(new PropertyValueFactory<Proveedor, String>(propNames[i]));
      proveedorTableView.getColumns().add(x);
    }
  }
}
