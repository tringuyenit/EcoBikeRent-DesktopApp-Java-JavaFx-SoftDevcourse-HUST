package se.project.controller.pay;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.ValidTransact;

public class ResultController extends Controlled {

	@FXML
	private Button homeBtn;
	@FXML
	private Label money;
	@FXML
	private TextArea msg;
	@FXML
	private Label time;
	@FXML
	private Label transacCode;

	@FXML
	void backHome(MouseEvent event) {
		IValidTransact iCheck = new ValidTransact();
		if (iCheck.checkTransactFinish(myController.getCustomerId())) {// tri nguyen
			myController.setSx(App.home1);
		} else {
			myController.setSx(App.home2);
		}
	}

	public void initResultPane(String m, String message, String t) {
		money.setText(m);
		msg.setText(message);
		time.setText(t);
	}

}
