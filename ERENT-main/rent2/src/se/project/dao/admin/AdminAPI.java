package se.project.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;

import se.project.dao.Context;
import se.project.model.bike.BikeType;

public class AdminAPI {
   public static void  deleteBike(String id) {
	   try {

		      Connection con = Context.getConnection();
		      PreparedStatement ps = con.prepareStatement(
		          "DELETE  FROM bikeType  where id = ?");
		     ps.setString(1, id);
		     ps.executeUpdate();
		     
		      con.close();

		    } catch (Exception e) {
		      System.out.println(e);

		    }
   }
   
   public static void  insertBike(BikeType bike) {
	   try {

		      Connection con = Context.getConnection();
		      PreparedStatement ps = con.prepareStatement(
		          "INSERT INTO biketype VALUES (?,?,?,?,?,?,?,?,?,?)");
		        
				ps.setString(1,Integer.toString(bike.getId()));
				ps.setString(2,Integer.toString(bike.getStoreId()));
				ps.setString(3,bike.getName());
				ps.setString(4,bike.getType());
				ps.setString(5,Integer.toString(bike.getWeight()));
				ps.setString(6,bike.getLicense());
				ps.setString(7,bike.getManufacture());
				ps.setString(8,bike.getProducer());
				ps.setString(9,Integer.toString(bike.getCost()));
				ps.setString(10,bike.getStatus());
		        ps.executeUpdate();
		     
		      con.close();

		    } catch (Exception e) {
		      System.out.println(e);

		    }
   }
}
