package se.project.dao.api.store;

import javafx.collections.ObservableList;
import se.project.model.station.Station;

public interface StationService {

  public ObservableList<Station> getAllStations();
  public String getStationIdByName(String stationName);
}


