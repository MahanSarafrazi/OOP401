package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class OrderHistoryBoxController extends MenuController {


    @FXML
    public TextField totalPrice;

    @FXML
    public TextField orderID;

    @FXML
    public Button buttonBox;

    @FXML
    public void buttonBoxHandler(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Orderhistorybyowner.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((OrderHistoryController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
        ((OrderHistoryController) loader.getController()).chooseOrder(Integer.parseInt(orderID.getText()));
        ((OrderHistoryController) loader.getController()).getStage().setScene(scene);
        ((OrderHistoryController) loader.getController()).getStage().show();

    }

    public void chooseOrder(int orderID, double totalPrice) {
        this.orderID.setText(Integer.toString(orderID));
        this.totalPrice.setText(Double.toString(totalPrice));
    }
}
