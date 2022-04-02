package se.project.controller.home.history;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HistoryRecordController {

  @FXML
  private ImageView img;
  @FXML
  private Label bikeName;
  @FXML
  private Label money;
  @FXML
  private Label start;
  @FXML
  private Label end;

  public void initItem(String name, String starttime, String endtime, String moneyy, Image image) {
    bikeName.setText(name);
    start.setText(starttime);
    end.setText(endtime);
    money.setText(moneyy);
    img.setImage(image);
  }
}


