package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.Deliverer;
import phase2.model.Order;
import phase2.view.Output;

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

    @FXML
    public Button setLocation;

    @FXML
    public AnchorPane defaultTab;

    @FXML
    public Text error;

    private AnchorPane myOrder;

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

        if(((Deliverer) getManager().getLoggedInUser()).hasOrder()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/myOrderDelivery.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((MyOrderController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
            ((MyOrderController) loader.getController()).chooseOrder(((Deliverer) getManager().getLoggedInUser()).getOrder());
            setMyOrder(loader.getRoot());
        }

        username.setText(getManager().getLoggedInUser().getUserName());
        password.setText(getManager().getLoggedInUser().getPassword());
        update();
    }

    public void update() {
        if(((Deliverer) getManager().getLoggedInUser()).hasOrder()) {
            myOrderTab.setContent(myOrder);
        } else {
            myOrderTab.setContent(defaultTab);
        }
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

    @FXML
    public void setLocationHandler(ActionEvent actionEvent) {
        ((Deliverer) getManager().getLoggedInUser()).setLocation(1);
    }

    public AnchorPane getMyOrder() {
        return myOrder;
    }

    public void setMyOrder(AnchorPane myOrder) {
        this.myOrder = myOrder;
    }

    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        if(restoreQuestion.getText().equals("")) {
            error.setFill(Paint.valueOf("red"));
            error.setText("The restore question is empty");
        } else if(restoreSolve.getText().equals("")) {
            error.setFill(Paint.valueOf("red"));
            error.setText("The restore solve is empty");
        } else {
            getManager().setRestore(restoreQuestion.getText(), restoreSolve.getText());
            error.setFill(Paint.valueOf("green"));
            error.setText("The restore question set successfully");
            restoreQuestion.setText("");
            restoreSolve.setText("");
        }
        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }

    @FXML
    public void resetHandler(ActionEvent actionEvent) {
        restoreQuestion.setText("");
        restoreSolve.setText("");
    }
}
