package se.project.controller.station;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import se.project.app.App;
import se.project.controller.Controlled;
import se.project.controller.pay.PayController;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.model.bike.BikeType;
import se.project.model.rent.Rent;


public class BikeDetailsController extends Controlled {
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
	private ImageView img;

	@FXML
	private Label cost;

	@FXML
	private Label name;

	@FXML
	private Label producer;

	@FXML
	private Label type;

	@FXML
	private Label weight;


	public void initItem(BikeType bike) {
		img.setImage(bike.getI());
		name.setText(bike.getName());
		type.setText(bike.getType());
		weight.setText(Integer.toString(bike.getWeight()));
		producer.setText(bike.getProducer());
		cost.setText(Integer.toString(bike.getCost()));
	}

	@FXML
	public void rentItem(MouseEvent event) throws Exception {
		if(ConfirmController.display()){
			PayController payController = (PayController) myController.getController(App.paychoice);
			int bikeId =  bikeServiceImpl.getIdByName(name.getText());
			Rent rent = new Rent();
			rent.setBikeId(bikeId);
			rent.setCustId(myController.getCustomerId());
			rent.setStationId(getStationId());
			payController.setOrder(rent);
			payController.initData(rent);
			payController.setBack_bike_list(getBack_bike_list());
			myController.setSx(App.paychoice);
		}
	}

	@FXML
	public void back(MouseEvent event) {
		myController.setSx(App.station);
	}
}
