package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import phase2.model.UserType;
import phase2.view.Output;

public class RegisterMenuController extends MenuController {

    @FXML
    public TextField userName;

    @FXML
    public TextField passWord;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button back;

    @FXML
    public ChoiceBox type;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        String username = this.userName.getText();
        String password = this.passWord.getText();
        UserType userType = null;
        if(type.getValue().equals("customer")) {
            userType = UserType.CUSTOMER;
        } else if(type.getValue().equals("restaurant owner")) {
            userType = UserType.RESTAURANT_OWNER;
        } else if(type.getValue().equals("deliverer")) {
            userType = UserType.DELIVERER;
        }
        Output output = super.getManager().addUser(username, password, userType);
        System.out.println(output);
    }
}
