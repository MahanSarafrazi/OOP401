package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Order {


    private LinkedHashMap< Food, Integer> foods;

    public LinkedHashMap<Food, Integer> getFoods() {
        return foods;
    }

    public void setFoods(LinkedHashMap<Food, Integer> foods) {
        this.foods = foods;
    }



}
