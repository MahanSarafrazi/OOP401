package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phase2.model.Order;
import phase2.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

public class DelivererMenuController extends MenuController {

    @FXML
    public Button logout;

    @FXML
    public TextField username;

    @FXML
    public TextField restoreSolve;

    @FXML
    public TextField restoreQuestion;

    @FXML
    public TextField password;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public VBox list;

    @FXML
    public Button back;
    @FXML
    public Tab myOrderTab;

    public VBox getList() {
        return list;
    }

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        ArrayList<Order> orders = getManager().getOrdersWithoutDeliverer();
        for (Order order : orders) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxdelivery.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((DeliveryBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), getPreviousScene());
            ((DeliveryBoxController) loader.getController()).chooseOrder(order);
            list.getChildren().add(loader.getRoot());
        }
        username.setText(getManager().getLoggedInUser().getUserName());
        password.setText(getManager().getLoggedInUser().getPassword());
    }

    public void update(FXMLLoader loader) {
        myOrderTab.setContent(loader.getRoot());
    }


    public void logoutHandler(ActionEvent actionEvent) {
        getStage().close();
        getManager().logout();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RegisterAndLoginMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RegisterAndLoginMenuController) loader.getController()).initialize(getStage(), null, scene, null);
        super.getStage().setScene(scene);
        super.getStage().show();
    }

    public void backHandler(ActionEvent actionEvent) {
        getStage().close();
        getManager().logout();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RegisterAndLoginMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RegisterAndLoginMenuController) loader.getController()).initialize(getStage(), null, scene, null);
        super.getStage().setScene(scene);
        super.getStage().show();
    }
}
