package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import phase2.model.FoodType;
import phase2.model.RestaurantOwner;

import java.io.IOException;

public class FoodBoxController extends MenuController {

    @FXML
    public TextField foodName;

    @FXML
    public TextField foodType;

    @FXML
    public TextField foodPrice;

    @FXML
    public Button buttonBOX;

    private int ID;

    @FXML
    public void buttonBOXHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().getActiveRestaurant().setOpenedFood(ID);
        if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Foodmenubyowner.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(loader.getRoot());
            getStage().setScene(scene);
            ((FoodMenuByOwnerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
            getStage().show();
        }
    }

    @FXML
    public void chooseFood(String name, FoodType foodType, double price, int ID) {
        this.foodName.setText(name);
        this.foodType.setText(foodType.name());
        this.foodPrice.setText(Double.toString(price));
        this.ID = ID;
    }
}
