package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Customer extends User {
    public static Customer newCustomer(String userName, String pass) {
        Customer customer = new Customer(userName, pass);
        UserList.getUserListInstance().getCustomers().add(customer);
        return customer;
    }

    private Customer(String userName, String pass) {
        super(userName, pass);
        orders=new ArrayList<>();
        cart=new LinkedHashMap<>();
    }

    private final ArrayList<Order> orders ;
    public ArrayList<Order> getOrders() {
        for (Order order : orders) {
            order.correctOrderStatus();
        }
        return orders;
    }
    public void addOrder(Order order) {
        orders.add(order);
        this.cart.clear();
    }
    private final LinkedHashMap<Food,Integer> cart;
    public LinkedHashMap<Food,Integer> getCart() {return cart;}
    private double charge;
    public double getCharge() {return charge;}
    public void charge(double charge) {this.charge+=charge;}
    public boolean orderedFood(Food food) {
        for (Order order : orders)
            if (order.getFoods().containsKey(food))
                return true;
        return false;
    }
}
