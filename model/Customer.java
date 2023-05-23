package model;

public class Customer extends User {
    public static Customer newCustomer (String userName , String  pass) {
        Customer customer = new Customer (userName , pass);
        UserList.getUserListInstance().getCustomers().add(customer);
        return customer;
    }
    private Customer (String userName , String pass) {super(userName, pass);}
    public static Customer getCustomerByUserName (String userName) {
        for (Customer customer : UserList.getUserListInstance().getCustomers())
            if (customer.userName.equals(userName))
                return customer;
        return null;
    }
}
