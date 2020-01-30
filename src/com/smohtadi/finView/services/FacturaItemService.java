package com.smohtadi.finView.services;
import com.smohtadi.finView.dao.FacturaItemDao;
import com.smohtadi.finView.model.FacturaItem;
import com.smohtadi.finView.model.ServerResponse;
import java.util.List;

public class FacturaItemService {
  private static FacturaItemService facturaItemService = null;
  private FacturaItemDao facturaItemDao;
  private FacturaItemService () {
    facturaItemDao = new FacturaItemDao();
  }
  public static FacturaItemService getInstance() {
    if (facturaItemService == null)
      facturaItemService = new FacturaItemService();
    return facturaItemService;
  }

  public String createTable() {
    return facturaItemDao.createTable().message;
  }

  public int createFacturaItem(FacturaItem facturaItem) {
    ServerResponse<Integer, Integer> response = facturaItemDao.create(facturaItem);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public int updateFactura(FacturaItem facturaItem) {
    ServerResponse<Integer, Integer> response = facturaItemDao.update(facturaItem);
    if (response.status >= 400) System.out.println(response.message);
    return response.payload;
  }

  public List<FacturaItem> fetchFacturaItemsByFid(int facturaId) {
    ServerResponse<Integer, List<FacturaItem>> res = facturaItemDao.findByFid(facturaId);
    if (res.status >= 400) {
      System.out.println(res.message);
    }
    return res.payload;
  }

  public int deleteItemById(int facturaItemId) {
    ServerResponse<Integer, Integer> res = facturaItemDao.deleteById(facturaItemId);
    if (res.status >= 400) {
      System.out.println(res.message);
    }
    return res.payload;
  }
}
