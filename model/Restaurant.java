package model;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
    //Edit Location;
     private final String name;
     public ArrayList<Order> orders;
     private static int numberOfRestaurants = RestaurantList.restaurants.size();
     public Restaurant(String name, FoodType foodType) {
         this.name=name;
         ++numberOfRestaurants;
         this.ID = numberOfRestaurants;
         foods = new ArrayList<>();
         orders = new ArrayList<>();
         foodTypes = new ArrayList<>();
         foodTypes.add(foodType);
     }

     public String getName(){return name;}
     private final long ID;
     public long getID(){return ID;}
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
     public void DeleteFood(long IDCode){
         for (int i = 0; i < foods.size(); i++) {
             foods.removeIf(food -> food.getID() == IDCode);
         }
     }
    public void EditFoodPrice( long IDCode,double newPrice){
        for (Food food : foods) {
            if (food.getID() == IDCode) {
                food.setPrice(newPrice);
            }
        }
    }
    public void setActivationOrder(long IDCode, boolean activation){
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


     //ArrayList<FoodType> RestaurantFoodType = new ArrayList<>();



}
