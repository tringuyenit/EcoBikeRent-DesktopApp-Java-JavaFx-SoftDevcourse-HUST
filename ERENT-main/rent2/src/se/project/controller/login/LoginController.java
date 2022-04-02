package se.project.controller.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

import se.project.app.App;
import se.project.controller.Controlled;
import se.project.dao.Context;
import se.project.dao.api.transaction.IValidTransact;
import se.project.dao.api.transaction.ValidTransact;

public class LoginController extends Controlled {

	@FXML private TextField user;
	@FXML private TextField pass;

	@FXML
	public void signIn() {
		if (validate(user.getText(), pass.getText())) { // validate() function sets userID
			user.clear();
			pass.clear();
			IValidTransact iCheck = new ValidTransact();
			if (iCheck.checkTransactFinish(myController.getCustomerId())) {
				myController.setSx(App.home1);
			} else {
				myController.setSx(App.home2);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Wrong password or username");
		}
	}

	@FXML
	public void signUp() {
//		try {
//			Connection con = Context.getConnection();
//			PreparedStatement pss = con.prepareStatement(
//					"INSERT INTO useraccount SET user = ? , pass = ?");
//			pss.setString(1, user.getText());
//			pss.setString(2, pass.getText());
//			pss.execute();
//			con.close();
//		} catch (Exception ee) {
//			JOptionPane.showMessageDialog(null, "Wrong password or username");
//		} finally {
//			JOptionPane.showMessageDialog(null, "Success . You have created an account.");
//		}
	}

	@FXML
	public void signInEnter(KeyEvent k) {
		if (k.getCode().equals(KeyCode.ENTER)) {
			signIn();
		}
	}
	
    @FXML
    void adminLog(MouseEvent event) {
//    	FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("/se/project/view/admin/admin.fxml"));
//		try {
//			Parent root = loader.load();
//			Stage stage = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
//			stage.setScene(new Scene(root));
//			stage.show();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

	public boolean validate(String user, String pass) {

		boolean check = false;

		try {
			Connection con = Context.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"SELECT * FROM useraccount WHERE user =? AND pass =?");
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				myController.setCustomerId(Integer.parseInt(rs.getString(1)));
				con.close();
				check = true;
			} else {
				con.close();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return check;

	}
}
