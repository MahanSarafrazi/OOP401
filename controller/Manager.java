package controller;

import model.Admin;
import model.Customer;
import model.User;
import view.Inputs;
import view.Output;

public class Manager {
    private static Output checkValidLogin (String userName , String pass , Inputs input) {
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
    }
}
