package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.Deliverer;
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

    @FXML
    public TextField ordersID;

    private Order order;

    public void chooseOrder(Order order) {
        this.order = order;
        restaurantName.setText(order.getRestaurantName());
        restaurantName.setEditable(false);
        deliveryPrice.setText(Double.toString(order.totalDeliveryPrice()));
        deliveryPrice.setEditable(false);
        ordersID.setText(Double.toString(order.getID()));
        ordersID.setEditable(false);
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
        Deliverer deliverer = (Deliverer)getManager().getLoggedInUser();
        if(deliverer.getLocation() <= 1000 && deliverer.getLocation() >= 1) {
            if(!((Deliverer)(getManager().getLoggedInUser())).hasOrder()) {
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
        } else {
            ((DelivererMenuController)(getFatherStageController())).getError2().setFill(Paint.valueOf("red"));
            ((DelivererMenuController)(getFatherStageController())).getError2().setText("You haven't set location");
            PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
            hitAnimation.setOnFinished(e -> ((DelivererMenuController)(getFatherStageController())).getError2().setText(""));
            hitAnimation.playFromStart();
        }
    }
}
