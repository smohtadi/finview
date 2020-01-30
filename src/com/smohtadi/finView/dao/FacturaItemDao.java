package com.smohtadi.finView.dao;
import com.smohtadi.finView.model.FacturaItem;
import com.smohtadi.finView.model.ServerResponse;
import utils.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaItemDao extends Dao {

  public ServerResponse<Integer, Integer> create(FacturaItem facturaItem) {
    int row = 0;
    try {
      Connection connection = dbManager.getConnection();
      String sql = "INSERT INTO facturaItem (facturaId, productoId, cantidad, precio) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setInt(1, facturaItem.getFacturaId());
      ps.setString(2, facturaItem.getProductoId());
      ps.setDouble(3, facturaItem.getCantidad());
      ps.setDouble(4, facturaItem.getPrecio());
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if(rs.next())
        row = rs.getInt(1);
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, row, Message.SUCCESS);
  }

  public ServerResponse<Integer, Integer> deleteById(int facturaItemId) {
    int row = 0;
    try {
      Connection connection = dbManager.getConnection();
      String sql = "DELETE FROM facturaItem WHERE facturaItemId=?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, facturaItemId);
      ps.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, row, Message.SUCCESS);
  }

  public ServerResponse<Integer, List<FacturaItem>> findByFid(int facturaId) {
    List<FacturaItem> facturaItems = null;
    try {
      Connection conn = dbManager.getConnection();
      String sql = "SELECT f.facturaItemId, f.facturaId, f.productoId, f.cantidad, f.precio, p.descripcion FROM facturaItem f, producto p WHERE f.facturaId = ? AND f.productoId = p.productoId";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, facturaId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        facturaItems = new ArrayList<>();
        do {
          facturaItems.add(new FacturaItem(rs.getInt("facturaItemId"), rs.getInt("facturaId"), rs.getInt("cantidad"), rs.getString("productoId"), rs.getString("descripcion"), rs.getDouble("precio")));
        } while (rs.next());
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, List<FacturaItem>>(400, null, e.getMessage());
    }
    return facturaItems != null ? new ServerResponse<Integer, List<FacturaItem>>(200, facturaItems, Message.SUCCESS) :
            new ServerResponse<Integer, List<FacturaItem>>(400, null, Message.NOT_FOUND);
  }

  public ServerResponse<Integer, Integer> update(FacturaItem facturaItem) {
    int row = 0;
    try {
      Connection connection = dbManager.getConnection();
      String sql = "UPDATE facturaItem SET productoId=?, cantidad=?, precio=? WHERE facturaItemId=?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, facturaItem.getProductoId());
      ps.setInt(2, facturaItem.getCantidad());
      ps.setDouble(3, facturaItem.getPrecio());
      ps.setDouble(4, facturaItem.getFacturaItemId());
      row = ps.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, row, Message.SUCCESS);
  }

  public ServerResponse<Integer, String> createTable() {
    String sql =
            "CREATE TABLE IF NOT EXISTS facturaItem (facturaItemId INTEGER PRIMARY KEY, facturaId INTEGER," +
                    "productoId TEXT," +
                    "cantidad INTEGER, precio REAL," +
                    "FOREIGN KEY(facturaId) REFERENCES factura(facturaId) ON DELETE CASCADE)";
    return super.createTable(sql);
  }
}
