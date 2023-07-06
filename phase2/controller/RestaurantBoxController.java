package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import phase2.model.Food;
import phase2.model.FoodType;

import java.io.IOException;

public class RestaurantBoxController extends MenuController {

    @FXML
    public Button buttonBOX;

    @FXML
    public ImageView imageView;

    @FXML
    public TextField name;

    @FXML
    public TextField foodType;

    private int ID;

    public void chooseRestaurant(String name, FoodType foodType, int ID) {
        this.name.setText(name);
        this.foodType.setText(foodType.name());
        this.ID = ID;
    }

    public void buttonBOXHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().setActiveRestaurant(ID);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Restaurantmenubyowner.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        getStage().setScene(scene);
        ((RestaurantMenuByOwnerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
        getStage().show();
    }
}
