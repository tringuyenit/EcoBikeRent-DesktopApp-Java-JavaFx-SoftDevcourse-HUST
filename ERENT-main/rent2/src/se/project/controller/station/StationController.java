package se.project.controller.station;

import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.controller.home.SearchController;
import se.project.model.bike.BikeType;

public class StationController extends Controlled {

	@FXML
	private FlowPane itemFlow;

	private int stationId;

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
    
	public void initBike(ObservableList<BikeType> bikeList) {
		itemFlow.getChildren().clear();
		for (BikeType s : bikeList) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/se/project/view/category/bikeStation.fxml"));

			try {
				Pane view = loader.load();

				File file = new File("src/se/project/image/" + s.getName() + ".jpeg");
				Image image = new Image(file.toURI().toString());
				BikeStationController storeP = loader.getController();
				storeP.setBikeName(s.getName());
				storeP.setStationId(getStationId());
				storeP.setBack_bike_list(bikeList);
				storeP.setSParent(myController);
				storeP.initBikePane(s.getName(), image);
				itemFlow.getChildren().add(view);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void back(MouseEvent event) {
		itemFlow.getChildren().clear();
		SearchController searchController = (SearchController) myController.getController(App.searching);
		searchController.init2();
		myController.setSx(App.searching);
	}

}
