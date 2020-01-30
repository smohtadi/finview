package com.smohtadi.finView.services;
import com.smohtadi.finView.dao.ProveedorDao;
import com.smohtadi.finView.model.Proveedor;
import com.smohtadi.finView.model.ServerResponse;
import java.util.List;

public class ProveedorService implements IService {
  private static ProveedorService proveedorService = null;
  private ProveedorDao proveedorDao;
  private ProveedorService() {
    proveedorDao = new ProveedorDao();
  }

  public static ProveedorService getInstance() {
    if (proveedorService == null)
      proveedorService = new ProveedorService();
    return proveedorService;
  }

  public Proveedor fetchProveedor(String proveedorId) {
    ServerResponse<Integer, Proveedor> response = proveedorDao.findById(proveedorId);
    if (response.status >= 400) {
      return null;
    }
    return response.payload;
  }

  public String createProveedor(Proveedor proveedor) {
    return proveedorDao.create(proveedor).message;
  }
  public String createTable() {
    return proveedorDao.createTable().message;
  }

  public List<Proveedor> fetchProveedorByNombre(String proveedorNombre) {
    ServerResponse<Integer, List<Proveedor>> response = proveedorDao.findByNombreLike(proveedorNombre);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }
  public String updateProveedorById(Proveedor proveedor) {
    return proveedorDao.updateById(proveedor).message;
  }

  @Override
  public List<String> fetchSuggestions(String proveedorId) {
    ServerResponse<Integer, List<String>> res = proveedorDao.findByIdLike(proveedorId);
    if (res.status >= 400) { return null; }
    return res.payload;
  }
}
