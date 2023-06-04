package view;

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
                System.out.println(manager.getLoggedInUser().getActiveRestaurant().getFoodType().toString());
            } else if(matchers[11].find()) {
                processEditFoodType(matchers[11].group(1), matchers[11].group(2));
            } else if(matchers[12].find()) {
                outputPrinter(manager.addFoodType(matchers[12].group(1)));
            } else if(matchers[13].find()) {
                runOrders = RunOrders.FOODS_MENU_USED_BY_OWNER;
                inThisMenu = false;
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                manager.back();
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
            case NO_SUCH_FOOD_TYPE_IN_RESTAURANT -> System.out.println("There is no such food type in this restaurant!");
            case NO_SUCH_FOOD_TYPE_IN_GENERAL -> System.out.println("There is no food type with this name!");
            case EQUAL_FOOD_TYPES -> System.out.println("These food types are the same!");
            case THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE -> System.out.println("There is still orders with this food type!");
            case FOOD_TYPE_ALREADY_EXIST -> System.out.println("This food type already exist in this restaurant!");
            case FOOD_TYPE_ADDED -> System.out.println("Food type added successfully");
            case FOOD_TYPE_EDITED -> System.out.println("Food type edited successfully");
            case SURE_EDIT_FOOD_TYPE -> System.out.println("Are you sure you want to change your restaurant food type?");
            case EDIT_FOOD_TYPE_CANCELED -> System.out.println("Edit food type canceled");
        }
    }
    private void processEditFoodType(String firstType, String secondType) {
        Output temp = manager.processEditFoodType(firstType, secondType);
        outputPrinter(temp);
        if(temp.equals(Output.SURE_EDIT_FOOD_TYPE)) {
            outputPrinter(manager.editFoodType(firstType, secondType, scanner.nextLine()));
        }
    }
}
