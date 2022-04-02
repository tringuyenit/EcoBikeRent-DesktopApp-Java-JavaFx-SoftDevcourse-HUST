package se.project.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.project.controller.FullController;
import se.project.dao.Context;
import se.project.dao.MySQLConnection;


public class App extends Application {

	public static String splash = "splash";
	public static String splashfile = "/se/project/view/login/splash.fxml";
	public static String login = "login";
	public static String loginfile = "/se/project/view/login/login.fxml";

	public static String home1 = "home1";
	public static String home1file = "/se/project/view/home/home1.fxml";
	public static String home2 = "home2";
	public static String home2file = "/se/project/view/home/home2.fxml";

	public static String searching = "searching";
	public static String searchfile = "/se/project/view/home/search.fxml";
	public static String searchreturn = "searchreturn";
	public static String searchreturnfile = "/se/project/view/home/searchreturn.fxml";

	public static String profile = "profile";
	public static String profilefile = "/se/project/view/home/profile.fxml";

	public static String station = "station";
	public static String stationfile = "/se/project/view/category/station.fxml";

	public static String bikestation = "bikeinshop";
	public static String bikestationfile = "/se/project/view/category/bikeStation.fxml";

	public static String bikedetails = "bikedetails";
	public static String bikedetailsfile = "/se/project/view/home/bikeDetail.fxml";

	public static String details = "details";
	public static String detailsfile = "/se/project/view/rentdetails/rentdetails.fxml";

	public static String paychoice = "paychoice";
	public static String paychoicefile = "/se/project/view/pay/pay.fxml";

	public static String bankgate = "bankgate";
	public static String bankgatefile = "/se/project/view/pay/bankgate.fxml";

	public static String payresult = "payresult";
	public static String payresultfile = "/se/project/view/pay/payResult.fxml";

	public static String hist = "history";
	public static String histfile = "/se/project/view/history/history.fxml";

	public static String depositt = "depositt";
	public static String deposittfile = "/se/project/view/history/DepositHistory.fxml";

	private static Stage primaryStage;
	public static void resizeScreen(){
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
	}

	public App() {
		Context.selectDataBase(new MySQLConnection());
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		FullController mainController = new FullController();

		mainController.loadSx(splash, splashfile);
		mainController.loadSx(login, loginfile);
		mainController.loadSx(home1, home1file);
		mainController.loadSx(home2, home2file);
		mainController.loadSx(profile, profilefile);
		mainController.loadSx(searching, searchfile);
		mainController.loadSx(searchreturn, searchreturnfile);
		mainController.loadSx(station, stationfile);
		mainController.loadSx(bikestation, bikestationfile);
		mainController.loadSx(bikedetails, bikedetailsfile);
		mainController.loadSx(details, detailsfile);
		mainController.loadSx(paychoice, paychoicefile);
		mainController.loadSx(bankgate, bankgatefile);
		mainController.loadSx(payresult, payresultfile);
		mainController.loadSx(hist, histfile);

		primaryStage = stage;
		mainController.setSx(App.splash);

		Group root = new Group();
		root.getChildren().addAll(mainController);
		Scene scene = new Scene(root);
		Image icon = new Image("se/project/image/Bicycle-icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("EcoBikeRental");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

}
