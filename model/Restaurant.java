package model;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
    //Edit Location;
     private String name;
     public Restaurant(String name) {this.name=name;}

     public String getName(){return name;}
     private long ID;
     public long getID(){return ID;}
     private ArrayList<FoodType> foodTypes ;
     public void setFoodType(FoodType foodType){foodTypes.add(foodType);}
     public boolean editFoodType(FoodType FirstfoodType,FoodType SecondaryFoodType){
         //*************************************** چک کردن سفارش موجود؟؟
         for (int i = 0; i < foodTypes.size(); i++) {
             if (foodTypes.get(i) == FirstfoodType){
                 foodTypes.set(i , SecondaryFoodType);
                 for (int j = foods.size()-1; j > 0; j--) {
                     foods.remove(j);
                 }
                 return true;
             }
         }
         return false;
     }
         //*************************************************************
     public ArrayList<FoodType> ShowFoodType (){return foodTypes;}
     private ArrayList<Food> foods ;






     //ArrayList<FoodType> RestaurantFoodType = new ArrayList<>();



}
