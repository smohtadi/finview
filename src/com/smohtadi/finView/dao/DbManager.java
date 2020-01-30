package com.smohtadi.finView.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
  private final String URL = "./data/finview.db";
  private static DbManager dbManager;
  private Connection connection = null;

  private DbManager() { }
  public static DbManager getInstance() {
    if (dbManager == null)
      dbManager = new DbManager();
    return dbManager;
  }

  public Connection getConnection() throws SQLException {
    if (connection == null)
      connection = DriverManager.getConnection("jdbc:sqlite:" + URL);
    return connection;
  }
}
