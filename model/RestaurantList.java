package model;

import java.util.ArrayList;

public class RestaurantList {
    public static final ArrayList<Restaurant> restaurants = new ArrayList<>();
    public static Restaurant getRestaurant(String name) {
        for (Restaurant restaurant : restaurants)
            if (restaurant.getName().equals(name))
                return restaurant;
        return null;
    }
}