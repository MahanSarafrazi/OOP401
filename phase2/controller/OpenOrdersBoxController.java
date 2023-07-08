package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @FXML
    public void buttonBoxHandler(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Openordersbyowner.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((OpenOrdersController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
        ((OpenOrdersController) loader.getController()).chooseOrder(Integer.parseInt(orderID.getText()));
        ((OpenOrdersController) loader.getController()).getStage().setScene(scene);
        ((OpenOrdersController) loader.getController()).getStage().show();
    }

    public void chooseOrder(int orderID, double totalPrice, OrderStatus status) {
        this.orderID.setText(Integer.toString(orderID));
        this.totalPrice.setText(Double.toString(totalPrice));
        this.statusSent.setText(status.name());
    }
}
