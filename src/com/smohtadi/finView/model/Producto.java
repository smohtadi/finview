package com.smohtadi.finView.model;

public class Producto {
  private String productoId;
  private String descripcion;
  private String grupo;
  private String proveedorId;
  private int cantidad;
  private double precio;

  public Producto(String productoId, String descripcion, double precio, String grupo, String proveedorId, int cantidad) {
    this.productoId = productoId;
    this.descripcion = descripcion;
    this.precio = precio;
    this.grupo = grupo;
    this.proveedorId = proveedorId;
    this.cantidad = cantidad;
  }

  public Producto(String productoId, String descripcion, double precio, String grupo, String proveedorId) {
    this.productoId = productoId;
    this.descripcion = descripcion;
    this.precio = precio;
    this.grupo = grupo;
    this.proveedorId = proveedorId;
  }

  public Producto(String productoId, String descripcion, int cantidad, double precio) {
    this.productoId = productoId;
    this.descripcion = descripcion;
    this.precio = precio;
    this.cantidad = cantidad;
  }

  public String getProductoId() {
    return productoId;
  }

  public void setProductoId(String productoId) {
    this.productoId = productoId;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getGrupo() {
    return grupo;
  }

  public void setGrupo(String grupo) {
    this.grupo = grupo;
  }

  public String getProveedorId() {
    return proveedorId;
  }

  public void setProveedorId(String proveedorId) {
    this.proveedorId = proveedorId;
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
}
