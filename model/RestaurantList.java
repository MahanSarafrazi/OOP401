package model;

import java.util.ArrayList;

public class RestaurantList {
    static final ArrayList<Restaurant> restaurants = new ArrayList<>();

    //singleton
    private RestaurantList(){}
    private static RestaurantList restaurantUserInstance;
    public static RestaurantList getRestaurantUserInstance() {
        if(restaurantUserInstance == null) {
            restaurantUserInstance = new RestaurantList();
        }
        return restaurantUserInstance;
    }

    public static void setRestaurantUserInstance(RestaurantList restaurantUserInstance) {
        RestaurantList.restaurantUserInstance = restaurantUserInstance;
    }
}
