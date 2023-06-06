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
    private final Restaurant restaurant;
    private final Date registerDate;
    int numOfFoods = 0;
    int timeOfDelivery;
    int timeOfGettingReady;
    public Restaurant getRestaurant() {return restaurant;}
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
        this.restaurant = restaurant;
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
