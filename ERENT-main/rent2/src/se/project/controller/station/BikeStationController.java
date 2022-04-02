package se.project.controller.station;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.model.bike.BikeType;

public class BikeStationController extends Controlled {
	private final BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();

	public ObservableList<BikeType> getBack_bike_list() {
		return back_bike_list;
	}

	public void setBack_bike_list(ObservableList<BikeType> back_bike_list) {
		this.back_bike_list = back_bike_list;
	}

	private ObservableList<BikeType> back_bike_list;
	private int stationId;

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	@FXML
	private ImageView bikeImg;

	private String bikeName;

	public String getBikeName() {
		return bikeName;
	}

	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}

	public void initBikePane(String bikeName,Image image) {
		bikeImg.setImage(image);
	}

	@FXML
	void loadDetail(MouseEvent event) {
		BikeDetailsController bikeDetailsController = (BikeDetailsController) myController.getController(App.bikedetails);
		BikeType bike = bikeServiceImpl.getBikeByName(this.bikeName);  // get selected // tu
		bikeDetailsController.setBack_bike_list(getBack_bike_list());//tri nguyen
		bikeDetailsController.initItem(bike);
		bikeDetailsController.setStationId(getStationId());
		myController.setSx(App.bikedetails);
	}

}