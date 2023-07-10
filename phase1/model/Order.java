package phase1.model;

import phase1.controller.Manager;
import phase1.view.OrderStatus;

import java.util.*;

public class Order {
    private final int ID;
    private final int restaurantID;
    public boolean hasDeliverer;

    public int getRestaurantID() {
        return restaurantID;
    }

    OrderStatus orderStatus;
    private final int customerLocation;
    public int getCustomerLocation() {return customerLocation;}
    private final String restaurantName;
    public String getRestaurantName() {return restaurantName;}
    private int restaurantLocation;
    public int getRestaurantLocation() {return restaurantLocation;}
    public void setRestaurantLocation(int newLocation) {
        restaurantLocation = newLocation;
        Map map = Manager.getManagerInstance().getMap();
        int shortestPath = map.findShortestPath(customerLocation, restaurantLocation,false);
        this.timeOfDelivery = shortestPath * 20;
    }
    private final Date registerDate;
    private Date deliveryDate;
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
        this.restaurantID=restaurant.getID();
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
        Map map = Manager.getManagerInstance().getMap();
        int shortestPath = map.findShortestPath(customerLocation, restaurant.getLocation(),false);
        this.timeOfDelivery = shortestPath * 20;
        int numOfFoods = 0;
        for (int i : foodsCount)
            numOfFoods+=i;
        this.timeOfGettingReady = numOfFoods * 300;
        hasDeliverer=false;
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
    public double totalDeliveryPrice() {
        Map map = Manager.getManagerInstance().getMap();
        int shortestPath = map.findShortestPath(customerLocation, restaurantLocation,false)/30;
        return (0.1 + 0.1 * (double) shortestPath)*totalPrice();
    }
    public String estimateTime() {
        correctOrderStatus();
        if (orderStatus.equals(OrderStatus.SENT))
            return "The order "+ID+" is delivered";
        else if (orderStatus.equals(OrderStatus.ON_THE_WAY) && !hasDeliverer)
            return "There isn't any deliverer for the order"+ ID;
        else if (orderStatus.equals(OrderStatus.ON_THE_WAY)) {
            long time = timeOfDelivery - (new Date().getTime() - deliveryDate.getTime()) / 1000;
            return "The order " + ID + " will be delivered in " + time / 60 + "minutes";
        }
        else {
            long time = timeOfGettingReady - (new Date().getTime() - registerDate.getTime()) / 1000;
            return "The order " + ID + " will be ready to be delivered in " + time / 60 + "minutes" ;
        }
    }
    public void correctOrderStatus() {
        if(!orderStatus.equals(OrderStatus.SENT)) {
            if(timeOfGettingReady - (new Date().getTime() - registerDate.getTime()) / 1000 > 0) {
                orderStatus = OrderStatus.NOT_READY;
            } else if(!hasDeliverer || timeOfDelivery - (new Date().getTime() - deliveryDate.getTime()) / 1000 > 0) {
                orderStatus = OrderStatus.ON_THE_WAY;
            } else {
                orderStatus = OrderStatus.SENT;
            }
        }
    }
    public void setDeliverer(int location) {
        Map map = Manager.getManagerInstance().getMap();
        int shortestPathTime = map.findShortestPath(customerLocation, location,false)*20*1000;
        deliveryDate = new Date();
        deliveryDate.setTime(Calendar.getInstance().getTimeInMillis() + shortestPathTime);
        hasDeliverer=true;
    }
    public boolean reachedRestaurant() {

        return new Date().getTime() - deliveryDate.getTime() < 0;
    }
    public boolean contains(Food food) {
        for (Food food1 : foods)
            if (food1.getID() == food.getID())
                return true;
        return false;
    }
}
