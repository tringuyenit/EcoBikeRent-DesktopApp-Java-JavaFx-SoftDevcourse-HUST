package se.project.controller.home;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.controller.station.StationController;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.dao.api.store.StationService;
import se.project.dao.api.store.StationServiceImpl;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.ValidTransact;
import se.project.model.bike.BikeType;
import se.project.model.station.Station;

public class SearchController extends Controlled {
	private final BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
	private final StationService stationService = new StationServiceImpl();;
    
	@FXML
	private TableColumn<Station, String> storeNameCol;
	@FXML
	private TableColumn<Station, String> storeAddressCol;
	@FXML
	private TableColumn<Station, String> storeStatusCol;
	@FXML
	private TableView<Station> storeTable;
	@FXML
	private TextField searchBar;

	@FXML
	void chooseRow(MouseEvent event) {
		if(storeTable.getSelectionModel().getSelectedItem().getAvailability().equals("YES")){
			StationController stationController = (StationController) myController.getController(App.station);
			ObservableList<BikeType> bikeList = bikeServiceImpl
					.getListFromDB(storeTable.getSelectionModel().getSelectedItem().getId());

			stationController.setStationId(storeTable.getSelectionModel().getSelectedItem().getId());
			stationController.initBike(bikeList);
			myController.setSx(App.station);
		}
	}

	@FXML
	void back(MouseEvent event) {
		IValidTransact iCheck = new ValidTransact();
		if (iCheck.checkTransactFinish(myController.getCustomerId())) {
			myController.setSx(App.home1);
		} else {
			myController.setSx(App.home2);
		}
	}

	public void init2() {
		storeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		storeAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		storeStatusCol.setCellValueFactory(new PropertyValueFactory<>("availability"));

		ObservableList<Station> dataList = stationService.getAllStations();
		FilteredList<Station> filteredData = new FilteredList<>(dataList, b -> true);

		searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(store -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (store.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (store.getAddress().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				} else {
					return false; // Does not match.
				}
			});
		});

		SortedList<Station> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(storeTable.comparatorProperty());
		storeTable.setItems(sortedData);
	}

}
