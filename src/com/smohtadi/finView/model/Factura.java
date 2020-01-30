package com.smohtadi.finView.model;

public class Factura {
  private int facturaId; // primary key
  private double descuento;
  private double total;
  private double saldo;
  private String fecha;
  private String modo;
  private String noControl;
  private String proveedorId; // foreign key

  public Factura(int facturaId, double descuento, double total, double saldo, String fecha, String modo, String noControl, String proveedorId) {
    this.facturaId = facturaId;
    this.descuento = descuento;
    this.total = total;
    this.saldo = saldo;
    this.fecha = fecha;
    this.modo = modo;
    this.noControl = noControl;
    this.proveedorId = proveedorId;
  }
  public Factura() {}

  public int getFacturaId() {
    return facturaId;
  }

  public void setFacturaId(int facturaId) {
    this.facturaId = facturaId;
  }

  public double getDescuento() {
    return descuento;
  }

  public void setDescuento(double descuento) {
    this.descuento = descuento;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public double getSaldo() { return saldo; }

  public void setSaldo(double saldo) { this.saldo = saldo; }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public String getModo() {
    return modo;
  }

  public void setModo(String modo) {
    this.modo = modo;
  }

  public String getNoControl() {
    return noControl;
  }

  public void setNoControl(String noControl) {
    this.noControl = noControl;
  }

  public String getProveedorId() {
    return proveedorId;
  }

  public void setProveedorId(String proveedorId) {
    this.proveedorId = proveedorId;
  }
}
