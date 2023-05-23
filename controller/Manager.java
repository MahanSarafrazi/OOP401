package controller;

import model.*;
import view.Inputs;
import view.Output;

public class Manager {
    public Output addCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if(customer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        Customer.newCustomer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addAdmin(String username, String password) {
        for (Admin admin : UserList.getUserListInstance().getAdmins()) {
            if(admin.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        Admin.newAdmin(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if(deliverer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        Deliverer.newDeliverer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if(restaurantOwner.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        RestaurantOwner.newRestaurantOwner(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }
}
