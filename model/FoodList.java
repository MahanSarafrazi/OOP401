package model;

import java.util.ArrayList;

public class FoodList {
    static final ArrayList<Restaurant> restaurants = new ArrayList<>();
    static ArrayList<ArrayList<FoodType>> makeArrayListOfFoodType () {
        ArrayList<FoodType> restaurantFoodTypes = new ArrayList<>();
        for (Restaurant restaurant : restaurants)
            restaurantFoodTypes.add(restaurant.foodTypes());
    }
}
