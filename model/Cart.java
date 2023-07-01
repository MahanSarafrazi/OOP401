package model;

import java.util.ArrayList;

public class Cart {
    Cart() {
        foods = new ArrayList<>();
        foodsCount = new ArrayList<>();
    }
    private final ArrayList<Food> foods;
    private final ArrayList<Integer> foodsCount;
    public ArrayList<Food> getFoods() {return foods;}
    public ArrayList<Integer> getFoodsCount() {return foodsCount;}
    public void clear() {
        foods.clear();
        foodsCount.clear();
    }
    public void put(Food food,int count) {
        boolean isRepeated = false;
        for (int i=0;i<foods.size();i++) {
            if (foods.get(i).getName().equals(food.getName())) {
                foodsCount.set(i,count);
                isRepeated=true;
                break;
            }
        }
        if (!isRepeated) {
            foods.add(food);
            foodsCount.add(count);
        }
    }
    public void remove(Food food) {
        for (int i=0;i<foods.size();i++)
            if (foods.get(i).getName().equals(food.getName())) {
                foods.remove(i);
                foodsCount.remove(i);
                break;
            }
    }
}
