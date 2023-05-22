package model;

import java.util.ArrayList;

public class userList {
    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<RestaurantOwner> restaurantOwners = new ArrayList<>();
    static void setNewUser (Types type , User user) {
        switch (type) {
            case CUSTOMER -> customers.add((Customer) user);
            case RESTAURANT_OWNER -> restaurantOwners.add((RestaurantOwner) user);
        }
    }
}
