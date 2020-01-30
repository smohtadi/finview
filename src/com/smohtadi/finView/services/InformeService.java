package com.smohtadi.finView.services;

import com.smohtadi.finView.dao.InformeDao;
import com.smohtadi.finView.model.Informe;
import com.smohtadi.finView.model.ServerResponse;

import java.util.List;

public class InformeService {
  private static InformeService informeService = null;
  private InformeDao informeDao;

  private InformeService () { informeDao = new InformeDao(); }

  public static InformeService getInstance() {
    if (informeService == null)
      informeService = new InformeService();
    return informeService;
  }

  public List<Informe> fetchCompras(String de, String desde, String proveedorId) {
    ServerResponse<Integer, List<Informe>> response = informeDao.findCompras(de, desde, proveedorId);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public List<Informe> fetchCredito(String de, String desde, String proveedorId) {
    ServerResponse<Integer, List<Informe>> response = informeDao.findCredito(de, desde, proveedorId);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }
}
