package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.Producto;
import com.smohtadi.finView.services.ProductoService;
import com.smohtadi.finView.services.ProveedorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utils.ComboBoxAutoComplete;
import utils.Message;
import java.net.URL;
import java.util.ResourceBundle;
import static utils.Validator.isANumber;
import static utils.Validator.isValidText;

public class CreateProductController implements Initializable {

  @FXML private AnchorPane parentAnchorPane;
  @FXML private TextField productoIdTextField;
  @FXML private TextField descripcionTextField;
  @FXML private TextField precioTextField;
  @FXML private TextField grupoTextField;
  private ComboBoxAutoComplete proveedorIdComboBox;

  public void onClickSave(ActionEvent actionEvent) {
    if (isValidText(productoIdTextField.getText()) && isValidText(descripcionTextField.getText()) && isValidText(grupoTextField.getText()) && isANumber(precioTextField.getText()) && isValidText(proveedorIdComboBox.getValue())) {
      String response = ProductoService.getInstance()
              .createProducto(new Producto(productoIdTextField.getText(),
                      descripcionTextField.getText(),
                      Double.parseDouble(precioTextField.getText()),
                      grupoTextField.getText(),
                      proveedorIdComboBox.getValue()
              ));
      new Alert(Alert.AlertType.CONFIRMATION, response).show();
    } else {
      new Alert(Alert.AlertType.ERROR, Message.INVALID_INPUT).show();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    proveedorIdComboBox = new ComboBoxAutoComplete(ProveedorService.getInstance());
    proveedorIdComboBox.setLayout(parentAnchorPane, 148, 255);
    AnchorPane.setLeftAnchor(proveedorIdComboBox, 148.0);
    AnchorPane.setRightAnchor(proveedorIdComboBox, 10.0);
  }
}
