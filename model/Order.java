package model;

import controller.Manager;
import view.OrderStatus;

import java.util.*;
import java.util.Map;

public class Order {
    private final int ID;
    OrderStatus orderStatus;
    private final int customerLocation;
    public int getCustomerLocation() {return customerLocation;}
    private final String restaurantName;
    public String getRestaurantName() {return restaurantName;}
    private int restaurantLocation;
    public int getRestaurantLocation() {return restaurantLocation;}
    public void setRestaurantLocation(int newLocation) {
        restaurantLocation = newLocation;
        model.Map map = Manager.getManagerInstance().getMap();
        int shortestPath = map.findShortestPath(customerLocation, restaurantLocation);
        this.timeOfDelivery = shortestPath * 20;
        this.timeOfGettingReady = numOfFoods * 300;
    }
    private final Date registerDate;
    private int numOfFoods = 0;
    private int timeOfDelivery;
    private int timeOfGettingReady;
    public int getID() {
        return ID;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    private final LinkedHashMap<Food , Integer> foods;
    public LinkedHashMap<Food, Integer> getFoods() {
        return foods;
    }
    public Order(LinkedHashMap<Food , Integer> cart, Restaurant restaurant, int customerLocation) {
        this.restaurantName = restaurant.getName();
        this.restaurantLocation = restaurant.getLocation();
        this.foods = new LinkedHashMap<>();
        for (Map.Entry<Food,Integer> entry : cart.entrySet()) {
            this.foods.put(new Food(entry.getKey().getName(), entry.getKey().getDiscountedPrice(),
                    entry.getKey().getType(), entry.getKey().getID()), entry.getValue());
            numOfFoods += entry.getValue();
        }
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        orderStatus = OrderStatus.NOT_READY;
        this.customerLocation = customerLocation;
        registerDate = new Date();
        registerDate.setTime(Calendar.getInstance().getTimeInMillis());
        model.Map map = Manager.getManagerInstance().getMap();
        int shortestPath = map.findShortestPath(customerLocation, restaurant.getLocation());
        this.timeOfDelivery = shortestPath * 20;
        this.timeOfGettingReady = numOfFoods * 300;
    }

    public LinkedHashSet<FoodType> getType() {
        LinkedHashSet<FoodType> foodTypes = new LinkedHashSet<>();
        for (Map.Entry<Food,Integer> entry : foods.entrySet()) {
            foodTypes.add(entry.getKey().getType());
        }
        return foodTypes;
    }
    public double totalPrice() {
        double price = 0;
        for (Map.Entry<Food,Integer> entry : foods.entrySet()) {
            double number = entry.getValue();
            price += entry.getKey().getPrice() * number;
        }
        return price;
    }
    public String estimateTime() {
        correctOrderStatus();
        if(!orderStatus.equals(OrderStatus.SENT)) {
            long time = timeOfGettingReady + timeOfDelivery - (new Date().getTime() - registerDate.getTime()) / 1000;
            return "The time left for order " + ID + " is " + time / 60 + " minutes";
        }
        return "This order is delivered";
    }
    public void correctOrderStatus() {
        if(!orderStatus.equals(OrderStatus.SENT)) {
            if(timeOfGettingReady - (new Date().getTime() - registerDate.getTime()) / 1000 > 0) {
                orderStatus = OrderStatus.NOT_READY;
            } else if(timeOfGettingReady + timeOfDelivery - (new Date().getTime() - registerDate.getTime()) / 1000 > 0) {
                orderStatus = OrderStatus.ON_THE_WAY;
            } else {
                orderStatus = OrderStatus.SENT;
            }
        }
    }
}
