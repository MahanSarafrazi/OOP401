package model;

import java.util.ArrayList;

public class userList {
    static final ArrayList<Customer> customers = new ArrayList<>();
    static final ArrayList<Deliverer> deliverers = new ArrayList<>();
    static final ArrayList<Admin> admins = new ArrayList<>();
    static final ArrayList<RestaurantOwner> restaurantOwners = new ArrayList<>();
    public static void setDataBase () {
        System.out.println("Set successfully !");
    }
    public static void getDatabase () {
        System.out.println("Got successfully !");
    }
}
