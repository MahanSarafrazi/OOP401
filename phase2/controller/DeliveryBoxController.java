package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase2.model.Order;

import java.io.IOException;

public class DeliveryBoxController extends MenuController {

    @FXML
    public TextField deliveryPrice;

    @FXML
    public TextField restaurantName;

    @FXML
    public Button restaurantLocation;

    @FXML
    public Button customerLocation;

    @FXML
    public Button buttonBox;

    private Order order;

    public void chooseOrder(Order order) {
        this.order = order;
        restaurantName.setText(order.getRestaurantName());
        deliveryPrice.setText(Double.toString(order.totalDeliveryPrice()));
    }

    public Order getOrder() {
        return order;
    }

    @FXML
    public void restaurantLocationHandler(ActionEvent actionEvent) {

    }

    @FXML
    public void customerLocationHandler(ActionEvent actionEvent) {

    }

    @FXML
    public void buttonBox(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/SureGettingOrder.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        ((SureGettingOrderController) loader.getController()).initialize(stage, getFatherStageController(), scene, null);
        ((SureGettingOrderController) loader.getController()).chooseOrder(order);
        ((SureGettingOrderController) loader.getController()).getStage().show();
    }
}
