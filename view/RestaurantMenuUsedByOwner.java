package view;

import model.Food;
import model.FoodType;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RestaurantMenuUsedByOwner extends Menu {
    private RestaurantMenuUsedByOwner() {
        super();
    }
    private static RestaurantMenuUsedByOwner restaurantMenuUsedByOwner;
    public static RestaurantMenuUsedByOwner getRestaurantMenuUsedByOwnerInstance() {
        if(restaurantMenuUsedByOwner == null) {
            restaurantMenuUsedByOwner = new RestaurantMenuUsedByOwner();
        }
        return restaurantMenuUsedByOwner;
    }

    //in Menu
    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;
        Matcher[] matchers = new Matcher[Inputs.values().length];

        while(inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[10].find()) {
                processShowFoodType();
            } else if(matchers[11].find()) {
                processEditFoodType(matchers[11].group(1), matchers[11].group(2));
            } else if(matchers[12].find()) {
                processAddFoodType(matchers[12].group(1));
            } else if(matchers[13].find()) {
                processAddFood(matchers[13].group(1), Double.parseDouble(matchers[13].group(2)), matchers[13].group(3));
            } else if(matchers[14].find()) {
                processShowFoods();
            } else if(matchers[15].find()) {
                processEditFoodName(Integer.parseInt(matchers[15].group(1)), matchers[15].group(2));
            }else if(matchers[16].find()) {
                processEditFoodPrice(Integer.parseInt(matchers[16].group(1)), Double.parseDouble(matchers[16].group(2)));
            } else if(matchers[17].find()) {
                processDeleteFood(Integer.parseInt(matchers[17].group(1)));
            } else if(matchers[18].find()) {
                processDeActiveFood(Integer.parseInt(matchers[18].group(1)));
            } else if(matchers[19].find()) {
                processActiveFood(Integer.parseInt(matchers[19].group(1)));
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLoggingOut();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                runOrders = RunOrders.RESTAURANT_OWNER_MENU;
                inThisMenu = false;
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            }  else {
                System.out.println("invalid command");
            }
        }

        return runOrders;
    }

    @Override
    protected void outputPrinter(Output output) {
        super.outputPrinter(output);
        switch (output) {
            case SUCCESSFUL_SELECT_RESTAURANT -> System.out.println("Restaurant selected successfully");
            case INVALID_RESTAURANT_ID -> System.out.println("There is no restaurant with this ID!");
            case NO_ACTIVE_RESTAURANT -> System.out.println("You haven't logged in in any restaurant!");
            case NO_SUCH_FOOD_TYPE_IN_RESTAURANT -> System.out.println("There is no such food type in this restaurant!");
            case NO_SUCH_FOOD_TYPE_IN_GENERAL -> System.out.println("There is no food type with this name!");
            case EQUAL_FOOD_TYPES -> System.out.println("These food types are the same!");
            case THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE -> System.out.println("There is still orders with this food type!");
            case FOOD_TYPE_ALREADY_EXIST -> System.out.println("This food type already exist in this restaurant!");
            case FOOD_TYPE_ADDED -> System.out.println("Food type added successfully");
            case FOOD_TYPE_EDITED -> System.out.println("Food type edited successfully");
            case SURE_EDIT_FOOD_TYPE -> System.out.println("Are you sure you want to change your restaurant food type?");
            case EDIT_FOOD_TYPE_CANCELED -> System.out.println("Edit food type canceled");
            case FOOD_ALREADY_EXIST -> System.out.println("This food already exist in this restaurant");
            case FOOD_ADDED -> System.out.println("Food added successfully");
            case LOGOUT -> System.out.println("Logged out successfully");
            case ADD_RESTORE_QUESTION -> System.out.println("Please set your restore question");
            case ADD_RESTORE_ANSWER -> System.out.println("Please set the answer");
            case RESTORE_QUESTION_EXISTS -> System.out.println("Restore question already exists");
            case RESTORE_QUESTION_ADDED -> System.out.println("Restore question added");
            case NO_FOOD_WITH_THIS_ID -> System.out.println("There is no food with this ID in your restaurant");
            case FOOD_NAME_EDITED -> System.out.println("Food name edited successfully");
            case FOOD_PRICE_EDITED -> System.out.println("Food price edited successfully");
            case FOOD_DELETED -> System.out.println("Food deleted successfully");
            case FOOD_ACTIVATED -> System.out.println("Food activated successfully");
            case FOOD_DEACTIVATED -> System.out.println("Food deactivated successfully");
            case THERE_ARE_FOODS_IN_ORDER -> System.out.println("There are still foods in orders with this ID");
        }
    }
    private void outputPrinter(Output output,ArrayList<Food> foods) {
        switch (output) {
            case NO_FOOD_IN_RESTAURANT -> System.out.println("There is no food in your restaurant");
            case SHOW_FOODS -> {
                for (Food food : foods) {
                    System.out.print("food name: " + food.getName() + "  ID: " + food.getID() + "  price: " + food.getPrice() + "  active discount: " + food.getDiscount());
                    if (food.getDiscount() != 0)
                        System.out.print(" discounted price: "+food.getDiscountedPrice());
                    System.out.println("  food type:" + food.getType());
                }
            }
        }
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
    private void processAddFood(String foodName, double foodPrice, String foodType) {
        outputPrinter(manager.addFood(foodName, foodPrice, foodType));
        processShowFoods();
    }
    private void processShowFoods() {
        outputPrinter(manager.showFoods(),manager.getActiveRestaurantFoods());
    }
    private void processEditFoodName(int ID, String newName) {
        outputPrinter(manager.editFoodName(ID, newName));
    }
    private void processEditFoodPrice(int ID, double newPrice) {
        outputPrinter(manager.editFoodPrice(ID, newPrice));
    }
    private void processDeleteFood(int ID) {
        outputPrinter(manager.deleteFood(ID));
    }
    private void processDeActiveFood(int ID) {
        outputPrinter(manager.deActiveFood(ID));
    }
    private void processActiveFood(int ID) {
        outputPrinter(manager.activeFood(ID));
    }
    private void processLoggingOut () {
        outputPrinter(manager.logoutFromRestaurantMenuUsedByOwner());
    }
    private void processBack() {
        manager.backFromRestaurantMenuUsedByOwner();
    }
}
