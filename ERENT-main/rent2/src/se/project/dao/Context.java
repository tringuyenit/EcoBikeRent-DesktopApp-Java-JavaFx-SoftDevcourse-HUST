package se.project.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class Context {

  private static DatabaseType type;


  // select database
  public static void selectDataBase(DatabaseType tp) {
    Context.type = tp;
  }


  // get connection of database
  public static Connection getConnection() throws ClassNotFoundException, SQLException {

    return type.getConnection();

  }

}
