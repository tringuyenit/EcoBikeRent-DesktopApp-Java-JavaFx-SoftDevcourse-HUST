package se.project.controller.home.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.project.dao.admin.AdminAPI;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.model.bike.BikeFactory;
import se.project.model.bike.BikeType;

public class AdminController implements Initializable {

	@FXML
	private TableColumn<BikeType, Integer> idCol;

	@FXML
	private TableColumn<BikeType, String> licenseCol;

	@FXML
	private TableColumn<BikeType, String> manuCol;

	@FXML
	private TableColumn<BikeType, String> nameCol;

	@FXML
	private TableColumn<BikeType, String> producerCol;

	@FXML
	private TableColumn<BikeType, String> statusCol;

	@FXML
	private TableColumn<BikeType, Integer> storeIdCol;

	@FXML
	private TableColumn<BikeType, Integer> weightCol;

	@FXML
	private TableColumn<BikeType, Integer> costCol;
	@FXML
	private TableColumn<BikeType, String> typeCol;

	@FXML
	private TableView<BikeType> bikeTable;
	@FXML
	private Button btnSignOut;

	

	@FXML
	private TextField cost;



	@FXML
	private TextField id;

	@FXML
	private TextField license;

	@FXML
	private TextField manufacture;

	@FXML
	private TextField name;

	@FXML
	private TextField producer;

	@FXML
	private TextField status;

	@FXML
	private TextField storeId;

	@FXML
	private TextField type;
	@FXML
	private TextField weight;

	@FXML
	void add(ActionEvent event) {
		  BikeType bike = BikeFactory.getBike(type.getText()); // type
		    bike.setId(Integer.valueOf(id.getText()));
			bike.setStoreId(Integer.valueOf(storeId.getText()));
			bike.setName(name.getText());
			bike.setType(type.getText());
			bike.setWeight(Integer.parseInt(weight.getText()));
			bike.setLicense(license.getText());
			bike.setManufacture(manufacture.getText());
			bike.setProducer(producer.getText());
			bike.setCost(Integer.valueOf(cost.getText()));
			bike.setStatus(status.getText());
			AdminAPI.insertBike(bike);
			JOptionPane.showMessageDialog(null, "Insert success");
		    reloadTable();
	}

	public void initialize(URL location, ResourceBundle resources) {

		bikeTable.setEditable(true);

		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		storeIdCol.setCellValueFactory(new PropertyValueFactory<>("storeId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name")); // empty dock
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
		licenseCol.setCellValueFactory(new PropertyValueFactory<>("license"));
		manuCol.setCellValueFactory(new PropertyValueFactory<>("manufacture"));
		producerCol.setCellValueFactory(new PropertyValueFactory<>("producer"));
		costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

		reloadTable();

	}

	public void reloadTable() {
		BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
		ObservableList<BikeType> bikeList = bikeServiceImpl.getAllBike();
		bikeTable.setItems(bikeList);
	}

	@FXML
	void logOut(MouseEvent event) {
		Util util = new Util();
		util.change("/se/project/view/login/login.fxml", btnSignOut);
	}

	@FXML
	void remove(ActionEvent event) {
		AdminAPI.deleteBike(Integer.toString(bikeTable.getSelectionModel().getSelectedItem().getId()));
		JOptionPane.showMessageDialog(null, "Delete success");
		reloadTable();
	}

	@FXML
	void update(ActionEvent event) {

	}

}
