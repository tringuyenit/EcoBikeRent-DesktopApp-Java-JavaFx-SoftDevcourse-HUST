package se.project.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseType {
  public Connection getConnection() throws SQLException, ClassNotFoundException;
}
