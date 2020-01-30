package com.smohtadi.finView.dao;
import com.smohtadi.finView.model.Proveedor;
import com.smohtadi.finView.model.ServerResponse;
import utils.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDao extends Dao {

  public ServerResponse<Integer, String> create(Proveedor proveedor) {
    try {
      String sql = "INSERT INTO proveedor (proveedorId, nombre, ruc) VALUES (?, ?, ?)";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, proveedor.getProveedorId());
      preparedStatement.setString(2, proveedor.getNombre());
      preparedStatement.setString(3, proveedor.getRuc());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, String>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, String>(200, null, Message.SUCCESS);
  }

  public ServerResponse<Integer, String> deleteById(String proveedorId) {
    try {
      String sql = "DELETE FROM proveedor WHERE proveedorId = ?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, proveedorId);
      preparedStatement.execute();
    } catch (SQLException e) {
      return new ServerResponse<Integer, String>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, String>(200, null, Message.SUCCESS);
  }

  public ServerResponse<Integer, String> updateById(Proveedor proveedor) {
    try {
      String sql = "UPDATE proveedor SET nombre = ?, ruc = ? where proveedorId = ?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, proveedor.getNombre());
      preparedStatement.setString(2, proveedor.getRuc());
      preparedStatement.setString(3, proveedor.getProveedorId());
      preparedStatement.execute();
    } catch (SQLException e) {
      return new ServerResponse<Integer, String>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, String>(200, null, Message.SUCCESS);
  }


  public ServerResponse<Integer, Proveedor> findById(String pid) {
    Proveedor proveedor = null;
    try {
      String sql = "SELECT * FROM proveedor WHERE proveedorId = ?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, pid);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        proveedor = new Proveedor(resultSet.getString("proveedorId"), resultSet.getString("nombre"), resultSet.getString("ruc"));
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, Proveedor>(400, null, e.getMessage());
    }
    return proveedor != null ? new ServerResponse<Integer, Proveedor>(200, proveedor, null) :
            new ServerResponse<Integer, Proveedor>(400, null, "No proveedor found");
  }

  public ServerResponse<Integer, List<Proveedor>> findByNombreLike(String nombre) {
    List<Proveedor> proveedores = null;
    try {
      String sql = "SELECT * FROM proveedor WHERE nombre LIKE ?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, "%"+nombre+"%");
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        proveedores = new ArrayList<>();
        do {
          proveedores.add(new Proveedor(resultSet.getString("proveedorId"), resultSet.getString("nombre"), resultSet.getString("ruc")));
        } while (resultSet.next());
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, List<Proveedor>>(400, null, e.getMessage());
    }
    return proveedores != null ? new ServerResponse<Integer, List<Proveedor>>(200, proveedores, Message.SUCCESS) :
            new ServerResponse<Integer, List<Proveedor>>(400, null, "No proveedor found");
  }

  public ServerResponse<Integer, List<String>> findByIdLike(String proveedorId) {
    List<String> proveedorIds = null;
    try {
      String sql = "SELECT proveedorId FROM proveedor WHERE proveedorId LIKE ? LIMIT 100";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, proveedorId+"%");
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        proveedorIds = new ArrayList<>();
        do {
          proveedorIds.add(resultSet.getString("proveedorId"));
        } while (resultSet.next());
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, List<String>>(400, null, e.getMessage());
    }
    return proveedorIds != null ? new ServerResponse<Integer, List<String>>(200, proveedorIds, null) :
            new ServerResponse<Integer, List<String>>(400, null, "No proveedor found");
  }

  public ServerResponse<Integer, List<Proveedor>> findAll() {
    List<Proveedor> proveedores = null;
    try {
      String sql = "SELECT * FROM proveedor";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        proveedores = new ArrayList<>();
        do {
          proveedores.add(new Proveedor(resultSet.getString("proveedorId"), resultSet.getString("nombre"), resultSet.getString("ruc")));
        } while (resultSet.next());
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, List<Proveedor>>(400, null, e.getMessage());
    }
    return proveedores != null ? new ServerResponse<Integer, List<Proveedor>>(200, proveedores, null) :
            new ServerResponse<Integer, List<Proveedor>>(400, null,"No proveedor found");
  }

  public ServerResponse<Integer, String> createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS proveedor (proveedorId TEXT PRIMARY KEY, nombre TEXT, ruc TEXT)";
    return super.createTable(sql);
  }
}