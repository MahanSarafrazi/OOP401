package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.Food;
import phase2.view.Output;

public class FoodMenuByOwnerController extends MenuController {

    @FXML
    public TextField name;

    @FXML
    public TextField price;

    @FXML
    public TextField discount;

    @FXML
    public CheckBox activationDiscount;

    @FXML
    public Button seeComments;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button logout;

    @FXML
    public Button selectFood;

    @FXML
    public TextField score;

    @FXML
    public ChoiceBox addtype;

    @FXML
    public TextField type;

    @FXML
    public Text nameError;

    @FXML
    public Text priceError;

    @FXML
    public Text typeError;

    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getID();

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        Food openedFood = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        name.setText(openedFood.getName());
        type.setText(openedFood.getType().name());
        price.setText(Double.toString(openedFood.getPrice()));
    }


    public void backHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().getActiveRestaurant().closeFood();
        back();
    }


    public void confirmHandler(ActionEvent actionEvent) {
        Food food = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();

        Output editNameResult = getManager().editFoodName(food.getID(), name.getText());
        if(editNameResult.equals(Output.FOOD_NAME_EDITED)) {
            nameError.setFill(Paint.valueOf("green"));
        } else {
            nameError.setFill(Paint.valueOf("red"));
            name.setText(food.getName());
        }
        nameError.setText(OutputChecker.outputString(editNameResult));

        try {
            Output editPriceResult = getManager().editFoodPrice(food.getID(), Double.parseDouble(price.getText()));
            if(editPriceResult.equals(Output.FOOD_PRICE_EDITED)) {
                priceError.setFill(Paint.valueOf("green"));
            } else {
                priceError.setFill(Paint.valueOf("red"));
                price.setText(Double.toString(food.getPrice()));
            }
            priceError.setText(OutputChecker.outputString(editPriceResult));
        } catch (Exception e) {
            priceError.setFill(Paint.valueOf("red"));
            priceError.setText("invalid Price");
            price.setText(Double.toString(food.getPrice()));
        }


        /*Output editTypeResult = getManager().processEditFoodType(food.getType().name(), type.getText());
        if(editTypeResult.equals(Output.SURE_EDIT_FOOD_TYPE)) {
            Output output = getManager().editFoodType(food.getType().name(), type.getText(), "yes");
            typeError.setFill(Paint.valueOf("green"));
            typeError.setText(OutputChecker.outputString(output));
        } else {
            typeError.setFill(Paint.valueOf("red"));
            typeError.setText(OutputChecker.outputString(editTypeResult));
        }
        type.setText(food.getType().name());*/

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> {
            nameError.setText("");
            priceError.setText("");
            //typeError.setText("");
        });
        hitAnimation.playFromStart();
    }
}
