package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class OrderHistoryBoxController extends MenuController {


    @FXML
    public TextField totalPrice;

    @FXML
    public TextField orderID;

    @FXML
    public Button buttonBox;

    @FXML
    public void buttonBoxHandler(ActionEvent actionEvent) {

    }

    public void chooseOrder(int orderID, double totalPrice) {
        this.orderID.setText(Integer.toString(orderID));
        this.totalPrice.setText(Double.toString(totalPrice));
    }
}
