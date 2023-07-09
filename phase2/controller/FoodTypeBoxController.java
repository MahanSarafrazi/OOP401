package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import phase2.model.Food;
import phase2.model.FoodType;
import phase2.view.Output;

import java.util.ArrayList;

public class FoodTypeBoxController extends MenuController {

    @FXML
    public Button edit;

    @FXML
    public TextField foodTypeText;

    private FoodType foodType;
    @FXML
    public void editHandler(ActionEvent actionEvent) {
       ArrayList<Food> foods = getManager().getLoggedInUser().getActiveRestaurant().getFoods();
       boolean isThereAnyFood = false;
        for (Food food : foods) {
            if(food.getType().equals(foodType)) {
                isThereAnyFood = true;
                break;
            }
        }

        if(!isThereAnyFood) {
            Output output = getManager().processEditFoodType(foodType.name(), foodTypeText.getText());
            if(output.equals(Output.SURE_EDIT_FOOD_TYPE)) {
                getManager().editFoodType(foodType.name(), foodTypeText.getText(), "yes");
            } else {
                foodTypeText.setStyle("-fx-background-color: red");
                PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
                hitAnimation.setOnFinished(e -> {
                    foodTypeText.setStyle("-fx-background-color: white");
                    foodTypeText.setText(foodType.name());
                });
                hitAnimation.playFromStart();
            }
        } else {
            foodTypeText.setStyle("-fx-background-color: red");
            PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
            hitAnimation.setOnFinished(e -> {
                foodTypeText.setStyle("-fx-background-color: white");
                foodTypeText.setText(foodType.name());
            });
            hitAnimation.playFromStart();
        }
        ((RestaurantMenuByOwnerController) getFatherStageController()).updateRestaurantInformation();
    }

    public void chooseType(FoodType foodType) {
        this.foodType = foodType;
        foodTypeText.setText(foodType.name());
    }

    public FoodType getFoodType() {
        return foodType;
    }
}
