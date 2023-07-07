package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            restaurants = getManager().normalSearch(searchField.getText(),FoodType.valueOf(restaurantType.getValue()));
        else if (searchType.getValue().equals("near restaurants"))
            restaurants = getManager().searchForNearRestaurants(location,restaurantType.getValue(), searchField.getText());
        else if (searchType.getValue().equals("favorite restaurants"))
            restaurants = getManager().favoriteRestaurants(restaurantType.getValue(), searchField.getText());
        else
            restaurants = getManager().normalSearch(searchField.getText(),FoodType.valueOf(restaurantType.getValue()));
        openRestaurantsMenu(restaurants);
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
        getStage().close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Cart.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((CartController) loader.getController()).initialize(getStage(), null, scene, getPreviousScene());
        FXMLLoader foodLoader;
        Customer customer = (Customer) getManager().getLoggedInUser();
        for (int i = 0 ; i < customer.getCart().getFoods().size() ; ++i) {
            foodLoader = new FXMLLoader(this.getClass().getResource("../view/boxRestaurantbyowner.fxml"));
            try {
                foodLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((FoodBoxController) foodLoader.getController()).initialize(getStage(), getFatherStageController(), null, null);
            ((FoodBoxController) foodLoader.getController()).chooseFood(customer.getCart().getFoods().get(i).getName(),
                    customer.getCart().getFoods().get(i).getType(), customer.getCart().getFoods().get(i).getDiscountedPrice(),customer.getCart().getFoods().get(i).getID());
            ((CartController) loader.getController()).vBox.getChildren().add(foodLoader.getRoot());
        }
        super.getStage().setScene(scene);
        super.getStage().show();
    }

    @FXML
    public void iranianHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.IRANIAN);
        openRestaurantsMenu(restaurants);
    }

    @FXML
    public void fastHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.FAST_FOOD);
        openRestaurantsMenu(restaurants);
    }

    @FXML
    public void drinkHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.DRINK);
        openRestaurantsMenu(restaurants);
    }

    @FXML
    public void dessertHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.DESSERT);
        openRestaurantsMenu(restaurants);
    }

    @FXML
    public void turkishHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.TURKISH);
        openRestaurantsMenu(restaurants);
    }

    @FXML
    public void interNationalHandler() {
        ArrayList<Restaurant> restaurants = getManager().typeSearch(location, FoodType.INTERNATIONAL_FOOD);
        openRestaurantsMenu(restaurants);
    }
    private void openRestaurantsMenu(ArrayList<Restaurant> restaurants) {
        super.getStage().close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantsMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RestaurantsMenuController) loader.getController()).initialize(getStage(), null, scene, getMainScene());
        FXMLLoader restaurantLoader;
        for (int i = 3; i < restaurants.size() + 3; ++i) {
            restaurantLoader = new FXMLLoader(this.getClass().getResource("../view/boxRestaurantbyowner.fxml"));
            try {
                restaurantLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((RestaurantBoxController) restaurantLoader.getController()).initialize(getStage(), getFatherStageController(), null, null);
            ((RestaurantBoxController) restaurantLoader.getController()).chooseRestaurant(restaurants.get(i-3).getName(), restaurants.get(i-3).getFoodType().get(0), restaurants.get(i-3).getID());
            ((RestaurantsMenuController) loader.getController()).gridPane.add(restaurantLoader.getRoot(),i%3,i/3,1,1);
        }
        super.getStage().setScene(scene);
        super.getStage().show();
    }

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
        if (!restoreSolve.getText().isEmpty() && !restoreQuestion.getText().isEmpty() )
            getManager().setRestore(restoreQuestion.getText(),restoreSolve.getText());
        else
            getManager().setRestore(null,null);
        customer.charge(charge);
        this.accountCharge.setText(String.valueOf(customer.getCharge()));
        chargeBox.setValue(null);
    }
}

