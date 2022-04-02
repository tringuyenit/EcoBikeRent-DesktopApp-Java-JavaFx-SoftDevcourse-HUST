package se.project.controller.home;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.controller.home.history.HistoryController;
import se.project.controller.home.rentdetails.RentDetailsController;
import se.project.dao.api.rent.RentService;
import se.project.dao.api.rent.RentServiceImpl;
import se.project.model.rent.Rent;

public class HomeController extends Controlled{
	private final RentService rentService = new RentServiceImpl();

	@FXML
	void search(MouseEvent event) {
		SearchController searchController = (SearchController) myController.getController(App.searching);
		searchController.init2();
		myController.setSx(App.searching);
	}

	@FXML
	void viewRentDetails(MouseEvent event) {
		RentDetailsController rentDetailsController = (RentDetailsController) myController.getController(App.details);
		Rent rent = rentService.getOrderByUserId(myController.getCustomerId());
//		rentDetailsController.setOrder(rent);
		rentDetailsController.initPane(rent);
		myController.setSx(App.details);
	}

	@FXML
	void viewProfile(MouseEvent event) {
		ProfileController profileController = (ProfileController) myController.getController(App.profile);
		profileController.initPane();
		myController.setSx(App.profile);
	}

	@FXML
	void viewHistory(MouseEvent event) {
		HistoryController historyController = (HistoryController) myController.getController(App.hist);
		historyController.initHistory();
		myController.setSx(App.hist);
	}

	@FXML
	void signOut(MouseEvent event) {
		myController.setSx(App.login);
	}
}
