package se.project.controller.login;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import se.project.app.App;
import se.project.controller.Controlled;

public class SplashController extends Controlled implements Initializable {

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		new ShowSplashScreen().start();
	}

	class ShowSplashScreen extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				Platform.runLater(() -> {
					myController.setSx(App.login);
				});
			} catch (InterruptedException ex) {
				Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}
}
