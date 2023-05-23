package controller;

import model.*;
import view.Inputs;
import view.Output;

public class Manager {
    /*private static Output checkValidLogin (String userName , String pass , Inputs input) {
        Output output ;
        if (input == Inputs.LOGIN_CUSTOMER) {
            Customer customer = Customer.getCustomerByUserName(userName);
            if (customer == null)
                return Output.INVALID_USER_NAME;
            if (!customer.getPass().equals(pass))
                return Output.INVALID_PASSWORD;
        }
        else if (input == Inputs.LOGIN_ADMIN) {
            Admin admin = Admin.getAdminByUserName(userName);
            if (admin == null)
                return Output.INVALID_USER_NAME;
            if (!admin.getPass().equals(pass))
                return Output.INVALID_PASSWORD;
        }
        return Output.SUCCESSFUL_LOGIN;
    }
    //sample
    public static void start () {
        Inputs input = Inputs.ADD_ADMIN;
        User user = null ;
        if (outputMethod(checkValidLogin("fariman","passF",input))) {
            if (input == Inputs.ADD_ADMIN)
                user = Admin.getAdminByUserName("fariman");
        }
        // further in app
        if (user instanceof Admin)
            System.out.println("hi Admin !");
    }
    private static boolean outputMethod (Output output) {
        switch (output) {
            case INVALID_PASSWORD -> {
                System.out.println("pass error");
                return false ;
            }
            case SUCCESSFUL_LOGIN -> {
                System.out.println("success");
                return true;
            }
        }
        return true ;
    }*/
    public Output addCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if(customer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        Customer.newCustomer(username, password);
        return Output.INVALID_PASSWORD;
    }

    public Output addAdmin(String username, String password) {
        for (Admin admin : UserList.getUserListInstance().getAdmins()) {
            if(admin.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        Admin.newAdmin(username, password);
        return Output.INVALID_PASSWORD;
    }

    public Output addDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if(deliverer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        Deliverer.newDeliverer(username, password);
        return Output.INVALID_PASSWORD;
    }

    public Output addRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if(restaurantOwner.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        RestaurantOwner.newRestaurantOwner(username, password);
        return Output.INVALID_PASSWORD;
    }
}
