package com.smohtadi.finView.dao;
import com.smohtadi.finView.model.Factura;
import com.smohtadi.finView.model.ServerResponse;
import utils.DateFormatter;
import utils.Message;
import java.sql.*;
import java.text.ParseException;

public class FacturaDao extends Dao {

  public ServerResponse<Integer, String> createTable() {
    String sql =
            "CREATE TABLE IF NOT EXISTS factura (facturaId INTEGER PRIMARY KEY," +
                    "descuento REAL, total REAL, saldo REAL," +
                    "fecha TEXT, modo TEXT, noControl TEXT, proveedorId TEXT," +
                    "FOREIGN KEY(proveedorId) REFERENCES proveedor(proveedorId) ON DELETE SET NULL)";
    return super.createTable(sql);
  }

  public ServerResponse<Integer, Integer> update(Factura factura) {
    try {
      String sql = "UPDATE factura SET descuento=?, total=?, fecha=?, modo=?, saldo=?, noControl=?, proveedorId = ? WHERE facturaId=?";
      factura.setFecha(DateFormatter.format(factura.getFecha(), "dd/MM/yyyy", "yyyy-MM-dd"));
      Connection connection = dbManager.getConnection();
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setDouble(1, factura.getDescuento());
      ps.setDouble(2, factura.getTotal());
      ps.setString(3, factura.getFecha());
      ps.setString(4, factura.getModo());
      ps.setDouble(5, factura.getSaldo());
      ps.setString(6, factura.getNoControl());
      ps.setString(7, factura.getProveedorId());
      ps.setDouble(8, factura.getFacturaId());
      ps.executeUpdate();
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, Integer>(400, 0, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, 0, Message.SUCCESS);
  }

  public ServerResponse<Integer, Integer> create(Factura factura) {
    int rowNum = 0;
    try {
      Connection connection = dbManager.getConnection();
      String sql = "INSERT INTO factura (facturaId, descuento, total, fecha, modo, saldo, noControl, proveedorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      factura.setFecha(DateFormatter.format(factura.getFecha(), "dd/MM/yyyy", "yyyy-MM-dd"));
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, factura.getFacturaId());
      ps.setDouble(2, factura.getDescuento());
      ps.setDouble(3, factura.getTotal());
      ps.setString(4, factura.getFecha());
      ps.setString(5, factura.getModo());
      ps.setDouble(6, factura.getSaldo());
      ps.setString(7, factura.getNoControl());
      ps.setString(8, factura.getProveedorId());
      rowNum = ps.executeUpdate();
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, Integer>(400, rowNum, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, rowNum, Message.SUCCESS);
  }

  public ServerResponse<Integer, Integer> deleteById(int facturaId) {
    try {
      Connection conn = dbManager.getConnection();
      String sql = "DELETE FROM factura WHERE facturaId=?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, facturaId);
      ps.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, 0, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, 0, Message.SUCCESS);
  }

  public ServerResponse<Integer, Factura> findById(int facturaId) {
    Factura factura = null;
    try {
      Connection conn = dbManager.getConnection();
      String sql = "SELECT * FROM factura WHERE facturaId = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, facturaId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String fecha = DateFormatter.format(rs.getString("fecha"), "yyyy-MM-dd", "dd/MM/yyyy");
        factura = new Factura(rs.getInt("facturaId"), rs.getDouble("descuento"), rs.getDouble("total"), rs.getDouble("saldo"), fecha, rs.getString("modo"), rs.getString("noControl"), rs.getString("proveedorId"));
      }
      while (rs.next()) { rs.getInt("facturaId"); }
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, Factura>(400, null, e.getMessage());
    }
    return factura != null ? new ServerResponse<Integer, Factura>(200, factura, Message.SUCCESS) :
            new ServerResponse<Integer, Factura>(400, null, Message.NOT_FOUND);
  }

  public ServerResponse<Integer, Integer> getLastRowId() {
    int rowId = 0;
    try {
      Connection conn = dbManager.getConnection();
      String sql = "SELECT facturaId FROM factura ORDER BY facturaId DESC LIMIT 1";
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(sql);
      while (rs.next()) { rowId = rs.getInt("facturaId"); }
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, rowId, Message.SUCCESS);
  }
}