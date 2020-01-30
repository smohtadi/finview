package com.smohtadi.finView.services;

import com.smohtadi.finView.dao.ProductoDao;
import com.smohtadi.finView.model.Producto;
import com.smohtadi.finView.model.ServerResponse;

import java.util.List;

public class ProductoService implements IService {

  private static ProductoService productoService = null;
  private ProductoDao productoDao;
  private ProductoService() {
    productoDao = new ProductoDao();
  }
  public static ProductoService getInstance() {
    if (productoService == null)
      productoService = new ProductoService();
    return productoService;
  }

  public String createProducto(Producto producto) {
    return productoDao.create(producto).message;
  }

  public Producto fetchProveedor(String productoId) {
    ServerResponse<Integer, Producto> res = productoDao.findById(productoId);
    if (res.status >= 400) { return null; }
    return res.payload;
  }

  public List<Producto> fetchProductos(String descripcion) {
    ServerResponse<Integer, List<Producto>> response =  productoDao.findByDescripcion(descripcion);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public String updateProducto(Producto producto) {
    ServerResponse<Integer, Integer> response = productoDao.update(producto);
    if (response.status >= 400) System.out.println(response.message);
    return response.message;
  }

  public String createTable() {
    return productoDao.createTable().message;
  }

  @Override
  public List<String> fetchSuggestions(String productoId) {
    ServerResponse<Integer, List<String>> res = productoDao.findByIdLike(productoId);
    if (res.status >= 400) { return null; }
    return res.payload;
  }
}
