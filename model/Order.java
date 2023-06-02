package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Order {
    private FoodType type;
    private LinkedHashMap<Food, Integer> foods;
    public FoodType getType() {
        return type;
    }

    public LinkedHashMap<Food, Integer> getFoods() {
        return foods;
    }
}
