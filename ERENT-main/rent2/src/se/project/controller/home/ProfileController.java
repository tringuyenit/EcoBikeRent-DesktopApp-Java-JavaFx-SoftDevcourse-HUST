package se.project.controller.home;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.ValidTransact;
import se.project.dao.api.user.UserService;
import se.project.dao.api.user.UserServiceImpl;
import se.project.model.user.Customer;

public class ProfileController extends Controlled {

    @FXML
    private Button name;
    @FXML
    private Button username;
    @FXML
    private Button email;

    @FXML
    void back(MouseEvent event) {
        IValidTransact iCheck = new ValidTransact();
        if (iCheck.checkTransactFinish(myController.getCustomerId())) {
            myController.setSx(App.home1);
        } else {
            myController.setSx(App.home2);
        }
    }

    public void initPane() {
        UserService userService = new UserServiceImpl();
        Customer user = userService.getUserById(Integer.toString(myController.getCustomerId()));
        name.setText(user.getName());
        username.setText(user.getUsername());
        email.setText(user.getEmail());
    }
}
