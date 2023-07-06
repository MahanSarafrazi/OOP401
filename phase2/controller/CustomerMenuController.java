package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import phase2.model.FoodType;
import phase2.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerMenuController extends MenuController {
    @FXML
    public ComboBox searchType;

    int location = 50;

    @FXML
    public ComboBox restaurantType;

    @FXML
    public TextField searchField;

    @FXML
    public void searchHandler() {
        ArrayList<Restaurant> restaurants ;
        if (searchType.getValue() == null)
            restaurants = getManager().normalSearch(searchField.getText());
        else if (searchType.getValue().equals("near restaurants"))
            restaurants = getManager().searchForNearRestaurants(location,restaurantType.getValue().toString(), searchField.getText());
        else if (searchType.getValue().equals("favorite restaurants"))
            restaurants = getManager().favoriteRestaurants(restaurantType.getValue().toString(), searchField.getText());
        else
            restaurants = getManager().normalSearch(searchField.getText());
        super.getStage().close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantsMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RestaurantsMenuController) loader.getController()).initialize(getStage(), null, scene, getMainScene());
        ((RestaurantsMenuController) loader.getController()).restaurants = restaurants;
        super.getStage().setScene(scene);
        super.getStage().show();
    }


    @FXML
    public void resetHandler1() {
        searchType.setValue(null);
        restaurantType.setValue(null);
        searchField.setText(null);
    }

    @FXML
    public void logoutHandler() {
        getStage().close();
        getManager().logout();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RegisterAndLoginMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RegisterAndLoginMenuController) loader.getController()).initialize(getStage(), null, scene, null);
        super.getStage().setScene(scene);
        super.getStage().show();
    }

    @FXML
    public void cartHandler() {

    }

    @FXML
    public void iranianHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.IRANIAN);
        super.getStage().close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantsMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RestaurantsMenuController) loader.getController()).initialize(getStage(), null, scene, getMainScene());
        ((RestaurantsMenuController) loader.getController()).restaurants = restaurants;
        super.getStage().setScene(scene);
        super.getStage().show();
    }
    //...

    @FXML
    public void resetHandler() {

    }
}

