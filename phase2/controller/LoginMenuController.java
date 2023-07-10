package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.RestaurantOwner;
import phase2.model.UserType;
import phase2.view.Output;

import java.io.IOException;

public class LoginMenuController extends MenuController {

    @FXML
    public TextField userName;

    @FXML
    public TextField passWord;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button forgetPassword;

    @FXML
    public Button back;

    @FXML
    public Text error;

    @FXML
    public ChoiceBox<String> type;

    @FXML
    public void backHandler() {
        back();
    }

    @FXML
    public void confirmHandler() {
        String username = this.userName.getText();
        String password = this.passWord.getText();
        String answer = "";
        Output output = null;

        if (type.getValue() == null) {
            answer = "You didn't choose a type";
            error.setText(answer);
        } else {
            if (type.getValue().equals("customer")) {
                output = super.getManager().logInUser(username, password, UserType.CUSTOMER);
                answer = OutputChecker.outputString(output);
            } else if (type.getValue().equals("restaurant owner")) {
                output = super.getManager().logInUser(username, password, UserType.RESTAURANT_OWNER);
                answer = OutputChecker.outputString(output);
            } else if (type.getValue().equals("deliverer")) {
                output = super.getManager().logInUser(username, password, UserType.DELIVERER);
                answer = OutputChecker.outputString(output);
            }
            error.setText(answer);
        }


        if(output == Output.SUCCESSFUL_LOGIN) {
            super.getStage().close();
            FXMLLoader loader ;
            if(type.getValue().equals("customer")) {
                loader = new FXMLLoader(this.getClass().getResource("../view/CustomerPanel.fxml"));
            } else if(type.getValue().equals("restaurant owner")) {
                loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantOwnerMenu.fxml"));
            } else {
                loader = new FXMLLoader(this.getClass().getResource("../view/DelivererMenu.fxml"));
            }
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(loader.getRoot());
            Stage stage = new Stage();
            ((MenuController) loader.getController()).initialize(stage, null, scene, null);
            if(type.getValue().equals("restaurant owner") &&
                    ((RestaurantOwner)(getManager().getLoggedInUser())).getRestaurants().size() == 1) {
                RestaurantOwner user = (RestaurantOwner)(getManager().getLoggedInUser());
                user.setActiveRestaurant(user.getRestaurants().get(0));
                FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("../view/RestaurantMenuByOwner.fxml"));
                try {
                    loader2.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene2 = new Scene(loader2.getRoot());
                ((RestaurantMenuByOwnerController) loader2.getController()).initialize(stage, (RestaurantOwnerMenuController) loader.getController(),
                      scene2, scene);
                ((RestaurantMenuByOwnerController) loader2.getController()).getStage().setScene(scene2);
                ((RestaurantMenuByOwnerController) loader2.getController()).getStage().show();
            } else {
                ((MenuController) loader.getController()).getStage().setScene(scene);
                ((MenuController) loader.getController()).getStage().show();
            }

        } else {
            PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
            hitAnimation.setOnFinished(e -> error.setText(""));
            hitAnimation.playFromStart();
        }
    }

    @FXML
    public void resetHandler() {
        userName.setText("");
        passWord.setText("");
        type.setValue(null);
    }

    @FXML
    public void forgetHandler() {
        String username = this.userName.getText();
        String answer ;
        Output output = super.getManager().getRestoreQuestion(username);
        answer = OutputChecker.outputString(output);
        error.setText(answer);
        if(output == Output.SHOW_RESTORE_QUESTION) {
            super.getStage().close();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestorePassword.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(loader.getRoot());
            ((RestorePasswordController) loader.getController()).initialize(getStage(), null, scene, getMainScene(),getManager().getUser(username));
            super.getStage().setScene(scene);
            super.getStage().show();
        } else {
            PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
            hitAnimation.setOnFinished(e -> error.setText(""));
            hitAnimation.playFromStart();
        }
    }
}