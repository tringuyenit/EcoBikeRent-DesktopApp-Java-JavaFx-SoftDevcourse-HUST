package se.project.dao.api.bike;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import se.project.dao.Context;
import se.project.model.bike.BikeFactory;
import se.project.model.bike.BikeType;


public class BikeServiceImplService implements BikeServiceImpl {

	@Override
	public ObservableList<BikeType> getListFromDB(int store) { // getListFromDB(String store) // tu
		ObservableList<BikeType> bikeList = FXCollections.observableArrayList();

		try {
			Connection con = Context.getConnection();
//			PreparedStatement ps = con.prepareStatement(
//					"SELECT biketype.id,biketype.name,type,manuafactur,producer,cost,biketype.status FROM biketype INNER JOIN store ON biketype.storeid = store.storeid where store.name = ?");
//			ps.setString(1, store); // tu

			PreparedStatement ps = con.prepareStatement("SELECT * FROM biketype WHERE id in (SELECT DISTINCT bikeid " +
					"FROM storehasbike WHERE storeid = ? and number != 0)");
			ps.setString(1, Integer.toString(store));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BikeType bike = BikeFactory.getBike(rs.getString(4));
				bike.setId(Integer.valueOf(rs.getString(1)));
				bike.setName(rs.getString(3));
				bike.setType(rs.getString(4));
				bike.setManufacture(rs.getString(7));
				bike.setProducer(rs.getString(8));
				bike.setCost(Integer.parseInt(rs.getString(9)));
				bikeList.add(bike);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return bikeList;
	}

	@Override
	public BikeType getBikeByName(String bikeName) {

		try {

			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM biketype where biketype.name = ?");
			ps.setString(1, bikeName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			BikeType bike = BikeFactory.getBike(rs.getString(4));
			bike.setId(Integer.valueOf(rs.getString(1)));
			bike.setName(rs.getString(3));
			bike.setType(rs.getString(4));
			bike.setWeight(Integer.parseInt(rs.getString(5)));
			bike.setLicense(rs.getString(6));
			bike.setManufacture(rs.getString(7));
			bike.setProducer(rs.getString(8));
			bike.setCost(Integer.valueOf(rs.getString(9)));
			
			File file = new File("src/se/project/image/" + bike.getName() + ".jpeg");
			Image image = new Image(file.toURI().toString());
			bike.setI(image);
			con.close();
			return bike;
		} catch (Exception e) {
			System.out.println(e);

		}
		return null;
	}
	public String getBikeTypeById(String id) {
		String type = null;
		try {

			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT type FROM biketype  where id = ?"); // nen select col thay
																									// vi select het
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
			type = rs.getString(1);}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);

		}
		return type;
	}
	public BikeType getBikeById(String id) {
		BikeType bike;
		try {

			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT  * FROM biketype  where id = ?"); // nen select col thay
																									// vi select het
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				bike = BikeFactory.getBike(rs.getString(4));
				bike.setId(Integer.parseInt(rs.getString(1)));
				bike.setName(rs.getString(3));
				bike.setType(rs.getString(4));
				bike.setWeight(Integer.parseInt(rs.getString(5)));
				bike.setLicense(rs.getString(6));
				bike.setManufacture(rs.getString(7));
				bike.setProducer(rs.getString(8));
				bike.setCost(Integer.parseInt(rs.getString(9)));
				File file = new File("src/se/project/image/" + bike.getName() + ".jpeg");
				Image image = new Image(file.toURI().toString());
				bike.setI(image);
				con.close();
				return bike;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public ObservableList<BikeType> getAllBike() {
		ObservableList<BikeType> bikeList = FXCollections.observableArrayList();

		try {
			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT  * FROM biketype"); // nen select col thay vi select het

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BikeType bike = BikeFactory.getBike(rs.getString(4)); // type
				bike.setId(Integer.valueOf(rs.getString(1)));
				bike.setStoreId(Integer.valueOf(rs.getString(2)));
				bike.setName(rs.getString(3));
				bike.setType(rs.getString(4));
				bike.setWeight(Integer.parseInt(rs.getString(5)));
				bike.setLicense(rs.getString(6));
				bike.setManufacture(rs.getString(7));
				bike.setProducer(rs.getString(8));
				bike.setCost(Integer.valueOf(rs.getString(9)));
				bike.setStatus(rs.getString(10));
				bikeList.add(bike);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return bikeList;
	}

	@Override
	public Integer getIdByName(String name) {
		int id = 0;
		try {

			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT id FROM biketype  where name = ?"); // nen select col thay
																									// vi select het
			ps.setString(1,name);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
			id = Integer.valueOf(rs.getString(1));}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);

		}
		return id;
	}
}
