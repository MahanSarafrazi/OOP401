package phase2.model;

import java.util.ArrayList;
import java.util.Random;

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
        spentMoney=0;
        discountTokens=new ArrayList<>();
    }
    private double spentMoney;
    private final ArrayList<String> discountTokens;
    public ArrayList<String> getDiscountTokens() {return discountTokens;}
    private final ArrayList<Order> orders ;
    public ArrayList<Order> getOrders() {
        for (Order order : orders) {
            order.correctOrderStatus();
        }
        return orders;
    }
    public boolean addOrder(Order order,double totalPrice,String token) {
        orders.add(order);
        clearCart();
        orderedRestaurant=null;
        if (hasThisToken(token)) {
            totalPrice *= 0.8;
            discountTokens.remove(token);
        }
        charge-=totalPrice;
        int multiply= (int) (spentMoney/1000000);
        spentMoney+=totalPrice;
        if (spentMoney<0)
            spentMoney=0;
        if (multiply == (int) spentMoney/1000000)
            return false;
        addToken();
        return true;
    }
    private final Cart cart;
    public Cart getCart() {return cart;}
    private double charge;
    public double getCharge() {return charge;}
    public double getCharge(String token) {
        if (hasThisToken(token))
            return charge*1.25;
        return charge;
    }
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
    public void clearCart() {cart.clear();}
    public void addToken() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        discountTokens.add(generatedString);
    }
    public boolean hasThisToken(String token) {
        return discountTokens.contains(token);
    }
}

