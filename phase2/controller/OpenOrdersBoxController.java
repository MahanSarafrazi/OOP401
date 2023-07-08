package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import phase2.view.OrderStatus;

public class OpenOrdersBoxController extends MenuController {

    @FXML
    public TextField totalPrice;

    @FXML
    public TextField orderID;

    @FXML
    public TextField statusSent;

    @FXML
    public Button buttonBox;

    @FXML
    public void buttonBoxHandler(ActionEvent actionEvent) {

    }

    public void chooseOrder(int orderID, double totalPrice, OrderStatus status) {
        this.orderID.setText(Integer.toString(orderID));
        this.totalPrice.setText(Double.toString(totalPrice));
        this.statusSent.setText(status.name());
    }
}
