package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import phase2.model.Customer;
import phase2.model.FoodType;
import phase2.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerMenuController extends MenuController {
    @FXML
    public ComboBox<String> searchType;

    int location = 50;

    @FXML
    public ComboBox<String> restaurantType;

    @FXML
    public TextField searchField;

    @FXML
    public TextField totalSpending;

    @FXML
    public TextField username;

    @FXML
    public TextField password;

    @FXML
    public TextField restoreQuestion;

    @FXML
    public TextField restoreSolve;

    @FXML
    public TextField accountCharge;

    @FXML
    public ChoiceBox<String> chargeBox;

    @FXML
    public void searchHandler() {
        ArrayList<Restaurant> restaurants ;
        if (searchType.getValue() == null)
            restaurants = getManager().normalSearch(searchField.getText());
        else if (searchType.getValue().equals("near restaurants"))
            restaurants = getManager().searchForNearRestaurants(location,restaurantType.getValue(), searchField.getText());
        else if (searchType.getValue().equals("favorite restaurants"))
            restaurants = getManager().favoriteRestaurants(restaurantType.getValue(), searchField.getText());
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
        FXMLLoader foodLoader;
        for (int i = 3; i < restaurants.size() + 3; ++i) {
            foodLoader = new FXMLLoader(this.getClass().getResource("../view/boxRestaurantbyowner.fxml"));
            try {
                foodLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((RestaurantBoxController) foodLoader.getController()).initialize(getStage(), getFatherStageController(), null, null);
            ((RestaurantBoxController) foodLoader.getController()).chooseRestaurant(restaurants.get(i-3).getName(), restaurants.get(i-3).getFoodType().get(0));
            ((RestaurantsMenuController) loader.getController()).gridPane.add(foodLoader.getRoot(),i%3,i/3,1,1);
        }
        super.getStage().setScene(scene);
        super.getStage().show();
    }
    //...

    @FXML
    public void resetHandler() {
        chargeBox.setValue(null);
        restoreQuestion.setText("");
        restoreSolve.setText("");
    }

    @FXML
    public void confirmHandler() {
        Customer customer = (Customer) getManager().getLoggedInUser();
        int charge = 0;
        if (chargeBox.getValue() != null) {
            String [] word = chargeBox.getValue().split(" ");
            charge = Integer.parseInt(word[0]);
        }
        getManager().setRestore(restoreQuestion.getText(),restoreSolve.getText());
        customer.charge(charge);
        this.accountCharge.setText(String.valueOf(customer.getCharge()));
        chargeBox.setValue(null);
    }
}

