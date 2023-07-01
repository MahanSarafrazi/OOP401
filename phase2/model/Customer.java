package phase2.model;

import java.util.ArrayList;

public class Customer extends User {
    public static Customer newCustomer(String userName, String pass) {
        Customer customer = new Customer(userName, pass);
        UserList.getUserListInstance().getCustomers().add(customer);
        return customer;
    }

    private Customer(String userName, String pass) {
        super(userName, pass);
        orders=new ArrayList<>();
        cart=new Cart();
        charge=0;
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
        orderedRestaurant=null;
    }
    private final Cart cart;
    public Cart getCart() {return cart;}
    private double charge;
    public double getCharge() {return charge;}
    public void charge(double charge) {this.charge+=charge;}
    public boolean orderedFood(Food food) {
        for (Order order : orders)
            for (Food food1 : order.getFoods())
                if (food1.getID() == food.getID())
                    return true;
        return false;
    }
    public boolean orderedFood (Restaurant restaurant) {
        for (Order order : orders)
            if (order.getRestaurantID() == restaurant.getID())
                return true;
        return false;
    }
    private Restaurant orderedRestaurant = null;
    public void setOrderedRestaurant(Restaurant restaurant) {this.orderedRestaurant=restaurant;}
    public Restaurant getOrderedRestaurant() {return orderedRestaurant;}
}

