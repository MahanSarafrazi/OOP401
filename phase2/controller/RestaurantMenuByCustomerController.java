package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase2.model.Restaurant;

public class RestaurantMenuByCustomerController extends MenuController {
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        this.name.setText(restaurant.getName());
        this.name.setEditable(false);
        this.type.setText(restaurant.getFoodType().toString());
        this.type.setEditable(false);
        this.ID.setText(String.valueOf(restaurant.getID()));
        this.ID.setEditable(false);
        this.score.setText(getManager().averageRating());
        this.score.setEditable(false);
    }

    @FXML
    public TextField name;

    @FXML
    public TextField type;

    @FXML
    public TextField ID;

    @FXML
    public TextField score;

    @FXML
    public ChoiceBox<String> scoreBox;

    @FXML
    public void backHandler() {
        back();
    }

    @FXML
    public void confirmHandler() {
        if (scoreBox.getValue() == null) {
            score.setText("NO RATING!");
            score.setStyle("-fx-text-fill: red");
        }
        else {
            getManager().addRating(Double.parseDouble(scoreBox.getValue()));
            score.setText(getManager().averageRating());
            score.setStyle("-fx-text-fill: black");
        }
    }

    @FXML
    public void commentHandler() {

    }
}
