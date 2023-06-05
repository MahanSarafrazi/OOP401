package model;

import java.util.ArrayList;

public class UserList {
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<Deliverer> deliverers = new ArrayList<>();
    private final ArrayList<RestaurantOwner> restaurantOwners = new ArrayList<>();
    private UserList() {}
    private static UserList UserListInstance;

    public static void setUserListInstance(UserList userListInstance) {
        UserListInstance = userListInstance;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Deliverer> getDeliverers() {
        return deliverers;
    }

    public ArrayList<RestaurantOwner> getRestaurantOwners() {
        return restaurantOwners;
    }

    public static UserList getUserListInstance() {
        if(UserListInstance == null) {
            UserListInstance = new UserList();
        }
        return UserListInstance;
    }
}

