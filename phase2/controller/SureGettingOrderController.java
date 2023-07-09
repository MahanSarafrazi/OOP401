package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import phase2.model.Deliverer;
import phase2.model.Order;

import java.io.IOException;

public class SureGettingOrderController extends MenuController {

    @FXML
    public Button no;

    @FXML
    public Button yes;


    private Order order;

    @FXML
    public void yesHandler(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/myOrderDelivery.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((MyOrderController) loader.getController()).initialize(getFatherStageController().getStage(), getFatherStageController(), getFatherStageController().getMainScene(), null);
        ((MyOrderController) loader.getController()).chooseOrder(order);
        order.setDeliverer(((Deliverer) getManager().getLoggedInUser()).getLocation());
        ((Deliverer) getManager().getLoggedInUser()).setOrder(order);
        ((DelivererMenuController) getFatherStageController()).setMyOrder(loader.getRoot());
        ((DelivererMenuController) getFatherStageController()).update();
        getStage().close();
    }

    public void chooseOrder(Order order) {
        this.order = order;
    }

    @FXML
    public void noHandler(ActionEvent actionEvent) {
        getStage().close();
    }
}
