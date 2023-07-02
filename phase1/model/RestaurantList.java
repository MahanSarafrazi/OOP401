package phase1.model;

import java.util.ArrayList;

public class RestaurantList {
    public static final ArrayList<Restaurant> restaurants = new ArrayList<>();
    public static Restaurant getRestaurant(int ID) {
        for (Restaurant restaurant : restaurants)
            if (restaurant.getID() == ID)
                return restaurant;
        return null;
    }
}