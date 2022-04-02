package se.project.dao.api.rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import se.project.dao.Context;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.model.rent.Rent;

public class RentServiceImpl implements RentService {
	@Override
	public Rent getOrderByUserId(int userId) {
		Rent rent = new Rent();
		BikeServiceImpl ibike = new BikeServiceImplService();
		try {

			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"SELECT orderId,timeCreate,bikeId FROM rent  where custId = ? and timeFinish is null");
			ps.setString(1, Integer.toString(userId));
			ResultSet rs = ps.executeQuery();

			// exist bike
			if (rs.next()) {
				rent.setId(Integer.valueOf(rs.getString(1)));
				rent.setTimeCreate(rs.getString(2));
				rent.setBikeId(Integer.valueOf(rs.getString(3)));
			} else {
				//order.setBike(null);
				rent = null;
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}
		return rent;
	}

	@Override
	public int getOrderIdByUserId(int custId) {
		try {
			Connection con = Context.getConnection();
			PreparedStatement ps = con
					.prepareStatement("SELECT orderId FROM rent WHERE custId = ? ORDER BY orderId DESC LIMIT 1 ");
			ps.setString(1, Integer.toString(custId));
			ResultSet rs = ps.executeQuery();
			rs.next();
			int ans = Integer.valueOf(rs.getString(1));
			con.close();
			return ans;
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

}
