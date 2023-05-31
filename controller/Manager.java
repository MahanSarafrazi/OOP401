package controller;

import model.*;
import view.Output;
import java.util.ArrayList;

public class Manager {
    //only one Manager
    private Manager() {};
    private static Manager managerInstance;
    public static Manager getManagerInstance() {
        if(managerInstance == null) {
            managerInstance = new Manager();
        }
        return managerInstance;
    }

    private User loggedInUser = null;
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Output addCustomer(String username, String password) {
        for (Customer customer : UserList.getUserListInstance().getCustomers()) {
            if(customer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = Customer.newCustomer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addAdmin(String username, String password) {
        for (Admin admin : UserList.getUserListInstance().getAdmins()) {
            if(admin.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = Admin.newAdmin(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addDeliverer(String username, String password) {
        for (Deliverer deliverer : UserList.getUserListInstance().getDeliverers()) {
            if(deliverer.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
        loggedInUser = Deliverer.newDeliverer(username, password);
        return Output.SUCCESSFUL_REGISTER;
    }

    public Output addRestaurantOwner(String username, String password) {
        for (RestaurantOwner restaurantOwner : UserList.getUserListInstance().getRestaurantOwners()) {
            if(restaurantOwner.getUserName().equals(username)) {
                return Output.INVALID_USER_NAME;
            }
        }
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
    public Output logInAdmin(String username, String password) {
        for (Admin admin : UserList.getUserListInstance().getAdmins()) {
            if(admin.getUserName().equals(username)) {
                if(admin.getPassword().equals(password)) {
                    loggedInUser = admin;
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
    public Output editFoodType(String firstType, String secondType) {
        RestaurantOwner owner = (RestaurantOwner) loggedInUser;
        FoodType changingType = null, replacingType = null;
        try {
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

            return null;
        } catch (Exception e) {
            return Output.NO_ACTIVE_RESTAURANT;
        }
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
        owner.getActiveRestaurant().addFoodType(foodType1);
        return Output.FOOD_TYPE_ADDED;
    }
}
