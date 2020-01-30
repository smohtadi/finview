package com.smohtadi.finView.dao;

import com.smohtadi.finView.model.ServerResponse;
import utils.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Dao {
  protected DbManager dbManager = DbManager.getInstance();

  protected ServerResponse<Integer, String> createTable(String sql) {
    try {
      Connection connection = dbManager.getConnection();
      Statement statement = connection.createStatement();
      statement.execute(sql);
    } catch (SQLException e) {
      return new ServerResponse<Integer, String>(400, null, e.getMessage());
    }
    return new ServerResponse<Integer, String>(200, null, Message.SUCCESS);
  }
}
