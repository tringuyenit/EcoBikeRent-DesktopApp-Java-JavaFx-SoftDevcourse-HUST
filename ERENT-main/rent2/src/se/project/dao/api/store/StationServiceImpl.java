package se.project.dao.api.store;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import se.project.dao.Context;
import se.project.model.station.Station;

public class StationServiceImpl implements StationService {

	public String checkAvalibility(int current_bike_num) {
		if(current_bike_num > 0){
			return "YES";
		}
		return "NO";
	}
	public String checkEmptySlot(int current_bike_num, int max_bike) {
		if(max_bike - current_bike_num > 0){
			return "YES";
		}

		if(max_bike - current_bike_num == 0){
			return "NO";
		}

		System.out.println("max_bike - current_bike_num < 0 ???????");
		return "ERROR";
	}

	@Override
	public ObservableList<Station> getAllStations() {
		ObservableList<Station> stationList = FXCollections.observableArrayList();

		try {

			Connection con = Context.getConnection();
			// can liet ke so xe dang thue
//			PreparedStatement ps = con
//					.prepareStatement("SELECT s.name,s.address,s.max,coalesce(d.rent,0),s.status,s.storeid "
//							+ "FROM store s LEFT JOIN (SELECT storeid,count(*) as rent  FROM biketype "
//							+ "WHERE status = 'Rent' GROUP BY storeid ) as d ON s.storeid = d.storeid"); // tu

			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM (SELECT storeid, SUM(number) as current_bike FROM `storehasbike` " +
					"GROUP BY storeid) as t1 " +
					"LEFT JOIN (SELECT storeid as storeid_tmp, name, address, maxbike FROM `store`) as t2 ON t1.storeid = t2.storeid_tmp " +
					"UNION " +
					"SELECT * FROM (SELECT storeid, SUM(number) as current_bike FROM `storehasbike` " +
					"GROUP BY storeid) as t1 " +
					"RIGHT JOIN (SELECT storeid as storeid_tmp, name, address, maxbike FROM `store`) as t2 ON t1.storeid = t2.storeid_tmp");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Station station = new Station();
				// System.out.print(rs.getString(1));
//				store.setName(rs.getString(1));
//				store.setAddress(rs.getString(2));
//				store.setAvailable(Integer.valueOf(rs.getString(3)));
//				store.setRent(Integer.valueOf(rs.getString(4)));
//				store.setStatus(rs.getString(5));
//				store.setId(Integer.valueOf(rs.getString(6)));

				station.setName(rs.getString(4));
				station.setAddress(rs.getString(5));
				station.setId(Integer.valueOf(rs.getString(1)));
				station.setAvailability(checkAvalibility(Integer.parseInt(rs.getString(2))));
				station.setEmptySlot(checkEmptySlot(Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(6))));
				File file = new File("src/se/project/image/" + station.getId() + ".jpeg");
				Image image = new Image(file.toURI().toString());
				station.setImage(image);
				stationList.add(station);
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return stationList;
	}

	public String getStationIdByName(String bikeName) {
		String id = null;
		try {
			Connection con = Context.getConnection();
			// GET ID
			PreparedStatement ps = con.prepareStatement("SELECT storeid FROM ebike.store where name = ?");
			ps.setString(1, bikeName);
			ResultSet rs = ps.executeQuery();

			rs.next();
			id = rs.getString(1);
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
		return id;
	}

}
