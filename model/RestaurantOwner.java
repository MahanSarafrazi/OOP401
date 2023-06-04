package model;

import java.util.ArrayList;

public class RestaurantOwner extends User {
    public static RestaurantOwner newRestaurantOwner (String userName , String  pass) {
        RestaurantOwner restaurantOwner = new RestaurantOwner (userName , pass);
        UserList.getUserListInstance().getRestaurantOwners().add(restaurantOwner);
        return restaurantOwner ;
    }
    private final ArrayList<Restaurant> restaurants;
    private RestaurantOwner (String userName , String pass) {
        super(userName, pass);
        restaurants = new ArrayList<>();
    }
    public void AddRestaurant(String name, FoodType foodType, int location) {
        restaurants.add(new Restaurant(name, foodType, location));
    }
    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }
    @Override
    public boolean setActiveRestaurant(int ID) {
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getID() == ID) {
                activeRestaurant = restaurant;
                return true;
            }
        }
        return false;
    }
}
