package com.smohtadi.finView.services;

import com.smohtadi.finView.dao.CreditoDao;
import com.smohtadi.finView.model.Credito;
import com.smohtadi.finView.model.ServerResponse;

import java.util.List;

public class CreditoService {
  private static CreditoService creditoService = null;
  private CreditoDao creditoDao;

  private CreditoService () { creditoDao = new CreditoDao(); }

  public static CreditoService getInstance() {
    if (creditoService == null)
      creditoService = new CreditoService();
    return creditoService;
  }

  public int updateCredito(Credito credito) {
    ServerResponse<Integer, Integer> response = creditoDao.update(credito);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public int createCredito(Credito credito) {
    ServerResponse<Integer, Integer> response = creditoDao.create(credito);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public String createTable () { return creditoDao.createTable().message; }

  public int deleteById(int creditoId) {
    ServerResponse<Integer, Integer> response = creditoDao.deleteById(creditoId);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public List<Credito> fetchCreditoByFid(int facturaId) {
    ServerResponse<Integer, List<Credito>> response = creditoDao.findByFid(facturaId);
    if(response.status >= 400 ) {
      System.out.println(response.message);
    }
    return response.payload;
  }

}
