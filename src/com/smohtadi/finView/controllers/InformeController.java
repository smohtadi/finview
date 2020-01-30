package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.Informe;
import com.smohtadi.finView.model.Store;
import com.smohtadi.finView.services.FacturaService;
import com.smohtadi.finView.services.InformeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Message;
import utils.WindowName;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Validator.isDateValid;
import static utils.Validator.isValidText;

public class InformeController implements Initializable {
  @FXML private Label titleLabel;
  @FXML private TextField fechaInicialTextField;
  @FXML private TextField fechaFinalTextField;
  @FXML private TextField proveedorIdTextField;
  @FXML private TableView<Informe> informeTableView;

  public void onClickTableCompras(MouseEvent mouseEvent) { }

  public void onClickDetalles(ActionEvent mouseEvent) {
      try {
        Informe informe = informeTableView.getSelectionModel().getSelectedItem();
        if (informe == null) return;
        Store.getInstance().setWindowName(WindowName.EDITAR_FACTURA);
        Store.getInstance().setFacturaId(informe.getFacturaId());
        Parent root1 = FXMLLoader.load(getClass().getResource("../views/factura.fxml"));
        Scene secScene = new Scene(root1, 700, 800);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("FinView - Factura");
        stage.setScene(secScene);
        stage.show();
      } catch (IOException e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage());
      }
  }

  public void onClickBuscar(ActionEvent actionEvent) {
    if (isDateValid(fechaInicialTextField.getText()) && isDateValid(fechaFinalTextField.getText())) {
      List<Informe> informe;
      if (Store.getInstance().getWindowName().equals(WindowName.INFORME_COMPRA))
        informe = InformeService.getInstance().fetchCompras(fechaInicialTextField.getText(), fechaFinalTextField.getText(), proveedorIdTextField.getText());
      else
        informe = InformeService.getInstance().fetchCredito(fechaInicialTextField.getText(), fechaFinalTextField.getText(), proveedorIdTextField.getText());
      if (informe != null)
        informeTableView.getItems().setAll(informe);
    } else {
      new Alert(Alert.AlertType.ERROR, Message.INVALID_INPUT).show();
    }
  }

  public void onClickEliminar(ActionEvent actionEvent) {
    Informe informe = informeTableView.getSelectionModel().getSelectedItem();
    if (informe != null) {
      String res = FacturaService.getInstance().deleteFactura(informe.getFacturaId());
      new Alert(Alert.AlertType.WARNING, res).show();
    }
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    titleLabel.setText(Store.getInstance().getWindowName());
    List<String> colNames = null;
    List<String> propNames = null;
    if (Store.getInstance().getWindowName().equals(WindowName.INFORME_COMPRA)) {
      // Create table;
      colNames = Arrays.asList("Fecha", "facturaId", "Modo", "proveedorId", "noControl", "Importe", "Saldo");
      propNames = Arrays.asList("fecha", "facturaId", "modo", "proveedorId", "noControl", "total", "saldo");
    } else {
      colNames  = Arrays.asList("Fecha", "Fecha Vcto", "Fecha Pago", "facturaId", "Modo", "proveedorId", "noControl", "Importe", "Saldo");
      propNames = Arrays.asList("fecha", "fechaVcto", "fechaPago", "facturaId", "modo", "proveedorId", "noControl", "total", "saldo");
    }
    for (int i = 0; i < colNames.size(); i++) {
      TableColumn<Informe,String> x = new TableColumn<Informe,String>(colNames.get(i));
      x.setCellValueFactory(new PropertyValueFactory<Informe,String>(propNames.get(i)));
      informeTableView.getColumns().add(x);
    }
  }
}
