package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import phase2.model.Food;
import phase2.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantsMenuController extends MenuController{
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene,ArrayList<Restaurant> restaurants) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        FXMLLoader restaurantLoader;
        for (int i = 3; i < restaurants.size() + 3; ++i) {
            restaurantLoader = new FXMLLoader(this.getClass().getResource("../view/boxRestaurantByOwner.fxml"));
            try {
                restaurantLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((RestaurantBoxController) restaurantLoader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
            ((RestaurantBoxController) restaurantLoader.getController()).chooseRestaurant(restaurants.get(i-3).getName(), restaurants.get(i-3).getFoodType().get(0), restaurants.get(i-3).getID());
            ((RestaurantBoxController) restaurantLoader.getController()).choosePics(restaurants);
            this.gridPane.add(restaurantLoader.getRoot(),i%3,i/3,1,1);
        }
    }
    public void initialize2(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene,ArrayList<Food> foods) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        FXMLLoader restaurantLoader;
        for (int i = 3; i < foods.size() + 3; ++i) {
            FXMLLoader foodLoader = new FXMLLoader(this.getClass().getResource("../view/boxFoodByCustomer.fxml"));
            try {
                foodLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((FoodBoxController) foodLoader.getController()).initialize(getStage(), this, getMainScene(), null);
            ((FoodBoxController) foodLoader.getController()).chooseFood(foods.get(i - 3), true);
            this.gridPane.add(foodLoader.getRoot(),i%3,i/3,1,1);
        }
    }
    @FXML
    public GridPane gridPane;

    @FXML
    public void backHandler() {back();}
}
