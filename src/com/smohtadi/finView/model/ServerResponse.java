package com.smohtadi.finView.model;

public class ServerResponse<Integer, T> {
  public T payload;
  public int status;
  public String message;
  public ServerResponse(int status, T payload, String message) {
    this.status = status;
    this.payload = payload;
    this.message = message;
  }
}
