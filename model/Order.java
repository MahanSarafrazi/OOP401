package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Order {
    private final int ID;
    public int getID(){ return ID;}
     private double timeFinishing=0;
    private ArrayList<Food> foods=new ArrayList<>();
    public ArrayList<Food> getFoods(){
        return foods;

    }
    public Order(Food food){
        foods.add(food);
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID=randomIDGenerator.getLastNumber();



        
    }



    public ArrayList<FoodType> getType(){
        ArrayList<FoodType> foodTypes=new ArrayList<>();
        for (int i = 0; i < foods.size(); i++) {
            foodTypes.add(foods.get(i).getType());
        }
        return foodTypes;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
    public  double BillWithoutDiscount(){
        double price=0;
        for (int i = 0; i < foods.size(); i++) {
            price+=foods.get(i).getPrice();
        }
        return price;
    }
    public  double BillWithDiscount(){
        double price=0;
        for (int i = 0; i < foods.size(); i++) {
            price+=foods.get(i).getDiscountedPrice();
        }
        return price;
    }


}
