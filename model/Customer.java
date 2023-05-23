package model;

public class Customer extends User {
    public static Customer newCustomer (String userName , String  pass) {
        Customer customer = new Customer (userName , pass);
        UserList.getUserListInstance().getCustomers().add(customer);
        return customer;
    }
    private Customer (String userName , String pass) {super(userName, pass);}
}
