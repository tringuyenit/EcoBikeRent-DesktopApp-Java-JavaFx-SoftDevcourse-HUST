package se.project.dao.api.rent;

import se.project.model.rent.Rent;

public interface RentService {

  public Rent getOrderByUserId(int userId);  // get order detail by cust id
  public int getOrderIdByUserId(int custId); // get orderid by cust id
}
