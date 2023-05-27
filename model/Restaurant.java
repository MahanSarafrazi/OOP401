package model;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
    //Edit Location;
     private String name;
     public ArrayList<Food> order;
     public Restaurant(String name) {this.name=name;}

     public String getName(){return name;}
     private long ID;
     public long getID(){return ID;}
     private ArrayList<FoodType> foodTypes ;
     public void setFoodType(FoodType foodType){foodTypes.add(foodType);}
     public boolean editFoodType(FoodType FirstfoodType,FoodType SecondaryFoodType){
         if (order.size()==0){
         for (int i = 0; i < foodTypes.size(); i++) {
             if (foodTypes.get(i) == FirstfoodType){
                 foodTypes.set(i , SecondaryFoodType);
                 for (int j = foods.size()-1; j > 0; j--) {
                     foods.remove(j);
                 }
                 return true;
             }
         }}
         return false;
     }

     public ArrayList<FoodType> getFoodType (){return foodTypes;}
     private ArrayList<Food> foods ;
     public void AddFood(String NameFood, double PriceName){
         Food food=new Food(NameFood,PriceName);
         foods.add(food);
     }
     public boolean DeleteFood(long IDCode){
         //اگر غذای مورد نظر در لیست سفارشات رستوران باشد false خروجی می دهد
         boolean check=false;
         for (int i = 0; i < order.size(); i++) {
             if(order.get(i).getID()==IDCode) {
                 check=true;
             }
         }

         if(check){
         for (int i = 0; i < foods.size(); i++) {
             if(foods.get(i).getID()==IDCode){
                 foods.remove(i);
             }
         }
         return true;
         }
         return false;
     }
     public void EditFoodName( long IDCode,String newName){
         for (int i = 0; i < foods.size(); i++) {
             if (foods.get(i).getID()==IDCode){
                 foods.get(i).setName(newName);
             }
         }
     }
    public void EditFoodPrice( long IDCode,double newPrice){
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getID()==IDCode){
                foods.get(i).setPrice(newPrice);
            }
        }
    }

    public boolean setActivationOrder(long IDCode, boolean activation){
         //اگر غذای مورد نظر در لیست سفارشات باشد این تابع false خروجی می دهد
        boolean check=false;
        for (int i = 0; i < order.size(); i++) {
            if(order.get(i).getID()==IDCode) {
                check=true;
            }
        }
        if(check){
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getID()==IDCode){
                foods.get(i).setActivation(activation);
            }
            return true;
        }
        }
        return false;
    }







     //ArrayList<FoodType> RestaurantFoodType = new ArrayList<>();




}
