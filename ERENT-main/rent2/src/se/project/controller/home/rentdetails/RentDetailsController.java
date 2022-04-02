package se.project.controller.home.rentdetails;

import java.time.LocalDateTime;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.ValidTransact;
import se.project.model.bike.BikeType;
import se.project.model.rent.Rent;
import se.project.util.MyUtils;

public class RentDetailsController extends Controlled {
	private Timeline timeline;
	@FXML
	private Label bikeType;
	@FXML
	private Label bikeWeight;
	@FXML
	private Label bikeProducer;
	@FXML
	private Label bikeCost;
	@FXML
	private ImageView bikeImg;
	@FXML
	private Label bikeName;
	@FXML
	private Label time;
	@FXML
	private Label totalTime;
	@FXML
	private Label totalMoney;


	public void initPane(Rent rent) {

		BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
		BikeType bike = bikeServiceImpl.getBikeById(Integer.toString(rent.getBikeId()));
		LocalDateTime s = LocalDateTime.now();
		s.format(MyUtils.format);

		timeline = new Timeline(
				new KeyFrame(Duration.seconds(1),
						e -> {
							totalTime.setText(rent.calculateAndSetTotalTime(LocalDateTime.now()));
							totalMoney.setText(MyUtils.moneyformat1.format(rent.getTotalMoney()));
						}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		time.setText(MyUtils.presentDate(rent.getTimeCreate()));

		bikeName.setText(bike.getName());
		bikeImg.setImage(bike.getI());
		bikeType.setText(bike.getType());
		bikeWeight.setText(Integer.toString(bike.getWeight()));
		bikeProducer.setText(bike.getProducer());
		bikeCost.setText(Integer.toString(bike.getCost()));
	}

	@FXML
	void returnBike(MouseEvent event) {
		totalMoney.setText("");
		totalTime.setText("");
		timeline.stop();

		SearchReturnController searchReturnController = (SearchReturnController) myController.getController(App.searchreturn);
		searchReturnController.init2();
		myController.setSx(App.searchreturn);
	}

	@FXML
	void back(MouseEvent event) {
		totalMoney.setText("");
		totalTime.setText("");
		timeline.stop();

		IValidTransact iCheck = new ValidTransact();
		if (iCheck.checkTransactFinish(myController.getCustomerId())) {
			myController.setSx(App.home1);
		} else {
			myController.setSx(App.home2);
		}
	}

}
