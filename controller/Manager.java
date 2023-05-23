package controller;

import model.Admin;
import model.Customer;
import view.Inputs;

public class Manager {
    private Errors checkValidLogin (String userName , String pass , Inputs input) {
        Errors error = null ;
        if (input == Inputs.ADD_CUSTOMER) {
            Customer customer = Customer.getCustomerByUserName(userName);
            if (customer == null)
                return Errors.INVALID_USER_NAME;
            if (!customer.pass.equals(pass))
                return Errors.INVALID_PASSWORD;
            Customer.
        }
            error = Customer.checkValidLogin(userName,pass);
        //else if (input == Inputs.ADD_ADMIN)
        //    error = Admin.checkValidLogin(userName,pass);
        return error;
    }
    public static Errors checkValidLogin (String userName,String pass) {
        Customer customer = Customer.getCustomerByUserName(userName);
        if (customer == null)
            return Errors.INVALID_USER_NAME;
        if (!customer.pass.equals(pass))
            return Errors.INVALID_PASSWORD;
        else {
            loggedInCustomer = customer;
            return Errors.SUCCESSFUL_LOGIN;
        }
    }
}
