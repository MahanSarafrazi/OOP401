package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import phase2.model.Food;
import phase2.model.FoodType;
import phase2.model.Order;
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
       Output output = getManager().processEditFoodType(foodType.name(), foodTypeText.getText());

        if(!output.equals(Output.SURE_EDIT_FOOD_TYPE)) {
            foodTypeText.setStyle("-fx-background-color: red");
            PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
            hitAnimation.setOnFinished(e -> {
                foodTypeText.setStyle("-fx-background-color: white");
            });
            hitAnimation.playFromStart();
        } else {
            FoodType foodType1 = null;
            for (FoodType value : FoodType.values()) {
                if(value.commandingPattern.matcher(foodTypeText.getText()).find()) {
                    foodType1 = value;
                    break;
                }
            }

            boolean isThereFoodType = false;
            for (FoodType type : getManager().getLoggedInUser().getActiveRestaurant().getFoodType()) {
                if(type.equals(foodType1)) {
                    isThereFoodType = true;
                    break;
                }
            }

            if(!isThereFoodType) {
                getManager().editFoodType(foodType.name(), foodTypeText.getText(), "yes");
                foodType = foodType1;
            } else {
                foodTypeText.setStyle("-fx-background-color: red");
                PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
                hitAnimation.setOnFinished(e -> {
                    foodTypeText.setStyle("-fx-background-color: white");
                });
                hitAnimation.playFromStart();
            }
        }
        foodTypeText.setText(foodType.name());

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
