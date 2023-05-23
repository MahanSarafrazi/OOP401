package model;

import controller.Errors;

public class Customer extends User {
    private static Customer loggedInCustomer ;
    public Customer getLoggedInCustomer() {return loggedInCustomer;}
    public static Customer newCustomer (String userName , String  pass) {
        Customer customer = new Customer (userName , pass);
        userList.customers.add(customer);
        return customer ;
    }
    private Customer (String userName , String pass) {super(userName, pass);
    }
    private static Customer getCustomerByUserName (String userName) {
        for (Customer customer : userList.customers)
            if (customer.userName.equals(userName))
                return customer;
        return null;
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
