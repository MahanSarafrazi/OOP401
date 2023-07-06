package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import phase2.model.Food;
import phase2.model.FoodType;

public class RestaurantBoxController extends MenuController {

    @FXML
    public Button buttonBOX;

    @FXML
    public ImageView imageView;

    @FXML
    public Label name;

    @FXML
    public Label foodType;

    public void chooseRestaurant(String name, FoodType foodType) {
        this.name.setText(name);
        this.foodType.setText(foodType.name());
    }
}
