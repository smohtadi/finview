package com.smohtadi.finView.dao;
import com.smohtadi.finView.model.Informe;
import com.smohtadi.finView.model.ServerResponse;
import utils.DateFormatter;
import utils.Message;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class InformeDao extends Dao {
  public ServerResponse<Integer, List<Informe>>  findCompras(String desde, String hasta, String proveedorId) {
    List<Informe> informe = null;
    try {
    Connection conn = dbManager.getConnection();
    String sql = "SELECT * FROM factura WHERE proveedorId LIKE ? AND fecha BETWEEN ? AND ?";
    String inicial = DateFormatter.format(desde, "dd/MM/yyyy", "yyyy-MM-dd");
    String fin = DateFormatter.format(hasta, "dd/MM/yyyy", "yyyy-MM-dd");
    PreparedStatement preparedStatement = conn.prepareStatement(sql);
    preparedStatement.setString(1, "%"+proveedorId+"%");
    preparedStatement.setString(2, inicial);
    preparedStatement.setString(3, fin);
    ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        informe = new ArrayList<>();
        do {
          informe.add(new Informe(resultSet.getString("fecha"), resultSet.getInt("facturaId"), resultSet.getString("modo"), resultSet.getString("proveedorId"), resultSet.getString("noControl"), resultSet.getDouble("total"), resultSet.getDouble("saldo")));
        } while (resultSet.next());
      }
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, List<Informe>>(400, null, e.getMessage());
    }
    return informe != null ? new ServerResponse<Integer, List<Informe>>(200, informe, Message.SUCCESS) :
            new ServerResponse<Integer, List<Informe>>(400, null, "Not found");
  }


  public ServerResponse<Integer, List<Informe>> findCredito(String desde, String hasta, String proveedorId) {
    List<Informe> informe = null;
    try {
      Connection conn = dbManager.getConnection();
      String sql = "SELECT f.fecha, c.fechaVcto, c.fechaPago, f.facturaId, f.modo, f.proveedorId, f.noControl, f.total, f.saldo FROM factura f, credito c WHERE f.facturaId = c.facturaId AND f.proveedorId LIKE ? AND c.fechaVcto BETWEEN ? AND ? AND c.fechaPago IS NULL";
      String inicial = DateFormatter.format(desde, "dd/MM/yyyy", "yyyy-MM-dd");
      String fin = DateFormatter.format(hasta, "dd/MM/yyyy", "yyyy-MM-dd");
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, "%"+proveedorId+"%");
      preparedStatement.setString(2, inicial);
      preparedStatement.setString(3, fin);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        informe = new ArrayList<>();
        do {
          informe.add(new Informe(resultSet.getString("fecha"), resultSet.getString("fechaVcto"), resultSet.getString("fechaPago"), resultSet.getInt("facturaId"), resultSet.getString("modo"), resultSet.getString("proveedorId"), resultSet.getString("noControl"), resultSet.getDouble("total"), resultSet.getDouble("saldo")));
        } while (resultSet.next());
      }
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, List<Informe>>(400, null, e.getMessage());
    }
    return informe != null ? new ServerResponse<Integer, List<Informe>>(200, informe, Message.SUCCESS) :
            new ServerResponse<Integer, List<Informe>>(400, null, "Not found");
  }
}
