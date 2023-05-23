package model;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
     private String name;
     public String getName(){return name;}
     private long ID;
     public long getID(){return ID;}
     private ArrayList<FoodType> foodTypes ;
     public void setFoodType(FoodType foodType){foodTypes.add(foodType);}
     public void editFoodType(FoodType FirstfoodType,FoodType SecondaryFoodType){
         for (int i = 0; i < foodTypes.size(); i++) {
             if (foodTypes.get(i) == FirstfoodType){foodTypes.set(i , SecondaryFoodType);}
         }
     }
     private ArrayList<Food> foods ;



     //ArrayList<FoodType> RestaurantFoodType = new ArrayList<>();



}
