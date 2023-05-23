package controller;

import model.*;
import view.Output;

public class Manager {
    User loggedInUser ;
    public Output addCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if(customer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = Customer.newCustomer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addAdmin(String username, String password) {
        for (Admin admin : UserList.getUserListInstance().getAdmins()) {
            if(admin.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = Admin.newAdmin(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if(deliverer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = Deliverer.newDeliverer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if(restaurantOwner.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = RestaurantOwner.newRestaurantOwner(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }
    public Output logInCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if(customer.getUserName().equals(username)) {
                if(customer.getPassword().equals(password)) {
                    loggedInUser = customer;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    public Output logInAdmin(String username, String password) {
        for (Admin admin : UserList.getUserListInstance().getAdmins()) {
            if(admin.getUserName().equals(username)) {
                if(admin.getPassword().equals(password)) {
                    loggedInUser = admin;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    public Output logInDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if(deliverer.getUserName().equals(username)) {
                if(deliverer.getPassword().equals(password)) {
                    loggedInUser = deliverer;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    public Output logInRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if(restaurantOwner.getUserName().equals(username)) {
                if(restaurantOwner.getPassword().equals(password)) {
                    loggedInUser = restaurantOwner;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
}
