package model;

import view.OrderStatus;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Order {
    private final int ID;
    OrderStatus orderStatus;
    private final int customerLocation;
    public int getCustomerLocation() {return customerLocation;}
    private final Restaurant restaurant;
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
        for (Map.Entry<Food,Integer> entry : cart.entrySet())
            this.foods.put(new Food(entry.getKey().getName(), entry.getKey().getDiscountedPrice(),
                    entry.getKey().getType(), entry.getKey().getID()), entry.getValue());
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        orderStatus = OrderStatus.NOT_READY;
        this.customerLocation = customerLocation;
    }

    public LinkedHashSet<FoodType> getType(){
        LinkedHashSet<FoodType> foodTypes = new LinkedHashSet<>();
        for (Map.Entry<Food,Integer> entry : foods.entrySet()) {
            foodTypes.add(entry.getKey().getType());
        }
        return foodTypes;
    }
    public double totalPrice(){
        double price = 0;
        for (Map.Entry<Food,Integer> entry : foods.entrySet()) {
            double number = entry.getValue();
            price += entry.getKey().getPrice() * number;
        }
        return price;
    }

}
