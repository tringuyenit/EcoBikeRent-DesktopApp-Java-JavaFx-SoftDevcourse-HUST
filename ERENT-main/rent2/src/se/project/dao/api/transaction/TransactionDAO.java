package se.project.dao.api.transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import se.project.dao.Context;
import se.project.model.rent.Rent;

public class TransactionDAO implements ITransaction {

    @Override
    public boolean startRent(Rent rent) {

        try {
            // update bike rent /save transact to "order table"
            Connection con = Context.getConnection();

//            PreparedStatement ps = con.prepareStatement("SELECT insertRent(?,?,?)"); // function to insert and update
//            ps.setString(1, order.getTimeCreate());
//            ps.setString(2, Integer.toString(order.getBikeId()));
//            ps.setString(3, Integer.toString(order.getCustId()));
//            ps.execute();


            PreparedStatement ps = con.prepareStatement("SELECT renting(?,?,?,?)"); // function to insert and update
            ps.setString(1, Integer.toString(rent.getStationId()));
            ps.setString(2, Integer.toString(rent.getBikeId()));
            ps.setString(3, rent.getTimeCreate());
            ps.setString(4, Integer.toString(rent.getCustId()));
//            System.exit(0);
            ps.execute();


//            // get store id as input update store dock when rent  // tu
//            PreparedStatement ps1 = con.prepareStatement("SELECT updateStoreRent(?)");// tu
//            ps1.setString(1, Integer.toString(order.getBikeId()));// tu
//            ps1.execute();// tu
            con.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean endRent(int orId, int bikeId, double totMoney, String timeFinish, String returnI) {
        try {
            Connection con = Context.getConnection();
            //GET ID
//            PreparedStatement ps = con.prepareStatement("SELECT insertReturn(?,?,?,?,?)");
//            ps.setString(1, Integer.toString(orId));
//            ps.setString(2, Integer.toString(bikeId));
//            ps.setString(3, Double.toString(totMoney));
//            ps.setString(4, timeFinish);
//            ps.setString(5, returnI);

//            PreparedStatement ps = con.prepareStatement("SELECT returning(?,?,?,?,?)");
            PreparedStatement ps = con.prepareStatement(
            "UPDATE rent SET total = ?, timeFinish = ?, returnId = ? WHERE rent.orderId = ?");



            ps.setString(1, new BigDecimal(totMoney).toPlainString());
            ps.setString(2, timeFinish);
            ps.setString(3, returnI);
            ps.setString(4, Integer.toString(orId));
            ps.execute();



            ps = con.prepareStatement(
                    "UPDATE storehasbike SET " +
                            "number = " +
                            "number+1 WHERE  " +
                            "storeid = ? and bikeid = ?");

            ps.setString(1, returnI);
            ps.setString(2, Integer.toString(bikeId));

            ps.execute();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void saveTransaction(int orID, String msg, double money,String card) {
        try {
            Connection con = Context.getConnection();
            //GET ID
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO transaction(orderId,message,money,card) VALUES (?,?,?,?); ");
            ps.setString(1, Integer.toString(orID));
            ps.setString(2, msg);
            ps.setString(3, Double.toString(money));
            ps.setString(4,card);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);

        }


    }
}
