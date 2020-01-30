package com.smohtadi.finView.model;

public class FacturaItem {
  private int facturaItemId;
  private int facturaId;
  private int cantidad;
  private String productoId;
  private String descripcion;
  private double precio;

  public FacturaItem(int facturaItemId, int facturaId, int cantidad, String productoId, String descripcion, double precio) {
    this.facturaItemId = facturaItemId;
    this.facturaId = facturaId;
    this.cantidad = cantidad;
    this.productoId = productoId;
    this.descripcion = descripcion;
    this.precio = precio;
  }

  public FacturaItem(int facturaId, String productoId, int cantidad, double precio) {
    this.facturaId = facturaId;
    this.productoId = productoId;
    this.cantidad = cantidad;
    this.precio = precio;
  }

  public FacturaItem(int facturaId, String productoId, int cantidad, double precio, String descripcion) {
    this.facturaId = facturaId;
    this.productoId = productoId;
    this.cantidad = cantidad;
    this.precio = precio;
    this.descripcion = descripcion;
  }

  public int getFacturaItemId() {
    return facturaItemId;
  }

  public void setFacturaItemId(int facturaItemId) {
    this.facturaItemId = facturaItemId;
  }

  public int getFacturaId() {
    return facturaId;
  }

  public void setFacturaId(int facturaId) {
    this.facturaId = facturaId;
  }

  public String getProductoId() {
    return productoId;
  }

  public void setProductoId(String productoId) {
    this.productoId = productoId;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
