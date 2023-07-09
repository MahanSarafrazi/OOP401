package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import phase2.model.Customer;
import phase2.model.RestaurantOwner;
import phase2.model.Order;
import phase2.view.OrderStatus;

import java.io.IOException;

public class OpenOrdersBoxController extends MenuController {

    @FXML
    public TextField totalPrice;

    @FXML
    public TextField orderID;

    @FXML
    public TextField statusSent;

    @FXML
    public Button buttonBox;

    private Order order;

    @FXML
    public void buttonBoxHandler(ActionEvent actionEvent) {
        FXMLLoader loader;
        if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
            loader = new FXMLLoader(this.getClass().getResource("../view/Openordersbyowner.fxml"));
        }
        else {
            loader = new FXMLLoader(this.getClass().getResource("../view/Orderbycustomer.fxml"));
        }
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        if (getManager().getLoggedInUser() instanceof RestaurantOwner)
            ((OpenOrdersController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
        else
            ((OpenOrdersController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene(),
                    ((Customer)getManager().getLoggedInUser()).getOrders(),Integer.parseInt(orderID.getText()));
        ((OpenOrdersController) loader.getController()).chooseOrder(order);
        ((OpenOrdersController) loader.getController()).getStage().setScene(scene);
        ((OpenOrdersController) loader.getController()).getStage().show();
    }

    public void chooseOrder(Order order) {
        this.order = order;
        this.orderID.setText(Integer.toString(order.getID()));
        this.totalPrice.setText(Double.toString(order.totalPrice()));
        this.statusSent.setText(order.getOrderStatus().name());
    }
}
