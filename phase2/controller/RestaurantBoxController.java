package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import phase2.model.Food;
import phase2.model.FoodType;
import phase2.model.Restaurant;
import phase2.model.RestaurantOwner;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantBoxController extends MenuController {

    @FXML
    public Button buttonBOX;

    @FXML
    public ImageView imageView;

    @FXML
    public TextField name;

    @FXML
    public TextField foodType;

    @FXML
    public Rectangle photoPlace;

    private int ID;

    public void chooseRestaurant(String name, FoodType foodType, int ID) {
        this.name.setText(name);
        this.foodType.setText(foodType.name());
        this.ID = ID;
    }

    public void buttonBOXHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().setActiveRestaurant(ID);
        if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
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
        else {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantMenuByCustomer.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(loader.getRoot());
            getStage().setScene(scene);
            ((RestaurantMenuByCustomerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
            getStage().show();
        }
    }

    public void choosePics() {
        for (Restaurant restaurant : ((RestaurantOwner) getManager().getLoggedInUser()).getRestaurants()) {
            if(restaurant.getID() == ID) {
                if(restaurant.getPhotoPath() != null) {
                    Image image = new Image(restaurant.getPhotoPath(), photoPlace.getWidth(), photoPlace.getHeight(), false, false);
                    imageView.setImage(image);
                }
            }
        }
    }
    public void choosePics(ArrayList<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getID() == ID) {
                if(restaurant.getPhotoPath() != null) {
                    System.out.println(restaurant.getName());
                    Image image = new Image(restaurant.getPhotoPath(), photoPlace.getWidth(), photoPlace.getHeight(), false, false);
                    imageView.setImage(image);
                }
            }
        }
    }
}
