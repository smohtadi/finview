package com.smohtadi.finView;

import com.smohtadi.finView.model.Store;
import com.smohtadi.finView.services.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import utils.Message;
import utils.WindowName;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
  @FXML private AnchorPane mainCenterPane;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
    primaryStage.setTitle("FinView");
    Scene mainScene = new Scene(root, 700, 700);
    primaryStage.setScene(mainScene);
    mainScene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
    primaryStage.show();
    if (!ProveedorService.getInstance().createTable().equals(Message.SUCCESS) ||
            !ProductoService.getInstance().createTable().equals(Message.SUCCESS) ||
            !FacturaService.getInstance().createTable().equals(Message.SUCCESS) ||
            !FacturaItemService.getInstance().createTable().equals(Message.SUCCESS) ||
            !CreditoService.getInstance().createTable().equals(Message.SUCCESS))
      new Alert(Alert.AlertType.ERROR, "Failed to create database tables").show();
  }

  private void anchorChildToAnchorParent(Node child) {
    AnchorPane.setTopAnchor(child, 0.0);
    AnchorPane.setRightAnchor(child, 0.0);
    AnchorPane.setBottomAnchor(child, 0.0);
    AnchorPane.setLeftAnchor(child, 0.0);
  }

  private void loadNodeInAnchorParent(AnchorPane parent, String viewPath) {
    parent.getChildren().clear();
    try {
      Node child = FXMLLoader.load(getClass().getResource(viewPath));
      anchorChildToAnchorParent(child);
      parent.getChildren().setAll(child);
    } catch (IOException e) {
      new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
  }

  public void onClickInicio(ActionEvent actionEvent) {
    loadNodeInAnchorParent(mainCenterPane, "views/inicio.fxml");
  }

  public void onClickCreateProduct(ActionEvent actionEvent) {
    loadNodeInAnchorParent(mainCenterPane, "views/createProduct.fxml");
  }

  public void onClickCreateFactura(ActionEvent actionEvent) {
    Store.getInstance().setWindowName(WindowName.CREAR_FACTURA);
    loadNodeInAnchorParent(mainCenterPane, "views/factura.fxml");
  }

  public void onClickCreateProveedor(ActionEvent actionEvent) {
    loadNodeInAnchorParent(mainCenterPane, "views/createProveedor.fxml");
  }

  public void onClickInformeCompras(ActionEvent actionEvent) {
    Store.getInstance().setWindowName(WindowName.INFORME_COMPRA);
    loadNodeInAnchorParent(mainCenterPane, "views/informe.fxml");
  }

  public void onClickInformeCredito(ActionEvent actionEvent) {
    Store.getInstance().setWindowName(WindowName.INFORME_CREDIO);
    loadNodeInAnchorParent(mainCenterPane, "views/informe.fxml");
  }

  public void onClickEditProveedor(ActionEvent actionEvent) {
    loadNodeInAnchorParent(mainCenterPane, "views/editProveedor.fxml");
  }

  public void onClickEditProducto(ActionEvent actionEvent) {
    loadNodeInAnchorParent(mainCenterPane, "views/editProducto.fxml");
  }

  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadNodeInAnchorParent(mainCenterPane, "views/inicio.fxml");
  }

  public void onClickClose(ActionEvent actionEvent) {
    System.exit(0);
  }
}
