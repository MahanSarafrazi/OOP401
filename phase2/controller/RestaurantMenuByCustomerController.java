package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phase2.model.Food;
import phase2.model.FoodType;
import phase2.model.Restaurant;

import java.io.IOException;

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
        for (FoodType foodType : restaurant.getFoodType()) {
            VBox vBox = new VBox();
            for (Food food : restaurant.getFoods()) {
                if (food.getType() == foodType) {
                    FXMLLoader foodLoader = new FXMLLoader(this.getClass().getResource("../view/boxFoodByCustomer.fxml"));
                    try {
                        foodLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ((FoodBoxController) foodLoader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
                    ((FoodBoxController) foodLoader.getController()).chooseFood(food.getName(), food.getType(), food.getDiscountedPrice(), food.getID());
                    vBox.getChildren().add(foodLoader.getRoot());

                }
            }
            ScrollPane scrollPane = new ScrollPane(vBox);
            Tab tab = new Tab(foodType.toString(),scrollPane) ;
            tabPane.getTabs().add(tab);
        }
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
    public TabPane tabPane;

    @FXML
    public void backHandler() {
        getManager().getLoggedInUser().deActiveRestaurant();
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
