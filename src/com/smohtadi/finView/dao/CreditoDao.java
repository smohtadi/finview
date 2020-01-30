package com.smohtadi.finView.dao;
import com.smohtadi.finView.model.Credito;
import com.smohtadi.finView.model.ServerResponse;
import utils.DateFormatter;
import utils.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CreditoDao extends Dao {

  public ServerResponse<Integer, Integer> create(Credito credito) {
    int rowNum = 0;
    try {
      Connection connection = dbManager.getConnection();
      String sql = "INSERT INTO credito (facturaId, fechaVcto, fechaPago, monto) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql);
      if (credito.getFechaPago() != null) {
        credito.setFechaPago(DateFormatter.format(credito.getFechaPago(), "dd/MM/yyyy", "yyyy-MM-dd"));
      }
      credito.setFechaVcto(DateFormatter.format(credito.getFechaVcto(), "dd/MM/yyyy", "yyyy-MM-dd"));
      ps.setInt(1, credito.getFacturaId());
      ps.setString(2, credito.getFechaVcto());
      ps.setString(3, credito.getFechaPago());
      ps.setDouble(4, credito.getMonto());
      rowNum = ps.executeUpdate();
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, Integer>(400, rowNum, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, rowNum, Message.SUCCESS);
  }

  public ServerResponse<Integer, Integer> update(Credito credito) {
    try {
      Connection connection = dbManager.getConnection();
      String sql = "UPDATE credito SET fechaVcto=?, fechaPago=?, monto=? WHERE creditoId=?";
      PreparedStatement ps = connection.prepareStatement(sql);
      if (credito.getFechaPago() != null) {
        credito.setFechaPago(DateFormatter.format(credito.getFechaPago(), "dd/MM/yyyy", "yyyy-MM-dd"));
      }
      credito.setFechaVcto(DateFormatter.format(credito.getFechaVcto(), "dd/MM/yyyy", "yyyy-MM-dd"));
      ps.setString(1, credito.getFechaVcto());
      ps.setString(2, credito.getFechaPago());
      ps.setDouble(3, credito.getMonto());
      ps.setInt(4, credito.getCreditoId());
      ps.executeUpdate();
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, Integer>(400, 0, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, 0, Message.SUCCESS);
  }

  public ServerResponse<Integer, Integer> deleteById(int creditoId) {
    try {
      Connection connection = dbManager.getConnection();
      String sql = "DELETE FROM credito WHERE creditoId=?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, creditoId);
      ps.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, 0, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, 0, Message.SUCCESS);
  }

  public ServerResponse<Integer, List<Credito>> findByFid(int facturaId) {
    List<Credito> creditos = null;
    try {
      Connection conn = dbManager.getConnection();
      String sql = "SELECT * FROM credito WHERE facturaId = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, facturaId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        creditos = new ArrayList<>();
        do {
          String vcto = DateFormatter.format(rs.getString("fechaVcto"), "yyyy-MM-dd", "dd/MM/yyyy");
          String pago = rs.getString("fechaPago") != null ? DateFormatter.format(rs.getString("fechaPago"), "yyyy-MM-dd", "dd/MM/yyyy") : null;
          creditos.add(new Credito(rs.getInt("creditoId"), rs.getInt("facturaId"), vcto, pago, rs.getDouble("monto")));
        } while (rs.next());
      }
    } catch (SQLException | ParseException e) {
      return new ServerResponse<Integer, List<Credito>>(400, null, e.getMessage());
    }
    return creditos != null ? new ServerResponse<Integer, List<Credito>>(200, creditos, Message.SUCCESS) :
            new ServerResponse<Integer, List<Credito>>(400, null, Message.NOT_FOUND);
  }

  public ServerResponse<Integer, String> createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS credito (creditoId INTEGER PRIMARY KEY, facturaId INTEGER," +
            "fechaVcto TEXT, fechaPago TEXT, monto REAL, FOREIGN KEY(facturaId) REFERENCES factura(facturaId) ON DELETE CASCADE)";
    return super.createTable(sql);
  }
}