package controller;

import model.*;
import model.Map;
import view.OrderStatus;
import view.RestaurantOwnerMenu;
import view.Output;
import java.util.*;


public class Manager {
    //only one Manager
    private Manager() {
    }

    private static Manager managerInstance;

    public static Manager getManagerInstance() {
        if (managerInstance == null) {
            managerInstance = new Manager();
        }
        return managerInstance;
    }

    private User loggedInUser = null;
    private Map map;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public User getUser(String username) {
        for (Customer customer : UserList.getUserListInstance().getCustomers())
            if (customer.getUserName().equals(username))
                return customer;
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers())
            if (deliverer.getUserName().equals(username))
                return deliverer;
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners())
            if (restaurantOwner.getUserName().equals(username))
                return restaurantOwner;
        return null;
    }

    public Output addUser(String username, String password, UserType userType) {
        Output temp = null;
        if (userType == UserType.CUSTOMER)
            temp = addCustomer(username, password);
        if (userType == UserType.DELIVERER)
            temp =
                    addDeliverer(username, password);
        if (userType == UserType.RESTAURANT_OWNER)
            temp = addRestaurantOwner(username, password);
        return temp;
    }
    public Output logInUser (String username, String password, UserType userType) {
        Output temp = null;
        if (userType == UserType.CUSTOMER)
            temp = logInCustomer(username, password);
        if (userType == UserType.DELIVERER)
            temp = logInDeliverer(username, password);
        if (userType == UserType.RESTAURANT_OWNER)
            temp = logInRestaurantOwner(username, password);
        return temp;
    }
    public Output checkRestoreQuestion() {
        if (loggedInUser.getRestoreQuestion() == null) {
            return Output.ADD_RESTORE_QUESTION;
        }
        return Output.RESTORE_QUESTION_EXISTS;
    }

    public Output getRestoreQuestion(String username) {
        if (getUser(username) == null) {
            return Output.INVALID_USER_NAME;
        } else if (Objects.requireNonNull(getUser(username)).getRestoreQuestion() == null)
            return Output.NO_RESTORE_QUESTION;
        return Output.SHOW_RESTORE_QUESTION;
    }
    public void setRestore(String question, String answer) {
        loggedInUser.setRestoreQuestion(question);
        loggedInUser.setRestoreAnswer(answer);
    }
    public void logout() {
        if (loggedInUser instanceof RestaurantOwner)
            RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(true);
        if (loggedInUser.getActiveRestaurant() != null)
            loggedInUser.getActiveRestaurant().closeFood();
        loggedInUser.deActiveRestaurant();
        loggedInUser = null;
    }
    public void back() {
        if (loggedInUser instanceof RestaurantOwner && loggedInUser.getActiveRestaurant().getOpenedFood() == null)
            RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(false);
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            loggedInUser.getActiveRestaurant().closeFood();
        else
            loggedInUser.deActiveRestaurant();
    }

    public Output addRestaurant(String name, String foodType, int location) {
        FoodType foodType1 = null;
        for (FoodType value : FoodType.values()) {
            if (value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
                break;
            }
        }

        if (foodType1 == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }

        if (location < 1 || location > 1000) {
            return Output.LOCATION_NOT_IN_THE_MAP;
        }

        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        owner.AddRestaurant(name, foodType1, location);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output processEditFoodType(String firstType, String secondType) {
        FoodType changingType = null, replacingType = null;

        for (FoodType foodType : loggedInUser.getActiveRestaurant().getFoodType()) {
            if (foodType.commandingPattern.matcher(firstType).find()) {
                changingType = foodType;
                break;
            }
        }

        if (changingType == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_RESTAURANT;
        }

        for (FoodType value : FoodType.values()) {
            if (value.commandingPattern.matcher(secondType).find()) {
                replacingType = value;
                break;
            }
        }

        if (replacingType == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }

        if (changingType == replacingType) {
            return Output.EQUAL_FOOD_TYPES;
        }

        if (loggedInUser.getActiveRestaurant().isThereAnyOrderOfThisType(changingType) || cartChecker(changingType)) {
            return Output.THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE;
        }

        return Output.SURE_EDIT_FOOD_TYPE;
    }

    public Output editFoodType(String firstType, String secondType, String command) {
        if (command.matches("^\\s*(?i)no\\s*$")) {
            return Output.EDIT_FOOD_TYPE_CANCELED;
        }
        if (command.matches("^\\s*(?i)yes\\s*$")) {
            FoodType changingType = null, replacingType = null;
            for (FoodType foodType : loggedInUser.getActiveRestaurant().getFoodType()) {
                if (foodType.commandingPattern.matcher(firstType).find()) {
                    changingType = foodType;
                    break;
                }
            }
            for (FoodType value : FoodType.values()) {
                if (value.commandingPattern.matcher(secondType).find()) {
                    replacingType = value;
                    break;
                }
            }
            loggedInUser.getActiveRestaurant().editFoodType(changingType, replacingType);
            return Output.FOOD_TYPE_EDITED;
        }

        return Output.INVALID_COMMAND;
    }

    public Output addFoodType(String foodType) {
        FoodType foodType1 = null;
        for (FoodType value : FoodType.values()) {
            if (value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
            }
        }
        if (foodType1 == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }
        for (FoodType type : loggedInUser.getActiveRestaurant().getFoodType()) {
            if (type.equals(foodType1)) {
                return Output.FOOD_TYPE_ALREADY_EXIST;
            }
        }
        loggedInUser.getActiveRestaurant().setFoodType(foodType1);
        return Output.FOOD_TYPE_ADDED;
    }

    public Output addFood(String foodName, double foodPrice, String foodType) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        FoodType foodType1 = null;
        for (FoodType value : FoodType.values()) {
            if (value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
                break;
            }
        }
        if (foodType1 == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }
        if (!owner.getActiveRestaurant().getFoodType().contains(foodType1))
            return Output.NO_SUCH_FOOD_TYPE_IN_RESTAURANT;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if (food.getName().equals(foodName)) {
                return Output.FOOD_ALREADY_EXIST;
            }
        }

        owner.getActiveRestaurant().AddFood(foodName, foodPrice, foodType1);
        return Output.FOOD_ADDED;
    }

    public ArrayList<Food> getActiveRestaurantActiveFoods() {
        ArrayList<Food> activeFoods = new ArrayList<>();
        for (Food food : loggedInUser.getActiveRestaurant().getFoods())
            if(food.getActivation())
                activeFoods.add(food);
        return activeFoods;
    }

    public Output editFoodName(int ID, String newName) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if (food.getID() == ID) {
                food.setName(newName);
                return Output.FOOD_NAME_EDITED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }

    public Output editFoodPrice(int ID, double newPrice) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if (food.getID() == ID) {
                food.setPrice(newPrice);
                return Output.FOOD_PRICE_EDITED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }

    public Output deleteFood(int ID) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if (food.getID() == ID) {
                boolean isThereFood = false;
                for (Order order : owner.getActiveRestaurant().getOrders()) {
                    if ( (order.getFoods().contains(owner.getActiveRestaurant().getFoodByID(ID)) &&
                            !order.getOrderStatus().equals(OrderStatus.SENT)) || cartChecker(ID)) {
                        isThereFood = true;
                        break;
                    }
                }
                if (isThereFood) {
                    return Output.THERE_ARE_FOODS_IN_ORDER;
                }
                owner.getActiveRestaurant().deleteFood(ID);
                return Output.FOOD_DELETED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }

    public Output deActiveFood(int ID) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if (food.getID() == ID) {
                boolean isThereFood = false;
                for (Order order : owner.getActiveRestaurant().getOrders()) {
                    if ( (order.getFoods().contains(owner.getActiveRestaurant().getFoodByID(ID)) &&
                            !order.getOrderStatus().equals(OrderStatus.SENT)) || cartChecker(ID)) {
                        isThereFood = true;
                        break;
                    }
                }
                if (isThereFood) {
                    return Output.THERE_ARE_FOODS_IN_ORDER;
                }
                owner.getActiveRestaurant().setActivation(ID, false);
                return Output.FOOD_DEACTIVATED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }

    public Output activeFood(int ID) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if (food.getID() == ID) {
                owner.getActiveRestaurant().setActivation(ID, true);
                return Output.FOOD_ACTIVATED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }
    public Output discountFood(int ID, double percent, int hours) {
        for (Food food : loggedInUser.getActiveRestaurant().getFoods()) {
            if(food.getID() == ID) {
                if(food.hasDiscounted()) {
                    return Output.FOOD_ALREADY_DISCOUNTED;
                } if(percent < 0 || percent > 50) {
                    return Output.WRONG_PERCENT_AMOUNT;
                } if(hours < 0) {
                    return Output.INVALID_TIME;
                }
                food.setDiscount(percent, hours);
                return Output.FOOD_DISCOUNTED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }
    public void addComment(String comment) {
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            loggedInUser.getActiveRestaurant().getOpenedFood().addComment(loggedInUser, comment);
        else
            loggedInUser.getActiveRestaurant().addComment(comment, loggedInUser);
    }

    public Output editComment(int ID, String commentString) {
        ArrayList<Comment> comments;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() == null)
            comments = loggedInUser.getActiveRestaurant().getComments();
        else
            comments = loggedInUser.getActiveRestaurant().getOpenedFood().getComments();
        for (Comment comment : comments) {
            if (comment.getID() == ID) {
                comment.editComment(commentString);
                return Output.COMMENT_EDITED;
            }
        }
        return Output.NO_COMMENT_WITH_ID;
    }
    public boolean isThereRating() {
        ArrayList<Rate> rates;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            rates = loggedInUser.getActiveRestaurant().getRates();
        else
            rates  = loggedInUser.getActiveRestaurant().getOpenedFood().getRates();
        return rates.isEmpty();
    }
    public double averageRating() {
        ArrayList<Rate> rates;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            rates = loggedInUser.getActiveRestaurant().getRates();
        else
            rates  = loggedInUser.getActiveRestaurant().getOpenedFood().getRates();
        double average = 0;
        for (Rate rate : rates)
            average += rate.getRating();
        return average / rates.size();
    }
    public ArrayList<Rate> getRating() {
        ArrayList<Rate> rates;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            rates = loggedInUser.getActiveRestaurant().getRates();
        else
            rates  = loggedInUser.getActiveRestaurant().getOpenedFood().getRates();
        return rates;
    }
    public Output addRating(double rating) {
        if (rating>5 || rating<0)
            return Output.RATING_OUT_OUT_OF_RANGE;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null) {
            for (Rate rate : loggedInUser.getActiveRestaurant().getOpenedFood().getRates())
                if (rate.getUser().getUserName().equals(loggedInUser.getUserName()))
                    return Output.RATING_EXISTS;
            loggedInUser.getActiveRestaurant().getOpenedFood().addRating(loggedInUser, rating);
        }
        else {
                for (Rate rate : loggedInUser.getActiveRestaurant().getRates())
                    if (rate.getUser().getUserName().equals(loggedInUser.getUserName()))
                        return Output.RATING_EXISTS;
                loggedInUser.getActiveRestaurant().addRating(loggedInUser, rating);
            }
        return Output.RATED;
    }
    public Output editRating(double rating) {
        if (rating>5 || rating<0)
            return Output.RATING_OUT_OUT_OF_RANGE;
        ArrayList<Rate> rates;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            rates = loggedInUser.getActiveRestaurant().getRates();
        else
            rates = loggedInUser.getActiveRestaurant().getOpenedFood().getRates();
        for (Rate rate : rates)
            if (rate.getUser().getUserName().equals(loggedInUser.getUserName())) {
                rate.editRating(rating);
                return Output.NO_RATING;

            }
        return Output.RATED;
    }
    public Output addResponse(int ID, String responseText) {
        ArrayList<Comment> comments;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            comments = loggedInUser.getActiveRestaurant().getComments();
        else
            comments = loggedInUser.getActiveRestaurant().getOpenedFood().getComments();
        for (Comment comment : comments) {
            if (comment.getID() == ID) {
                if (comment.hasResponse) {
                    return Output.RESPONSE_EXISTS;
                }
                comment.addResponse(loggedInUser, responseText);
                return Output.RESPONSE_ADDED;
            }
        }
        return Output.NO_COMMENT_WITH_ID;
    }

    public Output editResponse(int ID, String newComment) {
        ArrayList<Comment> comments;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            comments = loggedInUser.getActiveRestaurant().getComments();
        else
            comments = loggedInUser.getActiveRestaurant().getOpenedFood().getComments();
        for (Comment comment : comments) {
            if (comment.getID() == ID) {
                if (!comment.hasResponse) {
                    return Output.NO_RESPONSE;
                }
                comment.getResponse().editComment(newComment);
                return Output.RESPONSE_EDITED;
            }
        }
        return Output.NO_COMMENT_WITH_ID;
    }
    public Output editLocation(int location) {
        if (location>1000 || location<1)
            return Output.LOCATION_NOT_IN_THE_MAP;
        if (loggedInUser.getActiveRestaurant().getLocation() != location ) {
            loggedInUser.getActiveRestaurant().setLocation(location);
            return Output.LOCATION_SET;
        }
        return Output.EQUAL_LOCATION;
    }
    public ArrayList<Restaurant> searchForNearRestaurants(int location) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants) {
            if(map.findShortestPath(location, restaurant.getLocation(),false) < 30) {
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }
    public ArrayList<Restaurant> searchForNearRestaurants(int location, String foodType) {
        FoodType foodType1 = null;
        for (FoodType value : FoodType.values()) {
            if(value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
            }
        }
        if(foodType1 == null) {
            return null;
        }
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants) {
            boolean sameFoodType = false;
            for (FoodType type : restaurant.getFoodType()) {
                if(type.equals(foodType1)) {
                    sameFoodType = true;
                    break;
                }
            }
            if(!sameFoodType) {
                continue;
            }
            if(map.findShortestPath(location, restaurant.getLocation(),false) < 30) {
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }
    public LinkedHashMap<Integer, Integer> favoriteRestaurants() {
        Customer customer = (Customer) loggedInUser;
        ArrayList<Order> orders = customer.getOrders();
        LinkedHashMap<Integer , Integer> restaurants = new LinkedHashMap<>();
        for (Order order : orders) {
            if (restaurants.containsKey(order.getRestaurantID())) {
                int newValue = restaurants.get(order.getRestaurantID()) + 1;
                restaurants.put(order.getRestaurantID(), newValue);
            }
            else
                restaurants.put(order.getRestaurantID(), 1);
        }
        List<java.util.Map.Entry<Integer , Integer>> list = new ArrayList<>(restaurants.entrySet());
        list.sort(java.util.Map.Entry.comparingByValue());
        Collections.reverse(list);
        restaurants.clear();
        for (java.util.Map.Entry<Integer ,Integer> map : list)
            restaurants.put(map.getKey(),map.getValue());
        return restaurants;
    }
    public Order selectOrder(int ID) {
        Customer customer = (Customer) loggedInUser;
        for (Order order : customer.getOrders()) {
            if(order.getID() == ID) {
                return order;
            }
        }
        return null;
    }
    public Output confirmOrder(int customerLocation) {
        Customer customer = (Customer) getLoggedInUser();
        if (customer.getCart().getFoods().isEmpty())
            return Output.EMPTY_CART;
        else {
            double totalPrice = 0;
            Cart cart = customer.getCart();
            for (int i=0;i<cart.getFoodsCount().size();i++)
                totalPrice+=cart.getFoodsCount().get(i)*cart.getFoods().get(i).getDiscountedPrice();
            if (totalPrice > customer.getCharge())
                return Output.NOT_ENOUGH_CHARGE;
        }
        Order order = new Order(customer.getCart(),customer.getOrderedRestaurant(),customerLocation);
        customer.getOrderedRestaurant().addOrder(order);
        customer.addOrder(order);
        return Output.ORDER_CONFIRMED;
    }
    public ArrayList<String> estimateOrderTime() {
        Customer customer = (Customer) getLoggedInUser();
        ArrayList<String> estimateTimes = new ArrayList<>();
        for (Order order : customer.getOrders()) {
            estimateTimes.add(order.estimateTime());
        }
        return estimateTimes;
    }
    public ArrayList<Order> getActiveOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order order : loggedInUser.getActiveRestaurant().getOrders())
            if (!order.getOrderStatus().equals(OrderStatus.SENT))
                orders.add(order);
        return orders;
    }

    private Output addCustomer(String username, String password) {
        if (getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = Customer.newCustomer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public boolean editOrderStatus(int ID) {
        for (Order order : loggedInUser.getActiveRestaurant().getOrders()) {
            if(order.getID() == ID) {
                order.setOrderStatus(OrderStatus.SENT);
                return true;
            }
        }
        return false;
    }

    private Output addDeliverer(String username, String password) {
        if (getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = Deliverer.newDeliverer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    private Output addRestaurantOwner(String username, String password) {
        if (getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = RestaurantOwner.newRestaurantOwner(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    private Output logInCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if (customer.getUserName().equals(username)) {
                if (customer.getPassword().equals(password)) {
                    loggedInUser = customer;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }

    private Output logInDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if (deliverer.getUserName().equals(username)) {
                if (deliverer.getPassword().equals(password)) {
                    loggedInUser = deliverer;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }

    private Output logInRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if (restaurantOwner.getUserName().equals(username)) {
                if (restaurantOwner.getPassword().equals(password)) {
                    loggedInUser = restaurantOwner;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    private boolean cartChecker(FoodType foodType) {
        ArrayList<Customer> customers = UserList.getUserListInstance().getCustomers();
        for (Customer customer : customers) {
            for (Food food : customer.getCart().getFoods()) {
                if (food.getType().equals(foodType))
                    return true;
            }
        }
        return false;
    }
    private boolean cartChecker(int ID) {
        ArrayList<Customer> customers = UserList.getUserListInstance().getCustomers();
        for (Customer customer : customers) {
            for (Food food : customer.getCart().getFoods()) {
                if (food.getID() == ID)
                    return true;
            }
        }
        return false;
    }
}
