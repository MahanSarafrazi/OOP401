package phase2.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OpenOrdersController extends MenuController implements Initializable {

    @FXML
    public Tab openOrders;

    @FXML
    public Tab restaurantInformation;

    @FXML
    public Tab orderHistory;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restaurantInformation.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantMenuByOwner.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(loader.getRoot());
                TabPane tabPane = ((TabPane) ((AnchorPane) scene.getRoot()).getChildren().get(0));
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(0);
                ((RestaurantMenuByOwnerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getPreviousScene());
                ((RestaurantMenuByOwnerController) loader.getController()).getStage().setScene(scene);
            }
        });

        orderHistory.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Orderhistorybyowner.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(loader.getRoot());
                TabPane tabPane = ((TabPane) ((AnchorPane) scene.getRoot()).getChildren().get(0));
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(1);
                ((OrderHistoryController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getPreviousScene());
                ((OrderHistoryController) loader.getController()).getStage().setScene(scene);
            }
        });
    }
}
