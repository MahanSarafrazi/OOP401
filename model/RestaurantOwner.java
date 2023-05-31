package model;

import java.util.ArrayList;

public class RestaurantOwner extends User {
    public static RestaurantOwner newRestaurantOwner (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        UserList.getUserListInstance().getRestaurantOwners().add(restaurantOwner);
        return restaurantOwner ;
    }
    private final ArrayList<Restaurant> restaurants;
    private Restaurant activeRestaurant = null;
    private RestaurantOwner (String userName , String pass) {
        super(userName, pass);
        restaurants = new ArrayList<>();
    }
    public void AddRestaurant(String name) {
        restaurants.add(new Restaurant(name));
    }
    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Restaurant getActiveRestaurant() {
        return activeRestaurant;
    }

    public boolean editActiveRestaurant(long ID) {
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getID() == ID) {
                activeRestaurant = restaurant;
                return true;
            }
        }
        return false;
    }
    public void editActiveRestaurant(Restaurant restaurant) {
        this.activeRestaurant = restaurant;
    }
}
