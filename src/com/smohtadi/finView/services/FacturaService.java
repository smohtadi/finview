package com.smohtadi.finView.services;

import com.smohtadi.finView.dao.CreditoDao;
import com.smohtadi.finView.dao.FacturaDao;
import com.smohtadi.finView.dao.FacturaItemDao;
import com.smohtadi.finView.model.Credito;
import com.smohtadi.finView.model.Factura;
import com.smohtadi.finView.model.FacturaItem;
import com.smohtadi.finView.model.ServerResponse;

import java.util.List;

public class FacturaService {
  private static FacturaService facturaService = null;
  private CreditoDao creditoDao;
  private FacturaDao facturaDao;
  private FacturaItemDao facturaItemDao;
  private FacturaService () {
    facturaDao = new FacturaDao();
    facturaItemDao = new FacturaItemDao();
    creditoDao = new CreditoDao();
  }
  public static FacturaService getInstance() {
    if (facturaService == null)
      facturaService = new FacturaService();
    return facturaService;
  }

  public Integer fetchLastRowId() {
    ServerResponse<Integer, Integer> response = facturaDao.getLastRowId();
    return response.payload;
  }

  public String createFactura(Factura factura) {
    ServerResponse<Integer, Integer> response = facturaDao.create(factura);
    if (response.status >= 400)
      facturaDao.deleteById(factura.getFacturaId());
    return response.message;
  }

  public String deleteFactura(int facturaId) {
    ServerResponse<Integer, Integer> response = facturaDao.deleteById(facturaId);
    return response.message;
  }

  public String updateFactura(Factura factura) {
    ServerResponse<Integer, Integer> response = facturaDao.update(factura);
    return response.message;
  }

  public Factura fetchFacturaById(int facturaId) {
    ServerResponse<Integer, Factura> res = facturaDao.findById(facturaId);
    if (res.status >= 400) {
      System.out.println(res.message);
    }
    return res.payload;
  }

  public String createTable() {
    return facturaDao.createTable().message;
  }
}
