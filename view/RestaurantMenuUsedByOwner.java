package view;

import model.FoodType;
import model.Restaurant;
import model.RestaurantOwner;

import java.util.ArrayList;
import java.util.Comparator;
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
        RestaurantOwner owner = (RestaurantOwner) manager.getLoggedInUser();

        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;
        Matcher[] matchers = new Matcher[Inputs.values().length];

        while(inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[6].find()) {
                runOrders = RunOrders.LOGIN_MENU;
                outputPrinter(Output.LOGOUT);
                manager.logout();
                inThisMenu = false;
            } else if(matchers[11].find()) {
                processShowFoodType();
            } else if(matchers[12].find()) {
                processEditFoodType(matchers[12].group(1), matchers[12].group(2));
            } else if(matchers[13].find()) {
                processAddFoodType(matchers[13].group(1));
            } else if(matchers[14].find()) {
                processAddFood(matchers[14].group(1), Double.parseDouble(matchers[14].group(2)), matchers[14].group(3));
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
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
    }
}
