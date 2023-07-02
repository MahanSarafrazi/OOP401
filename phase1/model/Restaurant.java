package phase1.model;

import phase1.view.OrderStatus;

import java.util.ArrayList;

public class Restaurant {
    private final String name;
    private final ArrayList<Order> orders;
    public void addOrder(Order order) {orders.add(order);}

    public Restaurant(String name, FoodType foodType, int location) {
        this.name = name;
        RandomIDGenerator randomIDGenerator = new RandomIDGenerator();
        this.ID = randomIDGenerator.getLastNumber();
        foods = new ArrayList<>();
        orders = new ArrayList<>();
        foodTypes = new ArrayList<>();
        foodTypes.add(foodType);
        comments = new ArrayList<>();
        rates = new ArrayList<>();
        this.location = location;
    }

    public String getName() {
        return name;
    }

    private final int ID;
    private int location;
    public int getLocation() {return location;}
    public void setLocation(int location) {
        this.location=location;
        for (Order order : orders) {
            if (order.orderStatus != OrderStatus.SENT)
                order.setRestaurantLocation(location);
        }
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Order> getOrders() {
        for (Order order : orders) {
            order.correctOrderStatus();
        }
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
        foods.add(new Food(foodName, foodPrice, foodType,name));
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
        for (Order order : orders) {
            if (order.getType().contains(foodType) && !order.orderStatus.equals(OrderStatus.SENT)) {
                return true;
            }
        }
        return false;
    }

    public Food getFoodByID(int ID) {
        for (Food food : foods) {
            if (food.getID() == ID) {
                return food;
            }
        }
        return null;
    }

    private final ArrayList<Comment> comments;
    public ArrayList<Comment> getComments() {return comments;}
    public void addComment(String comment, User user) {comments.add(new Comment(user,comment));}
    private final ArrayList<Rate> rates ;
    public ArrayList<Rate>  getRates() {return rates;}
    public void addRating(User user,double rating) {rates.add(new Rate(user,rating));}
    private Food openedFood = null;
    public Food getOpenedFood() {
        return openedFood;
    }
    public boolean setOpenedFood(int ID) {
        for (Food food : foods) {
            if(food.getID() == ID && food.getActivation()) {
                openedFood = food;
                return true;
            }
        }
        return false;
    }
    public void closeFood() {openedFood = null;}

}
