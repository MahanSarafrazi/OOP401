package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import phase2.model.FoodType;
import phase2.view.Output;

public class addFoodController extends MenuController {

    @FXML
    public TextField foodName;

    @FXML
    public TextField foodType;

    @FXML
    public TextField foodPrice;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Text error;


    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        try {
            Output output = getManager().addFood(foodName.getText(), Double.parseDouble(foodPrice.getText()), foodType.getText());
            if(output.equals(Output.FOOD_ADDED)) {
                error.setFill(Paint.valueOf("green"));

                FoodType foodType1 = null;
                for (FoodType value : FoodType.values()) {
                    if (value.commandingPattern.matcher(foodType.getText()).find()) {
                        foodType1 = value;
                    }
                }

                boolean contains = false;
                for (FoodType type : getManager().getLoggedInUser().getActiveRestaurant().getFoodType()) {
                    if(type.equals(foodType1)) {
                        contains = true;
                        break;
                    }
                }

                if(!contains) {
                    getManager().getLoggedInUser().getActiveRestaurant().getFoodType().add(foodType1);
                }

            } else {
                error.setFill(Paint.valueOf("red"));
            }
            error.setText(OutputChecker.outputString(output));
        } catch (Exception e) {
            error.setFill(Paint.valueOf("red"));
            error.setText("invalid price");
        }

        ((RestaurantMenuByOwnerController) getFatherStageController()).update();

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }


    @FXML
    public void resetHandler(ActionEvent actionEvent) {

    }


}
