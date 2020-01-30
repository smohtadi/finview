package com.smohtadi.finView.model;

public class Proveedor {
  private String proveedorId;
  private String nombre;
  private String ruc;

  public Proveedor(String proveedorId, String nombre, String ruc) {
    this.proveedorId = proveedorId;
    this.nombre = nombre;
    this.ruc = ruc;
  }

  public String getProveedorId() {
    return proveedorId;
  }

  public void setProductoId(String proveedorId) {
    this.proveedorId = proveedorId;
  }

  public String getNombre() { return nombre; }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getRuc() {
    return ruc;
  }

  public void setRuc(String ruc) {
    this.ruc = ruc;
  }
}