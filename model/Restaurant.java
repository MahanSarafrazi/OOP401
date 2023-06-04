package model;

import view.Inputs;

import java.util.ArrayList;

public class Restaurant {
    //Location locate ;
    //Edit Location;
    private final String name;
    private final ArrayList<Order> orders;

    public Restaurant(String name, FoodType foodType) {
        this.name = name;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        foods = new ArrayList<>();
        orders = new ArrayList<>();
        foodTypes = new ArrayList<>();
        foodTypes.add(foodType);
        comments = new ArrayList<>();
        rates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    private final int ID;

    public int getID() {
        return ID;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    private final ArrayList<FoodType> foodTypes;

    public void setFoodType(FoodType foodType) {
        foodTypes.add(foodType);
    }

    public void editFoodType(FoodType firstFoodType, FoodType secondFoodType) {
        for (int i = 0; i < foodTypes.size(); i++) {
            if (foodTypes.get(i).equals(firstFoodType)) {
                foodTypes.set(i, secondFoodType);
                foods.removeIf(food -> food.getType().equals(firstFoodType));
            }
        }
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<FoodType> getFoodType() {
        return foodTypes;
    }

    private final ArrayList<Food> foods;

    public void AddFood(String foodName, double foodPrice, FoodType foodType) {
        foods.add(new Food(foodName, foodPrice, foodType));
    }

    public void deleteFood(int ID) {
        for (Food food : foods) {
            if (food.getID() == ID) {
                foods.remove(food);
                return;
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
            if (order.getType().equals(foodType)) {
                ++count;
            }
        }
        return count != 0;
    }

    public Food getFoodByID(int ID) {
        for (Food food : foods) {
            if (food.getID() == ID) {
                return food;
            }
        }
        return null;
    }

    private ArrayList<Comment> comments;
    public ArrayList<Comment> getComments() {return comments;}
    public void addComment(String comment, User user) {comments.add(new Comment(user,comment,false));}
    private String ownerName;
    public String getOwnerName() {return ownerName;}
    private ArrayList<Rate> rates ;
    public ArrayList<Rate>  getRates() {return rates;}
    public void addRating(User user,double rating) {rates.add(new Rate(user,rating));}
    private Food activeFood = null;
    public Food getActiveFood() {
        return activeFood;
    }

    public boolean setActiveFood(int ID) {
        for (Food food : foods) {
            if(food.getID() == ID) {
                activeFood = food;
                return true;
            }
        }
        return false;
    }
    public void setActiveFood(Food food) {
        this.activeFood = food;
    }
    public void deActiveFood() {activeFood = null;}
}
