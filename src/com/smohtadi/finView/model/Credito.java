package com.smohtadi.finView.model;

public class Credito {
  private int creditoId;
  private int facturaId; // Factura Id
  private String fechaVcto;
  private String fechaPago;
  private double monto;

  public Credito(int creditoId, int facturaId, String fechaVcto, String fechaPago, double monto) {
    this.creditoId = creditoId;
    this.facturaId = facturaId;
    this.fechaVcto = fechaVcto;
    this.fechaPago = fechaPago;
    this.monto = monto;
  }

  public Credito(int facturaId, String fechaVcto, String fechaPago, double monto) {
    this.facturaId = facturaId;
    this.fechaVcto = fechaVcto;
    this.fechaPago = fechaPago;
    this.monto = monto;
  }

  public Credito(String fechaVcto, String fechaPago, double monto) {
    this.fechaVcto = fechaVcto;
    this.fechaPago = fechaPago;
    this.monto = monto;
  }

  public int getCreditoId() {
    return creditoId;
  }

  public void setCreditoId(int creditoId) {
    this.creditoId = creditoId;
  }

  public int getFacturaId() {
    return facturaId;
  }

  public void setFacturaId(int facturaId) {
    this.facturaId = facturaId;
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

  public double getMonto() {
    return monto;
  }

  public void setMonto(double monto) {
    this.monto = monto;
  }
}
