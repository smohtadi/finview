package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.Producto;
import com.smohtadi.finView.services.ProductoService;
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

public class EditProductoController implements Initializable {
  @FXML private TextField editarDescripcionTextField;
  @FXML private TextField editarGrupoTextField;
  @FXML private TextField editarProveedorIdTextField;
  @FXML private TextField buscarNombreTextField;
  @FXML private TableView<Producto> productoTableView;
  private Producto productoSelected = null;

  public void onClickBuscarNombre(ActionEvent actionEvent) {
    List<Producto> productos = ProductoService.getInstance().fetchProductos(buscarNombreTextField.getText());
    if (productos != null) productoTableView.getItems().setAll(productos);
  }

  public void onClickEditar(ActionEvent actionEvent) {
    if (productoSelected != null && isValidText(editarDescripcionTextField.getText())) {
      String response;
      productoSelected.setDescripcion(editarDescripcionTextField.getText());
      if (editarGrupoTextField != null) productoSelected.setGrupo(editarGrupoTextField.getText());
      if (editarProveedorIdTextField != null) productoSelected.setProveedorId(editarProveedorIdTextField.getText());
      response = ProductoService.getInstance().updateProducto(productoSelected);
      new Alert(Alert.AlertType.INFORMATION, response).show();
    } else {
      new Alert(Alert.AlertType.ERROR, Message.INVALID_INPUT).show();
    }
  }

  public void onClickTable(MouseEvent mouseEvent) {
      productoSelected = productoTableView.getSelectionModel().getSelectedItem();
      if (productoSelected != null) {
        editarDescripcionTextField.setText(productoSelected.getDescripcion());
        editarGrupoTextField.setText(productoSelected.getGrupo());
        editarProveedorIdTextField.setText(productoSelected.getProveedorId());
      }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String[] colNames = { "Id", "Descripcion", "Grupo", "ProveedorID", "Precio" };
    String[] propNames = { "productoId", "descripcion", "grupo", "proveedorId", "precio" };
    for (int i = 0; i < colNames.length; i++) {
        TableColumn<Producto, String> x = new TableColumn<Producto, String>(colNames[i]);
        x.setCellValueFactory(new PropertyValueFactory<Producto, String>(propNames[i]));
        productoTableView.getColumns().add(x);
    }
  }
}
