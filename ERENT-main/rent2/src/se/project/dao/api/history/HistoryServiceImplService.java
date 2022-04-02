package se.project.dao.api.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import se.project.dao.Context;
import se.project.model.history.RentHistory;

public class HistoryServiceImplService implements HistoryServiceImpl {

	private ArrayList<RentHistory> history;

	/* Get rent history by id */
	public ArrayList<RentHistory> getRentHistory(int custId) {

		try {
			history = new ArrayList<>();
			Connection con = Context.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT b.name,r.timeCreate, r.timeFinish ,r.total\n" +
					"\t\t\tFROM rent r INNER JOIN biketype b ON r.bikeId = b.id where r.custId = ? and total is not " +
					"NULL"); // lay
			// cai dat coc

			ps.setString(1, Integer.toString(custId));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RentHistory or = new RentHistory();
				or.setBikeName(rs.getString(1));
				or.setStart(rs.getString(2));
				or.setEnd(rs.getString(3));
				or.setMoney(Float.valueOf(rs.getString(4))); // tu 3
				history.add(or);
			}
		} catch (Exception e) {
			System.out.println("here");
			System.out.println(e);
		}
		return history;
	}
}
