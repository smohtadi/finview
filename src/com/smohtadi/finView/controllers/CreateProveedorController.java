package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.Proveedor;
import com.smohtadi.finView.services.ProveedorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import utils.Message;

import static utils.Validator.isValidText;

public class CreateProveedorController {

  @FXML private TextField proveedorIdTextField;
  @FXML private TextField nombreTextField;
  @FXML private TextField rucTextField;

  public void onClickSave(ActionEvent actionEvent) {
    if (isValidText(proveedorIdTextField.getText()) && isValidText(nombreTextField.getText()) && isValidText(rucTextField.getText())) {
      String response = ProveedorService.getInstance().createProveedor(new Proveedor(proveedorIdTextField.getText().toUpperCase(), nombreTextField.getText(), rucTextField.getText()));
      new Alert(Alert.AlertType.CONFIRMATION, response).show();
    } else {
      new Alert(Alert.AlertType.ERROR, Message.INVALID_INPUT);
    }
  }
}
