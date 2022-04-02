package se.project.controller.home.rentdetails;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.controller.station.ConfirmController;
import se.project.controller.pay.PayController;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.dao.api.rent.RentService;
import se.project.dao.api.rent.RentServiceImpl;
import se.project.dao.api.store.StationService;
import se.project.dao.api.store.StationServiceImpl;
import se.project.model.rent.Rent;
import se.project.model.station.Station;
import se.project.util.MyUtils;

import java.time.LocalDateTime;

public class SearchReturnController extends Controlled {
    private final RentService rentService = new RentServiceImpl();
    private final BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
    private final StationService stationService = new StationServiceImpl();
    private Station station;

    @FXML
    private TableColumn<Station, String> storeNameCol;
    @FXML
    private TableColumn<Station, String> storeAddressCol;
    @FXML
    private TableColumn<Station, String> storeStatusCol;
    @FXML
    private TableView<Station> storeTable;
    @FXML
    private Button returnBtn;

    @FXML
    private TextField searchBar;

    @FXML
    void chooseRow(MouseEvent event) {
        station = storeTable.getSelectionModel().getSelectedItem();
        returnBtn.setVisible(station.getEmptySlot().equals("YES"));
    }

    @FXML
    void back(MouseEvent event) {
        station = null;
        RentDetailsController rentDetailsController = (RentDetailsController) myController.getController(App.details);
        Rent rent = rentService.getOrderByUserId(myController.getCustomerId());
//        rentDetailsController.setOrder(rent);
        rentDetailsController.initPane(rent);
        myController.setSx(App.details);
    }

    public void init2() {
        returnBtn.setVisible(false);
        storeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        storeStatusCol.setCellValueFactory(new PropertyValueFactory<>("emptySlot"));

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
                } else return store.getAddress().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Station> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(storeTable.comparatorProperty());
        storeTable.setItems(sortedData);
    }

    @FXML
    void returnBike(MouseEvent event) throws Exception {
        String stationReturn = station.getName();
        if(ConfirmController.display()){
            PayController payController = (PayController) myController.getController(App.paychoice);

            Rent rent = rentService.getOrderByUserId(myController.getCustomerId());
            rent.setReturnId(stationService.getStationIdByName(stationReturn));
            rent.setCustId(myController.getCustomerId());
            LocalDateTime now = LocalDateTime.now();
            rent.calculateAndSetTotalTime(now); // tinh tong thoi gian thanh toan
            rent.setTimeFinish(now.format(MyUtils.format)); // set String right format // time finish

            payController.setOrder(rent);
            payController.initData(rent);
            payController.setBack_bike_list(null);
            myController.setSx(App.paychoice);
        }
    }
}
