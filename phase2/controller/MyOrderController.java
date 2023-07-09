package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import phase2.model.Order;

public class MyOrderController extends MenuController {

    @FXML
    public TextField deliveryPrice;

    @FXML
    public Button showPath;

    private Order order;

    public void chooseOrder(Order order) {
        this.order = order;
        deliveryPrice.setText(Double.toString(order.totalDeliveryPrice()));
    }

    public Order getOrder() {
        return order;
    }
}
