package se.project.controller.pay;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.swing.JOptionPane;

import se.project.app.App;
import se.project.controller.Controlled;
import se.project.controller.station.BikeDetailsController;
import se.project.controller.home.rentdetails.RentDetailsController;
import se.project.dao.api.bike.BikeServiceImplService;
import se.project.dao.api.bike.BikeServiceImpl;
import se.project.dao.api.rent.RentService;
import se.project.dao.api.rent.RentServiceImpl;
import se.project.dao.api.user.UserService;
import se.project.dao.api.user.UserServiceImpl;
import se.project.model.bike.BikeType;
import se.project.model.rent.Rent;
import se.project.util.MyUtils;

public class PayController extends Controlled {
  private final RentService rentService = new RentServiceImpl();
  private ObservableList<BikeType> back_bike_list;// tri nguyen
  public ObservableList<BikeType> getBack_bike_list() {
    return back_bike_list;
  }

  public void setBack_bike_list(ObservableList<BikeType> back_bike_list) {
    this.back_bike_list = back_bike_list;
  }
  private final BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();

  @FXML
  private ImageView img;

  @FXML
  private Pane payPane;
  
  @FXML
  private Label excessCash;
  
  

  @FXML
  private Label total;

  @FXML
  private Label deposit;

  @FXML
  private Text bikeName;

  @FXML
  private Label time;

  @FXML
  private Label rentFee;

  @FXML
  private Label cost;
  @FXML
  private CheckBox cash;

  @FXML
  private CheckBox credit;

  @FXML
  private Label name;

  @FXML
  private Label total1;

 
  private Rent rent;

  public void setOrder(Rent rent2) {
    this.rent = rent2;
  }

  public void initData(Rent rent) {
    BikeServiceImpl bikeServiceImpl = new BikeServiceImplService();
    BikeType bike = bikeServiceImpl.getBikeById(Integer.toString(rent.getBikeId()));

    bikeName.setText(bike.getName());
    deposit.setText(MyUtils.moneyformat1.format(bike.getDeposit()));
    rentFee.setText(MyUtils.moneyformat1.format(rent.getTotalMoney()));
    if(rent.getId()!=0) {
      // not work
      excessCash.setText(MyUtils.moneyformat1.format(bike.getDeposit()));

      // LocalDateTime dateTime = LocalDateTime.parse(timeCreate, format);

      time.setText(MyUtils.date(rent.getTimeInterval()));
      double a = rent.getTotalMoney();
      total.setText(MyUtils.moneyformat1.format(a));
      total1.setText(MyUtils.moneyformat1.format(a));

    }else {
      total.setText(MyUtils.moneyformat1.format(bike.getDeposit()));
      total1.setText(MyUtils.moneyformat1.format(bike.getDeposit()));}
    UserService userService = new UserServiceImpl();
    String userName =  userService.getNameById(Integer.toString(rent.getCustId()));
    name.setText(userName);
    cost.setText(Integer.toString(bike.getCost()));
    img.setImage(bike.getI());
    // load pane
    payPane.toFront();

  }

  @FXML
  public void rentItem(MouseEvent event) {
    if (credit.isSelected() || cash.isSelected()) {
      BankGateController bankGateController = (BankGateController) myController.getController(App.bankgate);
      bankGateController.setOrder(rent);
      bankGateController.setBack_bike_list(getBack_bike_list());
      myController.setSx(App.bankgate);
    } else {
      JOptionPane.showMessageDialog(null, "Pick payment method first");
    }
  }

  @FXML
  public void back(MouseEvent event) {
    if(getBack_bike_list() == null){
      RentDetailsController rentDetailsController = (RentDetailsController) myController.getController(App.details);
      Rent rent = rentService.getOrderByUserId(myController.getCustomerId());
//      rentDetailsController.setOrder(rent);
      rentDetailsController.initPane(rent);
      cash.setSelected(false);
      credit.setSelected(false);
      myController.setSx(App.details);
      return;
    }

    BikeDetailsController bikeDetailsController = (BikeDetailsController) myController.getController(App.bikedetails);
    BikeType bike = bikeServiceImpl.getBikeById(Integer.toString(rent.getBikeId()));
    bikeDetailsController.setBack_bike_list(getBack_bike_list());
    bikeDetailsController.initItem(bike);
    cash.setSelected(false);
    credit.setSelected(false);
    myController.setSx(App.bikedetails);
  }

}
