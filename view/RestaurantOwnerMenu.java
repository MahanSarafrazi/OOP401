package view;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOwner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class RestaurantOwnerMenu extends Menu {
    private RestaurantOwnerMenu() {
        super();
    }
    private static RestaurantOwnerMenu restaurantOwnerMenu;
    public static RestaurantOwnerMenu getRestaurantOwnerMenuInstance() {
        if(restaurantOwnerMenu == null) {
            restaurantOwnerMenu = new RestaurantOwnerMenu();
        }
        return restaurantOwnerMenu;
    }

    //in Menu
    @Override
    public RunOrders openMenu() {
        RestaurantOwner owner = (RestaurantOwner) manager.getLoggedInUser();
        if(owner.getRestaurants().size() > 1) {
            System.out.println("Welcome! Here is the list of your restaurants");
            owner.getRestaurants().sort(Comparator.comparing(Restaurant::getName).reversed().thenComparing(Restaurant::getID).reversed());
            for (Restaurant restaurant : owner.getRestaurants()) {
                System.out.println(restaurant.getName());
            }
        } else if(owner.getRestaurants().size() == 1) {
            System.out.println("Welcome to your restaurant!");
            owner.editActiveRestaurant(owner.getRestaurants().get(0));
        } else {
            System.out.println("Welcome! please register a restaurant");
        }

        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;
        Matcher[] matchers = new Matcher[Inputs.values().length];

        while(inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(owner.getActiveRestaurant() == null && matchers[8].find()) {
                processSelectingRestaurant(Long.parseLong(matchers[8].group(1)));
            } else if(matchers[9].find()) {
                processAddingRestaurant(matchers[9].group(1), matchers[9].group(2));
            } else if(matchers[10].find()) {
                processShowFoodType();
            } else if(matchers[11].find()) {
                processEditFoodType(matchers[11].group(1), matchers[11].group(2));
            } else if(matchers[12].find()) {
                processAddFoodType(matchers[12].group(1));
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            } else {
                System.out.println("invalid command");
            }
        }

        return runOrders;
    }

    @Override
    protected void outputPrinter(Output output) {
        super.outputPrinter(output);
        switch (output) {
            case SUCCESSFUL_SELECT_RESTAURANT -> {
                System.out.println("Restaurant selected successfully");
            } case INVALID_RESTAURANT_ID -> {
                System.out.println("There is no restaurant with this ID!");
            } case NO_ACTIVE_RESTAURANT -> {
                System.out.println("You haven't logged in in any restaurant!");
            } case NO_SUCH_FOOD_TYPE_IN_RESTAURANT -> {
                System.out.println("There is no such food type in this restaurant!");
            } case NO_SUCH_FOOD_TYPE_IN_GENERAL -> {
                System.out.println("There is no food type with this name!");
            } case EQUAL_FOOD_TYPES -> {
                System.out.println("These food types are the same!");
            } case THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE -> {
                System.out.println("There is still orders with this food type!");
            } case FOOD_TYPE_ALREADY_EXIST -> {
                System.out.println("This food type already exist in this restaurant!");
            } case FOOD_TYPE_ADDED -> {
                System.out.println("Food type added successfully");
            } case FOOD_TYPE_EDITED -> {
                System.out.println("Food type edited successfully");
            } case SURE_EDIT_FOOD_TYPE -> {
                System.out.println("Are you sure you want to change your restaurant food type?");
            } case EDIT_FOOD_TYPE_CANCELED -> {
                System.out.println("Edit food type canceled");
            } case INVALID_COMMAND -> {
                System.out.println("Invalid command!");
            }
        }
    }

    //passing to manager
    private void processSelectingRestaurant(long ID) {
        outputPrinter(manager.selectRestaurant(ID));
    }
    private void processAddingRestaurant(String name, String foodType) {
        outputPrinter(manager.addRestaurant(name, foodType));
    }
    private void processShowFoodType() {
        ArrayList<FoodType> foodTypes = manager.showFoodType();
        System.out.println("food types are:");
        for (FoodType foodType : foodTypes) {
            System.out.println(foodType);
        }
    }
    private void processEditFoodType(String firstType, String secondType) {
        Output temp = manager.processEditFoodType(firstType, secondType);
        outputPrinter(temp);
        if(temp.equals(Output.SURE_EDIT_FOOD_TYPE)) {
            outputPrinter(manager.editFoodType(firstType, secondType, scanner.nextLine()));
        }
    }
    private void processAddFoodType(String foodType) {
        outputPrinter(manager.addFoodType(foodType));
    }
}
