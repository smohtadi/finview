package com.smohtadi.finView.model;

public class Informe {
  private String fecha;
  private String fechaVcto;
  private String fechaPago;
  private int facturaId;
  private String modo;
  private String proveedorId;
  private String noControl;
  private double total;
  private double saldo;

  public Informe(String fecha, String fechaVcto, String fechaPago, int facturaId, String modo, String proveedorId, String noControl, double total, double saldo) {
    this.fecha = fecha;
    this.fechaVcto = fechaVcto;
    this.fechaPago = fechaPago;
    this.facturaId = facturaId;
    this.modo = modo;
    this.proveedorId = proveedorId;
    this.noControl = noControl;
    this.total = total;
    this.saldo = saldo;
  }

  public Informe(String fecha, int facturaId, String modo, String proveedorId, String noControl, double total, double saldo) {
    this.fecha = fecha;
    this.facturaId = facturaId;
    this.modo = modo;
    this.proveedorId = proveedorId;
    this.noControl = noControl;
    this.total = total;
    this.saldo = saldo;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public String getFechaVcto() {
    return fechaVcto;
  }

  public void setFechaVcto(String fechaVcto) {
    this.fechaVcto = fechaVcto;
  }

  public String getFechaPago() {
    return fechaPago;
  }

  public void setFechaPago(String fechaPago) {
    this.fechaPago = fechaPago;
  }

  public int getFacturaId() {
    return facturaId;
  }

  public void setFacturaId(int facturaId) {
    this.facturaId = facturaId;
  }

  public String getModo() {
    return modo;
  }

  public void setModo(String modo) {
    this.modo = modo;
  }

  public String getProveedorId() {
    return proveedorId;
  }

  public void setProveedorId(String proveedorId) {
    this.proveedorId = proveedorId;
  }

  public String getNoControl() {
    return noControl;
  }

  public void setNoControl(String noControl) {
    this.noControl = noControl;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }
}
