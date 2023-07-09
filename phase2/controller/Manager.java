package phase2.controller;

import phase2.model.Map;
import phase2.view.OrderStatus;
import phase2.view.Output;
//import phase2.view.RestaurantOwnerMenu;
import phase2.model.*;
//import phase2.view.RestaurantOwnerMenu;
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
        //if (loggedInUser instanceof RestaurantOwner)
            //RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(true);
        if (loggedInUser instanceof Customer customer) {
            customer.clearCart();
            customer.setOrderedRestaurant(null);
        }
        if (loggedInUser !=null && loggedInUser.getActiveRestaurant() != null && loggedInUser.getActiveRestaurant().getOpenedFood() != null)
            loggedInUser.getActiveRestaurant().closeFood();
        if (loggedInUser !=null && loggedInUser.getActiveRestaurant() != null)
            loggedInUser.deActiveRestaurant();
        loggedInUser = null;
    }
    public void back() {
        //if (loggedInUser instanceof RestaurantOwner && loggedInUser.getActiveRestaurant().getOpenedFood() == null)
            //RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(false);
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

        if (loggedInUser.getActiveRestaurant().isThereAnyOrderOfThisType(changingType)) {
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
        /*if (!owner.getActiveRestaurant().getFoodType().contains(foodType1))
            return Output.NO_SUCH_FOOD_TYPE_IN_RESTAURANT;*/
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
    public ArrayList<Food> getActiveFoods() {
        ArrayList<Food> activeFoods = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants)
            for (Food food : restaurant.getFoods())
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
                            order.getOrderStatus().equals(OrderStatus.NOT_READY))) {
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
                            order.getOrderStatus().equals(OrderStatus.NOT_READY))) {
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
                if(food.isDiscounted()) {
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
    public boolean addComment(String comment) {
        Customer customer = (Customer) loggedInUser;
        for (Order order : customer.getOrders()) {
            if (loggedInUser.getActiveRestaurant().getOpenedFood() != null && order.contains(customer.getOrderedRestaurant().getOpenedFood())) {
                customer.getActiveRestaurant().getOpenedFood().addComment(loggedInUser, comment);
                return true;
            } else if (loggedInUser.getActiveRestaurant().getOpenedFood() == null && order.getRestaurantID() == customer.getActiveRestaurant().getID()){
                customer.getActiveRestaurant().addComment(comment,loggedInUser);
                return true;
            }
        }
        return false;
    }
    public String averageRating() {
        ArrayList<Rate> rates;
        if (loggedInUser.getActiveRestaurant().getOpenedFood() == null)
            rates = loggedInUser.getActiveRestaurant().getRates();
        else
            rates  = loggedInUser.getActiveRestaurant().getOpenedFood().getRates();
        double average = 0;
        for (Rate rate : rates)
            average += rate.getRating();
        if (rates.isEmpty())
            return "No rating";
        return String.valueOf(average / rates.size());
    }
    public boolean addRating(double rating) {
        Customer customer = (Customer) loggedInUser;
        for (Order order : customer.getOrders()) {
            if (loggedInUser.getActiveRestaurant().getOpenedFood() != null && order.contains(customer.getOrderedRestaurant().getOpenedFood())) {
                for (Rate rate : loggedInUser.getActiveRestaurant().getOpenedFood().getRates())
                    if (rate.getUser().getUserName().equals(loggedInUser.getUserName())) {
                        rate.editRating(rating);
                        return true;
                    }
                loggedInUser.getActiveRestaurant().getOpenedFood().addRating(loggedInUser, rating);
            } else if (loggedInUser.getActiveRestaurant().getOpenedFood() == null && order.getRestaurantID() == customer.getActiveRestaurant().getID()){
                for (Rate rate : loggedInUser.getActiveRestaurant().getRates())
                    if (rate.getUser().getUserName().equals(loggedInUser.getUserName())) {
                        rate.editRating(rating);
                        return true;
                    }
                loggedInUser.getActiveRestaurant().addRating(loggedInUser, rating);
            }
        }
        return false;
    }
    public ArrayList<Restaurant> typeSearch(int location ,FoodType foodType) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants)
            if (restaurant.getFoodType().contains(foodType) && map.findShortestPath(location, restaurant.getLocation(),false) < 30)
                restaurants.add(restaurant);
        return restaurants;
    }
    public ArrayList<Restaurant> normalSearch(String name) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        RestaurantOwner restaurantOwner = (RestaurantOwner) loggedInUser;
        for (Restaurant restaurant : restaurantOwner.getRestaurants())
            if (restaurant.getName().contains(name))
                restaurants.add(restaurant);
        return restaurants;
    }
    public ArrayList<Restaurant> normalSearch(String name,FoodType foodType) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants)
                if (restaurant.getName().contains(name) && (foodType == null ||restaurant.getFoodType().contains(foodType)))
                    restaurants.add(restaurant);
        return restaurants;
    }
    public ArrayList<Restaurant> searchForNearRestaurants(int location, String foodType,String name) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants) {
            if(restaurant.getName().contains(name) && (foodType == null || restaurant.getFoodType().contains(FoodType.valueOf(foodType))) &&
                    map.findShortestPath(location, restaurant.getLocation(),false) < 30) {
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }
    public ArrayList<Restaurant> favoriteRestaurants(String foodType,String name) {
        Customer customer = (Customer) loggedInUser;
        ArrayList<Order> orders = customer.getOrders();
        LinkedHashMap<Integer , Integer> restaurants = new LinkedHashMap<>();
        for (Order order : orders) {
            if (Objects.requireNonNull(RestaurantList.getRestaurant(order.getRestaurantID())).getName().contains(name) &&
                    restaurants.containsKey(order.getRestaurantID()) && (foodType == null || Objects.requireNonNull
                    (RestaurantList.getRestaurant(order.getRestaurantID())).getFoodType().contains(FoodType.valueOf(foodType)))) {
                int newValue = restaurants.get(order.getRestaurantID()) + 1;
                restaurants.put(order.getRestaurantID(), newValue);
            }
            else if (Objects.requireNonNull(RestaurantList.getRestaurant(order.getRestaurantID())).
                    getName().contains(name) && (foodType == null || Objects.requireNonNull(RestaurantList.getRestaurant(order.getRestaurantID())).
                    getFoodType().contains(FoodType.valueOf(foodType))))
                restaurants.put(order.getRestaurantID(), 1);
        }
        List<java.util.Map.Entry<Integer , Integer>> list = new ArrayList<>(restaurants.entrySet());
        list.sort(java.util.Map.Entry.comparingByValue());
        Collections.reverse(list);
        ArrayList<Restaurant> list1 = new ArrayList<>();
        for (java.util.Map.Entry<Integer ,Integer> map : list)
            list1.add(RestaurantList.getRestaurant(map.getKey()));
        return list1;
    }
    public void confirmOrder(int customerLocation,double totalPrice, String discountToken) {
        Customer customer = (Customer) loggedInUser;
        Order order = new Order(customer.getCart(),customer.getOrderedRestaurant(),customerLocation);
        customer.getOrderedRestaurant().addOrder(order);
        customer.addOrder(order,totalPrice,discountToken);
    }
    public Order getOrderByID(ArrayList<Order> orders,int ID) {
        for (Order order : orders) {
            if (order.getID() == ID)
                return order;
        }
        return null;
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
            if (order.getOrderStatus().equals(OrderStatus.NOT_READY))
                orders.add(order);
        return orders;
    }
    public ArrayList<Order> getOrdersWithoutDeliverer() {
        ArrayList<Order> orders = new ArrayList<>();
        for (Restaurant restaurant : RestaurantList.restaurants)
            for (Order order : restaurant.getOrders())
                if (!order.hasDeliverer && order.getOrderStatus() == OrderStatus.ON_THE_WAY)
                    orders.add(order);
        return orders;
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
    private Output addCustomer(String username, String password) {
        if (getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = Customer.newCustomer(username, password);
        return Output.SUCCESSFUL_REGISTER;
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
}
