package model;

public class Customer extends User {
    public static Customer newCustomer (String userName , String  pass) {
        Customer customer = new Customer (userName , pass);
        userList.customers.add(customer);
        return customer ;
    }
    private Customer (String userName , String pass) {
        this.userName = userName ;
        this.pass = pass ;
    }
    public Customer getCustomer (String pass) {
        for (Customer customer : userList.customers)
            if (customer.pass.equals(pass))
                return customer;
        return null;
    }
}
