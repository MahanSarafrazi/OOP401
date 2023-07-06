package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.Restaurant;
import phase2.model.RestaurantOwner;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantOwnerMenuController extends MenuController {

    @FXML
    public Button logout;
    @FXML
    public Button AddNewRestaurant;

    @FXML
    public TextField password;

    @FXML
    public TextField username;

    @FXML
    public TextField restoreQuestion;

    @FXML
    public TextField restoreSolve;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public TextField search;

    @FXML
    public Text error;

    @FXML
    public VBox list;

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        username.setText(getManager().getLoggedInUser().getUserName());
        password.setText(getManager().getLoggedInUser().getPassword());
        update();
    }
    
    public void update() {
        FXMLLoader loader;
        list.getChildren().clear();
        ArrayList<Restaurant> restaurants = ((RestaurantOwner) getManager().getLoggedInUser()).getRestaurants();
        for (int i = 0; i < restaurants.size(); ++i) {
            loader = new FXMLLoader(this.getClass().getResource("../view/boxRestaurantbyowner.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((RestaurantBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), null, null);
            ((RestaurantBoxController) loader.getController()).chooseRestaurant(restaurants.get(i).getName(), restaurants.get(i).getFoodType().get(0));
            list.getChildren().add(loader.getRoot());
        }
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


    public void addNewRestaurantHandler(ActionEvent actionEvent) {
        FXMLLoader addLoader = new FXMLLoader(this.getClass().getResource("../view/addNewRestaurant.fxml"));
        try {
            addLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(addLoader.getRoot());
        Stage stage = new Stage();
        stage.setTitle("add restaurant");
        stage.setScene(scene);
        ((addNewRestaurantController) addLoader.getController()).initialize(stage, this, scene, null);
        ((addNewRestaurantController) addLoader.getController()).getStage().show();
    }
}
