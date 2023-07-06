package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import phase2.model.Food;

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
    public Button login;

    @FXML
    public Button selectFood;

    @FXML
    public TextField score;

    @FXML
    public ChoiceBox addtype;

    @FXML
    public TextField type;

    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getID();

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        Food openedFood = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        name.setText(openedFood.getName());
        type.setText(openedFood.getType().name());
        price.setText(Double.toString(openedFood.getPrice()));
    }
}
