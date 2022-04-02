package se.project.controller.home.history;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.api.history.HistoryServiceImplService;
import se.project.dao.api.history.HistoryServiceImpl;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.ValidTransact;
import se.project.model.history.RentHistory;
import se.project.util.MyUtils;

public class HistoryController extends Controlled {

	@FXML
	private FlowPane rentFlow;
	
	private final HistoryServiceImpl history = new HistoryServiceImplService();

	public void init(FlowPane pane, ArrayList<RentHistory> list) {
		Collections.reverse(list);
		for (RentHistory i : list) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/se/project/view/history/DepositHistory.fxml"));
			try {
				File file = new File("src/se/project/image/" + i.getBikeName() + ".jpeg");
				Image image = new Image(file.toURI().toString());
				Pane view = loader.load();
				HistoryRecordController storeP = loader.getController();
				storeP.initItem(i.getBikeName(), MyUtils.presentDate2(i.getStart()), MyUtils.presentDate2(i. getEnd()) ,
						MyUtils.moneyformat2.format(i.getMoney()), image);
				pane.getChildren().add(view);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void initHistory() {
		init(rentFlow, history.getRentHistory(myController.getCustomerId()));
	}

	@FXML
	void back(MouseEvent event) {
		rentFlow.getChildren().clear();
		IValidTransact iCheck = new ValidTransact();
		if (iCheck.checkTransactFinish(myController.getCustomerId())) {
			myController.setSx(App.home1);
		} else {
			myController.setSx(App.home2);
		}
	}
}
