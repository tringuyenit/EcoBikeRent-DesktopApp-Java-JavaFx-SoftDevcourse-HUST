package se.project.model.rent;

import java.time.Duration;
import java.time.LocalDateTime;

import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.model.bike.BikeFactory;
import se.project.model.bike.BikeType;
import se.project.util.MyUtils;

public class Rent {

	private int id = 0; 
	private int bikeId;

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	private int custId; // should have cust id
	private int stationId;
	private int timeInterval;
	private String timeCreate;
	private String timeFinish;
	private String returnId;

	public int getBikeId() {
		return bikeId;
	}

	public void setBikeId(int bikeId) {
		this.bikeId = bikeId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getTimeCreate() {
		return timeCreate;
	}

	public void setTimeCreate(String timeCreate) {
		this.timeCreate = timeCreate;
	}

	public String getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(String timeFinish) {
		this.timeFinish = timeFinish;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(long l) {
		this.timeInterval = (int) l;
	}

	public String calculateAndSetTotalTime(LocalDateTime time) {
		LocalDateTime dateTime = LocalDateTime.parse(timeCreate, MyUtils.format);
		Duration duration = Duration.between(dateTime, time);
		this.timeInterval = (int) duration.toSeconds();
		return MyUtils.date(getTimeInterval());
	}
	
	public double getTotalMoney() {
		BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
		String bikeType = bikeServiceImpl.getBikeTypeById(Integer.toString(bikeId));
		BikeType bike = BikeFactory.getBike(bikeType);
		return bike.totalCostSpecificBike(timeInterval);
	}
}
