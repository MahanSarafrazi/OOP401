package model;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
    //Edit Location;
    private final String name;
    private ArrayList<Order> orders;
     private static int numberOfRestaurants = RestaurantList.restaurants.size();
     private static final ArrayList<Integer> allIDs = new ArrayList<>();
     public static ArrayList<Integer> getAllIDs() {
         return allIDs;
     }
     public Restaurant(String name, FoodType foodType) {
         this.name=name;
         ++numberOfRestaurants;
         RandomIDGenerator randomIDGenerator = new RandomIDGenerator(RandomIDGenerator.getSize());
         allIDs.add(randomIDGenerator.getLastNumber());
         this.ID = randomIDGenerator.getLastNumber();
         foods = new ArrayList<>();
         orders = new ArrayList<>();
         foodTypes = new ArrayList<>();
         foodTypes.add(foodType);
     }

     public String getName(){return name;}
     private final int ID;
     public int getID(){return ID;}
     public ArrayList<Order> getOrders() {
         return orders;
     }
     private final ArrayList<FoodType> foodTypes;
     public void setFoodType(FoodType foodType){foodTypes.add(foodType);}

    public void editFoodType(FoodType firstFoodType, FoodType secondFoodType) {
         for (int i = 0; i < foodTypes.size(); i++) {
             if (foodTypes.get(i).equals(firstFoodType)) {
                 foodTypes.set(i , secondFoodType);
                 foods.removeIf(food -> food.getType().equals(firstFoodType));
             }
         }
     }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<FoodType> getFoodType (){return foodTypes;}
     private final ArrayList<Food> foods;
     public void AddFood(String foodName, double foodPrice, FoodType foodType){
         foods.add(new Food(foodName, foodPrice, foodType));
     }
     public void deleteFood(int ID){
         for (Food food : foods) {
             if(food.getID() == ID) {
                 foods.remove(food);
                 return;
             }
         }
     }
    public void EditFoodPrice( int IDCode,double newPrice){
        for (Food food : foods) {
            if (food.getID() == IDCode) {
                food.setPrice(newPrice);
            }
        }
    }
    public void setActivation(int IDCode, boolean activation) {
        for (Food food : foods) {
            if (food.getID() == IDCode) {
                food.setActivation(activation);
            }
        }
    }

    public boolean isThereAnyOrderOfThisType(FoodType foodType) {
         int count = 0;
        for (Order order : orders) {
            if(order.getType().equals(foodType)) {
                ++count;
            }
        }
        return count != 0;
    }

    public Food getFoodByID(int ID) {
        for (Food food : foods) {
            if(food.getID() == ID) {
                return food;
            }
        }
        return null;
    }

     //ArrayList<FoodType> RestaurantFoodType = new ArrayList<>();



}
