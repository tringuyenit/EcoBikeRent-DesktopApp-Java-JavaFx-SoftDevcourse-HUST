package se.project.controller.station;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmController {
    static boolean answer;

    @FXML
    private Button yesbtn;
    @FXML
    private Button backbtn;

    @FXML
    public void yes(MouseEvent event) {
        answer = true;
        Stage window = (Stage) yesbtn.getScene().getWindow();
        window.close();
    }

    @FXML
    public void back(MouseEvent event) {
        answer = false;
        Stage window = (Stage) backbtn.getScene().getWindow();
        window.close();
    }

    public static boolean display() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ConfirmController.class.getResource("/se/project/view/home/confirm.fxml"));
        Parent root = loader.load();
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirmation");
        window.setScene(new Scene(root));
        window.showAndWait();
        return answer;
    }
}
