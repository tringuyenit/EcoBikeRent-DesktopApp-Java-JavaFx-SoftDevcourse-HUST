package se.project.controller.home.admin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Util {

	private Pane view;

	public void change(String scene, Button but) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource(scene));
			Stage stage = (Stage) but.getScene().getWindow();
			// para event Stage stage = (Stage) (Node)event.getScene().getWindow();
			stage.setScene(new Scene(root));

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
