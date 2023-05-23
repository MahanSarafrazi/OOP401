package controller;

import model.*;
import view.Inputs;
import view.Output;

public class Manager {
    User loggedInUser = null ;
    private static Output checkValidLogin (String userName , String pass , Inputs input) {
        if (input == Inputs.LOGIN_CUSTOMER) {
            Customer customer = Customer.getCustomerByUserName(userName);
            if (customer == null)
                return Output.INVALID_USER_NAME;
            if (!customer.getPass().equals(pass))
                return Output.INVALID_PASSWORD;
        }
        else if (input == Inputs.LOGIN_DELIVERER) {
            Deliverer deliverer = Deliverer.getDelivererByUserName(userName);
            if (deliverer == null)
                return Output.INVALID_USER_NAME;
            if (!deliverer.getPass().equals(pass))
                return Output.INVALID_PASSWORD;
        }
        else if (input == Inputs.LOGIN_ADMIN) {
            Admin admin = Admin.getAdminByUserName(userName);
            if (admin == null)
                return Output.INVALID_USER_NAME;
            if (!admin.getPass().equals(pass))
                return Output.INVALID_PASSWORD;
        }
        else if (input == Inputs.LOGIN_RESTAURANT_OWNER) {
            RestaurantOwner restaurantOwner = RestaurantOwner.getRestaurantOwnerByUserName(userName);
            if (restaurantOwner == null)
                return Output.INVALID_USER_NAME;
            if (!restaurantOwner.getPass().equals(pass))
                return Output.INVALID_PASSWORD;
        }
        else if (input == Inputs.ADD_CUSTOMER) {
            Customer customer = Customer.getCustomerByUserName(userName);
            if (customer != null)
                return Output.INVALID_USER_NAME;
        }
        else if (input == Inputs.ADD_DELIVERER) {
            Deliverer deliverer = Deliverer.getDelivererByUserName(userName);
            if (deliverer != null)
                return Output.INVALID_USER_NAME;
        }
        else if (input == Inputs.ADD_ADMIN) {
            Admin admin = Admin.getAdminByUserName(userName);
            if (admin != null)
                return Output.INVALID_USER_NAME;
        }
        else if (input == Inputs.ADD_RESTAURANT_OWNER) {
            RestaurantOwner restaurantOwner = RestaurantOwner.getRestaurantOwnerByUserName(userName);
            if (restaurantOwner != null)
                return Output.INVALID_USER_NAME;
        }
        return Output.SUCCESSFUL_LOGIN;
    }
    //sample
    private void sampleLogin (String userName , String pass , Inputs input) {
        Output output = checkValidLogin(userName,pass,input) ;
        if (output == Output.SUCCESSFUL_LOGIN) {
            if (input == Inputs.LOGIN_ADMIN) {
                loggedInUser = Admin.getAdminByUserName(userName);
            }
            else if (input == Inputs.ADD_ADMIN) {
                loggedInUser = Admin.newAdmin(userName,pass);
            }
            //open new menu at the end of this
        }
        else if (output == Output.INVALID_PASSWORD) {
            System.out.println("invalid password !");
            //open last menu at the end of this
        }
    }
}
