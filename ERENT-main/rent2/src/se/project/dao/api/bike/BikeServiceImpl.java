package se.project.dao.api.bike;

import javafx.collections.ObservableList;
import se.project.model.bike.BikeType;

public interface BikeServiceImpl {

//  public ObservableList<BikeType> getListFromDB(String store); // tu
  public ObservableList<BikeType> getListFromDB(int store);
  public ObservableList<BikeType> getAllBike();
  public BikeType getBikeByName(String bikeName);
  public BikeType getBikeById(String id);
  public String getBikeTypeById(String id);
  public Integer getIdByName(String name);
}
