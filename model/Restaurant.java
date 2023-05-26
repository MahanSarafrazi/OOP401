package model;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
    //Edit Location;
     private String name;
     public ArrayList<Food> order;
     private static int numberOfRestaurants = 0;
     public Restaurant(String name) {
         this.name=name;
         ++numberOfRestaurants;
         this.ID = numberOfRestaurants;
     }

     public String getName(){return name;}
     private final long ID;
     public long getID(){return ID;}
     private ArrayList<FoodType> foodTypes ;
     public void setFoodType(FoodType foodType){foodTypes.add(foodType);}
     public boolean editFoodType(FoodType FirstfoodType,FoodType SecondaryFoodType) {
         if (order.size()==0) {
             for (int i = 0; i < foodTypes.size(); i++) {
                 if (foodTypes.get(i) == FirstfoodType) {
                     foodTypes.set(i , SecondaryFoodType);
                     for (int j = foods.size()-1; j > 0; j--) {
                         foods.remove(j);
                     }
                     return true;
                 }
             }
         }
         return false;
     }

     public ArrayList<FoodType> getFoodType (){return foodTypes;}
     private ArrayList<Food> foods ;
     public void AddFood(String NameFood, double PriceName){
         Food food=new Food(NameFood,PriceName);
         foods.add(food);
     }
     public void DeleteFood(long IDCode){
         for (int i = 0; i < foods.size(); i++) {
             if(foods.get(i).getID() == IDCode){
                 foods.remove(i);
             }
         }
     }
     public void EditFoodName( long IDCode,String newName){
         for (Food food : foods) {
             if (food.getID() == IDCode) {
                 food.setName(newName);
             }
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






     //ArrayList<FoodType> RestaurantFoodType = new ArrayList<>();



}
