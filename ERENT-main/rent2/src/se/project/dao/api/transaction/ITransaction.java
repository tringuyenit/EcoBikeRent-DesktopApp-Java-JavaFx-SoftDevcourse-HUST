package se.project.dao.api.transaction;

import se.project.model.rent.Rent;

public interface ITransaction {

  public void saveTransaction(int orID, String msg, double money,String card); //
  public boolean startRent(Rent rent);
  public boolean endRent(int orId, int bikeId, double totMoney, String timeFinish, String returnI);
}
