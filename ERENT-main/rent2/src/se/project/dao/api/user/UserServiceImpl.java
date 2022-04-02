package se.project.dao.api.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import se.project.dao.Context;
import se.project.model.user.Customer;

public class UserServiceImpl implements UserService {

  @Override
  public Customer getUserByUsername(String username) {
    Customer customer = new Customer();
    try {

      Connection con = Context.getConnection();
      PreparedStatement ps = con.prepareStatement(
          "SELECT * FROM useraccount  where user = ?");
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      rs.next();

      customer.setId(Integer.parseInt(rs.getString(1)));
      customer.setName(rs.getString(4));
      customer.setUsername(rs.getString(2));
      customer.setAddress(rs.getString(5));
      customer.setEmail(rs.getString(6));
      con.close();

    } catch (Exception e) {
      System.out.println(e);

    }
    return customer;
  }
  
  @Override
  public String getNameById(String id) {
    String userName = null;
    try {

      Connection con = Context.getConnection();
      PreparedStatement ps = con.prepareStatement(
          "SELECT name FROM useraccount  where id = ?");
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
     if( rs.next()) {
    	 userName = rs.getString(1);
     }
     
      con.close();

    } catch (Exception e) {
      System.out.println(e);

    }
    return userName;
  }

  @Override
  public Customer getUserById(String id) {
    Customer customer = new Customer();
    try {

      Connection con = Context.getConnection();
      PreparedStatement ps = con.prepareStatement(
              "SELECT * FROM useraccount where id = ?");
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
      rs.next();

      customer.setId(Integer.parseInt(rs.getString(1)));
      customer.setName(rs.getString(4));
      customer.setUsername(rs.getString(2));
      customer.setAddress(rs.getString(5));
      customer.setEmail(rs.getString(6));
      con.close();

    } catch (Exception e) {
      System.out.println(e);
    }
    return customer;
  }

}
