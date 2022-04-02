package se.project.dao.api.history;

import java.util.ArrayList;

import se.project.model.history.RentHistory;

public interface HistoryServiceImpl {
	  public ArrayList<RentHistory> getRentHistory(int custId);
}
