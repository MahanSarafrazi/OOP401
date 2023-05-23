package controller;

import model.Admin;
import model.Customer;
import view.Inputs;

public class Manager {
    private Errors checkValidLogin (String userName , String pass , Inputs input) {
        Errors error = null ;
        if (input == Inputs.ADD_CUSTOMER)
            error = Customer.checkValidLogin(userName,pass);
        //else if (input == Inputs.ADD_ADMIN)
        //    error = Admin.checkValidLogin(userName,pass);
        return error;
    }
}
