package com.smohtadi.finView.controllers;
import com.smohtadi.finView.model.Informe;
import com.smohtadi.finView.model.Store;
import com.smohtadi.finView.services.InformeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.DateFormatter;
import utils.WindowName;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InicioController implements Initializable {
  @FXML private TableView<Informe> pagosVencerTableView;

  public void onClickDetalles(ActionEvent mouseEvent) {
    try {
      Informe informe = pagosVencerTableView.getSelectionModel().getSelectedItem();
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String[] colNames  = { "Fecha", "Fecha Vcto", "Fecha Pago", "facturaId", "Modo", "proveedorId", "noControl", "Importe", "Saldo" };
    String[] propNames = { "fecha", "fechaVcto", "fechaPago", "facturaId", "modo", "proveedorId", "noControl", "total", "saldo" };
    for (int i = 0; i < colNames.length; i++) {
      TableColumn<Informe,String> x = new TableColumn<Informe,String>(colNames[i]);
      x.setCellValueFactory(new PropertyValueFactory<Informe,String>(propNames[i]));
      pagosVencerTableView.getColumns().add(x);
    }
    String curDate = DateFormatter.getCurrentDay();
    String nexDate = DateFormatter.addDays(curDate, 15);
    List<Informe> informes = InformeService.getInstance().fetchCredito("01/01/2010", nexDate, "");
    if(informes != null) pagosVencerTableView.getItems().setAll(informes);
  }
}
