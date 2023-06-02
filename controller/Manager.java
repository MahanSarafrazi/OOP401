package controller;

import model.*;
import view.Output;
import view.RestaurantOwnerMenu;

import java.util.ArrayList;
import java.util.Objects;

public class Manager {
    //only one Manager
    private Manager() {}
    private static Manager managerInstance;
    public static Manager getManagerInstance() {
        if(managerInstance == null) {
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

    public User getUser (String username) {
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


    public Output addCustomer(String username, String password) {
        if(getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = Customer.newCustomer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addDeliverer(String username, String password) {
        if(getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = Deliverer.newDeliverer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addRestaurantOwner(String username, String password) {
        if(getUser(username) != null) {
            return Output.INVALID_USER_NAME;
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            return Output.INVALID_PASSWORD;
        loggedInUser = RestaurantOwner.newRestaurantOwner(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }
    public Output logInCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if(customer.getUserName().equals(username)) {
                if(customer.getPassword().equals(password)) {
                    loggedInUser = customer;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    public Output logInDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if(deliverer.getUserName().equals(username)) {
                if(deliverer.getPassword().equals(password)) {
                    loggedInUser = deliverer;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    public Output logInRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if(restaurantOwner.getUserName().equals(username)) {
                if(restaurantOwner.getPassword().equals(password)) {
                    loggedInUser = restaurantOwner;
                    return Output.SUCCESSFUL_LOGIN;
                }
                return Output.INVALID_PASSWORD;
            }
        }
        return Output.INVALID_USER_NAME;
    }
    public Output getRestoreQuestion(String username) {
        if (getUser(username) == null) {
            return Output.INVALID_USER_NAME;
        }
        else if (Objects.requireNonNull(getUser(username)).getRestoreQuestion() == null)
            return Output.NO_RESTORE_QUESTION;
        return Output.SHOW_RESTORE_QUESTION;
    }
    public Output getRestoreAnswer(String username, String restoreAnswer) {
        if (Objects.requireNonNull(getUser(username)).getRestoreAnswer().equals(restoreAnswer)) {
            return Output.CORRECT_ANSWER;
        }
        return Output.WRONG_ANSWER;
    }
    public void setRestore(String question,String answer) {
        loggedInUser.setRestoreQuestion(question);
        loggedInUser.setRestoreAnswer(answer);
    }
    public Output logoutFromRestaurantOwnerMenu() {
        RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(true);
        loggedInUser = null;
        return Output.LOGOUT;
    }
    public Output logoutFromRestaurantMenuUsedByOwner() {
        RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(true);
        RestaurantOwner restaurantOwner = (RestaurantOwner) loggedInUser;
        restaurantOwner.deActiveRestaurant();
        loggedInUser = null;
        return Output.LOGOUT;
    }
    public Output addRestaurant(String name, String foodType) {
        FoodType foodType1 = null;
        for (FoodType value : FoodType.values()) {
            if(value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
                break;
            }
        }

        if(foodType1 == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }

        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        owner.AddRestaurant(name, foodType1);
        return Output.SUCCESSFUL_REGISTER;
    }
    public Output selectRestaurant(long ID) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        if(owner.editActiveRestaurant(ID)) {
            return Output.SUCCESSFUL_SELECT_RESTAURANT;
        }
        return Output.INVALID_RESTAURANT_ID;
    }
    public ArrayList<FoodType> showFoodType() {
        return ((RestaurantOwner) loggedInUser).getActiveRestaurant().getFoodType();
    }
    public Output processEditFoodType(String firstType, String secondType) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        FoodType changingType = null, replacingType = null;

        for (FoodType foodType : owner.getActiveRestaurant().getFoodType()) {
            if(foodType.commandingPattern.matcher(firstType).find()) {
                changingType = foodType;
                break;
            }
        }

        if(changingType == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_RESTAURANT;
        }

        for (FoodType value : FoodType.values()) {
            if(value.commandingPattern.matcher(secondType).find()) {
                replacingType = value;
                break;
            }
        }

        if(replacingType == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }

        if(changingType == replacingType) {
            return Output.EQUAL_FOOD_TYPES;
        }

        if(owner.getActiveRestaurant().isThereAnyOrderOfThisType(changingType)) {
            return Output.THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE;
        }

        return Output.SURE_EDIT_FOOD_TYPE;
    }

    public Output editFoodType(String firstType, String secondType, String command) {
        if(command.matches("^\\s*(?i)no\\s*$")) {
            return Output.EDIT_FOOD_TYPE_CANCELED;
        } if(command.matches("^\\s*(?i)yes\\s*$")) {
            RestaurantOwner owner = (RestaurantOwner) loggedInUser;
            FoodType changingType = null, replacingType = null;
            for (FoodType foodType : owner.getActiveRestaurant().getFoodType()) {
                if(foodType.commandingPattern.matcher(firstType).find()) {
                    changingType = foodType;
                    break;
                }
            }
            for (FoodType value : FoodType.values()) {
                if(value.commandingPattern.matcher(secondType).find()) {
                    replacingType = value;
                    break;
                }
            }
            owner.getActiveRestaurant().editFoodType(changingType, replacingType);
            return Output.FOOD_TYPE_EDITED;
        }

        return Output.INVALID_COMMAND;
    }
    public Output addFoodType(String foodType) {
        FoodType foodType1 = null;
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (FoodType value : FoodType.values()) {
            if(value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
            }
        }
        if(foodType1 == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }
        for (FoodType type : owner.getActiveRestaurant().getFoodType()) {
            if(type.equals(foodType1)) {
                return Output.FOOD_TYPE_ALREADY_EXIST;
            }
        }
        owner.getActiveRestaurant().setFoodType(foodType1);
        return Output.FOOD_TYPE_ADDED;
    }

    public Output addFood(String foodName, double foodPrice, String foodType) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        if(owner.getActiveRestaurant() == null) {
            return Output.NO_ACTIVE_RESTAURANT;
        }
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if(food.getName().equals(foodName)) {
                return Output.FOOD_ALREADY_EXIST;
            }
        }
        FoodType foodType1 = null;
        for (FoodType value : FoodType.values()) {
            if(value.commandingPattern.matcher(foodType).find()) {
                foodType1 = value;
                break;
            }
        }
        if(foodType1 == null) {
            return Output.NO_SUCH_FOOD_TYPE_IN_GENERAL;
        }

        owner.getActiveRestaurant().AddFood(foodName, foodPrice, foodType1);
        return Output.FOOD_ADDED;
    }
    public ArrayList<Food> getActiveRestaurantFoods() {return ((RestaurantOwner) loggedInUser).getActiveRestaurant().getFoods();}
    public Output editFoodName(long ID, String newName) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if(food.getID() == ID) {
                food.setName(newName);
                return Output.FOOD_NAME_EDITED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }
    public Output editFoodPrice(long ID, double newPrice) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if(food.getID() == ID) {
                food.setPrice(newPrice);
                return Output.FOOD_PRICE_EDITED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }
    public Output deleteFood(long ID) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        for (Food food : owner.getActiveRestaurant().getFoods()) {
            if(food.getID() == ID) {
                owner.getActiveRestaurant().deleteFood(ID);
                return Output.FOOD_PRICE_EDITED;
            }
        }
        return Output.NO_FOOD_WITH_THIS_ID;
    }
    public Output checkRestoreQuestion() {
        if (loggedInUser.getRestoreQuestion() == null) {
            return Output.ADD_RESTORE_QUESTION;
        }
        return Output.RESTORE_QUESTION_EXISTS;
    }
    public void backFromRestaurantMenuUsedByOwner() {
        RestaurantOwnerMenu.getRestaurantOwnerMenuInstance().setAssumption(false);
        RestaurantOwner restaurantOwner = (RestaurantOwner) loggedInUser;
        restaurantOwner.deActiveRestaurant();
    }

}
