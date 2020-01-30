package com.smohtadi.finView.model;

public class Store {
  private static Store store;
  private String windowName;
  private int facturaId;
  private boolean updateFactura;
  public static Store getInstance() {
    if (store == null) store = new Store();
    return store;
  }
  public void setWindowName(String windowName) { this.windowName = windowName; }
  public String getWindowName() { return windowName; }
  public void setUpdateFactura(boolean updateFactura) { this.updateFactura = updateFactura; }
  public boolean getUpdateFactura() { return updateFactura; }
  public void setFacturaId(int facturaId) { this.facturaId = facturaId; }
  public int getFacturaId() { return facturaId; }
}
