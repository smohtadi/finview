package com.smohtadi.finView.dao;

import com.smohtadi.finView.model.Producto;
import com.smohtadi.finView.model.ServerResponse;
import utils.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao extends Dao {

  public ServerResponse<Integer, String> create(Producto producto) {
    try {
      String sql = "INSERT INTO producto (productoId, descripcion, precio, grupo, proveedorId) VALUES (?, ?, ?, ?, ?)";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, producto.getProductoId());
      preparedStatement.setString(2, producto.getDescripcion());
      preparedStatement.setDouble(3, producto.getPrecio());
      preparedStatement.setString(4, producto.getGrupo());
      preparedStatement.setString(5, producto.getProveedorId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, String>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, String>(200, null, Message.SUCCESS);
  }

  public ServerResponse<Integer, Integer> update(Producto producto) {
    try {
      String sql = "UPDATE producto SET descripcion=?, grupo=?, proveedorId=? WHERE productoId=?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, producto.getDescripcion());
      preparedStatement.setString(2, producto.getGrupo());
      preparedStatement.setString(3, producto.getProveedorId());
      preparedStatement.setString(4, producto.getProductoId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      return new ServerResponse<Integer, Integer>(400, 0, e.getMessage());
    }
    return new ServerResponse<Integer, Integer>(200, 0, Message.SUCCESS);
  }

  public ServerResponse<Integer, List<String>> findByIdLike(String productoId) {
    List<String> productoIds = null;
    try {
      String sql = "SELECT productoId FROM producto WHERE productoId LIKE ? LIMIT 100";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, productoId + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        productoIds = new ArrayList<>();
        do {
          productoIds.add(resultSet.getString("productoId"));
        } while (resultSet.next());
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, List<String>>(400, null, e.getMessage());
    }
    return productoIds != null ? new ServerResponse<Integer, List<String>>(200, productoIds, null) : new ServerResponse<Integer, List<String>>(400, null, "No proveedor found");
  }

  public ServerResponse<Integer, Producto> findById(String productoId) {
    Producto producto = null;
    try {
      String sql = "SELECT * FROM producto WHERE productoId = ?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, productoId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        producto = new Producto(
                resultSet.getString("productoId"),
                resultSet.getString("descripcion"),
                0,
                resultSet.getDouble("precio"));
      }
    } catch (SQLException e) {
      return new ServerResponse<>(400, null, e.getMessage());
    }
    return producto != null ?
            new ServerResponse<Integer, Producto>(200, producto, null) :
            new ServerResponse<Integer, Producto>(400, null, "No proveedor found");
  }

  public ServerResponse<Integer, List<Producto>> findByDescripcion(String descripcion) {
    List<Producto> productos = null;
    try {
      String sql = "SELECT * FROM producto WHERE descripcion LIKE ?";
      Connection connection = dbManager.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, "%" + descripcion + "%");
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        productos = new ArrayList<>();
        do {
          productos.add(new Producto(resultSet.getString("productoId"), resultSet.getString("descripcion"), resultSet.getDouble("precio"), resultSet.getString("grupo"), resultSet.getString("proveedorId")));
        } while (resultSet.next());
      }
    } catch (SQLException e) {
      return new ServerResponse<Integer, List<Producto>>(400, null, e.getMessage());
    }
    return productos != null ? new ServerResponse<Integer, List<Producto>>(200, productos, null) :
            new ServerResponse<Integer, List<Producto>>(400, null, "No producto found");
  }

  public ServerResponse<Integer, String> createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS producto (productoId TEXT PRIMARY KEY, descripcion TEXT, grupo TEXT, proveedorId TEXT, precio REAL, FOREIGN KEY(proveedorId) REFERENCES proveedor(proveedorId) ON DELETE CASCADE)";
    return super.createTable(sql);
  }
}