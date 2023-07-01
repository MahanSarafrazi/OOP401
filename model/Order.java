package model;

import controller.Manager;
import view.OrderStatus;

import java.util.*;

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
        int shortestPath = map.findShortestPath(customerLocation, restaurantLocation,false);
        this.timeOfDelivery = shortestPath * 20;
    }
    private final Date registerDate;
    private int timeOfDelivery;
    private final int timeOfGettingReady;
    public int getID() {
        return ID;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    private final ArrayList<Food> foods;
    private final ArrayList<Integer> foodsCount;
    public ArrayList<Food> getFoods() {
        return foods;
    }
    public ArrayList<Integer> getFoodsCount() {
        return foodsCount;
    }
    public Order(Cart cart, Restaurant restaurant, int customerLocation) {
        this.restaurantName = restaurant.getName();
        this.restaurantLocation = restaurant.getLocation();
        this.foods = new ArrayList<>();
        this.foodsCount = new ArrayList<>();
        for (Food food : cart.getFoods()) {
            this.foods.add(new Food(food.getName(), food.getDiscountedPrice(), food.getType(),food.getID()));
        }
        this.foodsCount.addAll(cart.getFoodsCount());
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        orderStatus = OrderStatus.NOT_READY;
        this.customerLocation = customerLocation;
        registerDate = new Date();
        registerDate.setTime(Calendar.getInstance().getTimeInMillis());
        model.Map map = Manager.getManagerInstance().getMap();
        int shortestPath = map.findShortestPath(customerLocation, restaurant.getLocation(),false);
        this.timeOfDelivery = shortestPath * 20;
        int numOfFoods = 0;
        for (int i : foodsCount)
            numOfFoods+=i;
        this.timeOfGettingReady = numOfFoods * 300;
    }

    public LinkedHashSet<FoodType> getType() {
        LinkedHashSet<FoodType> foodTypes = new LinkedHashSet<>();
        for (Food food : foods) {
            foodTypes.add(food.getType());
        }
        return foodTypes;
    }
    public double totalPrice() {
        double price = 0;
        for (int i=0; i<foods.size();i++) {
            double number = foodsCount.get(i);
            price += foods.get(i).getPrice() * number;
        }
        return price;
    }
    public String estimateTime() {
        correctOrderStatus();
        if(!orderStatus.equals(OrderStatus.SENT)) {
            long time = timeOfGettingReady + timeOfDelivery - (new Date().getTime() - registerDate.getTime()) / 1000;
            return "The time left for order " + ID + " is " + time / 60 + " minutes";
        }
        return "The order "+ID+" is delivered";
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
