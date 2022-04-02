package se.project.controller.pay;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.dao.api.rent.RentService;
import se.project.dao.api.rent.RentServiceImpl;
import se.project.dao.api.store.StationService;
import se.project.dao.api.store.StationServiceImpl;
import se.project.dao.api.transaction.ITransaction;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.TransactionDAO;
import se.project.dao.api.transaction.ValidTransact;
import se.project.util.email.IMessageService;
import se.project.model.bike.BikeType;
import se.project.model.rent.Rent;
import se.project.model.payment.CreditCard;

import se.project.model.payment.PayByCard;
import se.project.model.payment.PayService;
import se.project.util.MyUtils;

public class BankGateController extends Controlled implements Initializable {
	private ObservableList<BikeType> back_bike_list;
	public ObservableList<BikeType> getBack_bike_list() {
		return back_bike_list;
	}

	public void setBack_bike_list(ObservableList<BikeType> back_bike_list) {
		this.back_bike_list = back_bike_list;
	}
	private final BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
	@FXML
	private TextArea messageA;
	@FXML
	private TextField bank;
	@FXML
	private TextField cardNum;
	@FXML
	private DatePicker date;
	@FXML
	private TextField name;

	private final RentService rentService = new RentServiceImpl();
	private final ITransaction iTransaction = new TransactionDAO();
	private final StationService stationService = new StationServiceImpl();
	private Rent rent; // need to pass order from
	String formatDateTime;
	CreditCard card = new CreditCard();  // k khoi tao the moi
	private IMessageService service;

    private	BikeType bike;
	public void setOrder(Rent rent2) {
		this.rent = rent2;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		date.setEditable(false);
	}

   	public boolean validateCard() {
	   IValidTransact iCheck = new ValidTransact();
	   if (name.getText() == "" || cardNum.getText() == "" || bank.getText() == ""
				|| card.validateDate(date.getValue()) 
				|| cardNum.getText().replaceAll("\\s", "").length() != 16
				|| !cardNum.getText().replaceAll("\\s", "").matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Enter full infomation and card number is 16 digit !");
		}
	   
	   else if (!iCheck.checkSameCard(cardNum.getText().replaceAll("\\s", ""),Integer.toString(rent.getId()))) { // check same card
			JOptionPane.showMessageDialog(null, "You need to same card as first transaction");
	   }

	   else return true; // tri nguyen test

//	   else if (iCheck.checkCardUsed(cardNum.getText().replaceAll("\\s", ""),Integer.toString(order.getId()))) {// check cardUsed
//			JOptionPane.showMessageDialog(null, "Card in used");
//	   } else return true;
	   
	  return false;
   }


	@FXML
	public void back(MouseEvent event) {
		PayController payController = (PayController) myController.getController(App.paychoice);
		int bikeId =  bikeServiceImpl.getIdByName(bikeServiceImpl.getBikeById(Integer.toString(this.rent.getBikeId())).getName());
		Rent rent = new Rent();
		rent.setBikeId(bikeId);
		rent.setCustId(myController.getCustomerId());
		payController.setOrder(rent);
		payController.initData(rent);
		payController.setBack_bike_list(getBack_bike_list());// tri nguyen
		myController.setSx(App.paychoice);
	}

	@FXML
	public void pay() {
		boolean isSuccess = false;
		// can check xem the da su dung cho tk nao chua
		// check length = 16 contain only digit

//		if(validateCard()) {
		if(true) {
			BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
			bike = bikeServiceImpl.getBikeById(Integer.toString(rent.getBikeId()));

//			card = new CreditCard(bank.getText(), cardNum.getText().replaceAll("\\s", ""), date.getValue(),name.getText());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate datee = LocalDate.parse("2022-01-12", formatter);
			card = new CreditCard("banks", "1234567891111111", datee,"tu");
			PayService payService = new PayService(new PayByCard(card));// can luu the vao bang transaction

			formatDateTime = LocalDateTime.now().format(MyUtils.format);

			if (rent.getId() == 0) {
				isSuccess = payService.pay(bike.getDeposit());  // rent

				if (isSuccess) {
					rent.setTimeCreate(formatDateTime);
					if(iTransaction.startRent(rent)){
						int orderId = rentService.getOrderIdByUserId(rent.getCustId());
						rent.setId(orderId);
						iTransaction.saveTransaction(orderId, messageA.getText(),bike.getDeposit(),
								cardNum.getText().replaceAll("\\s", ""));
					}else {
						return;
					}
				}
			} else if (rent.getId() != 0) {
				isSuccess = payService.pay(bike.getDeposit(), rent.getTotalMoney());
				if (isSuccess) {
					if(iTransaction.endRent(rent.getId(), bike.getId(), rent.getTotalMoney(),
							rent.getTimeFinish(), rent.getReturnId())){
						iTransaction.saveTransaction(rent.getId(), messageA.getText(), rent.getTotalMoney(),
								cardNum.getText().replaceAll("\\s", ""));
					}else {
						return;
					}
				}
			}

			if (isSuccess) {
				createResultPane();
			} else
				JOptionPane.showMessageDialog(null, "Not enough money");

		}
	}

	public void createResultPane() {
		ResultController resultController = (ResultController) myController.getController(App.payresult);
		String money;
		if (rent.getTimeFinish()==null) {
			money = MyUtils.moneyformat1.format(bike.getDeposit());
		} else {
			money = MyUtils.moneyformat1.format(rent.getTotalMoney());
		}
		resultController.initResultPane(money,messageA.getText() ,formatDateTime);
		myController.setSx(App.payresult);
	}

}
