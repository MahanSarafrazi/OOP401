package view;

import model.Food;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class FoodsMenuUsedByOwner extends Menu {
    private FoodsMenuUsedByOwner() {super();}
    private static FoodsMenuUsedByOwner foodsMenuUsedByOwnerInstance;
    public static FoodsMenuUsedByOwner getFoodsMenuUsedByOwnerInstance() {
        if(foodsMenuUsedByOwnerInstance == null) {
            foodsMenuUsedByOwnerInstance = new FoodsMenuUsedByOwner();
        }
        return foodsMenuUsedByOwnerInstance;
    }


    //Menu managing

    @Override
    public RunOrders openMenu() {
        manager.getLoggedInUser().getActiveRestaurant().getFoods().sort(Comparator.comparing(Food::getName).thenComparing(Food::getID));
        ArrayList<Food> activeRestaurantFoods = manager.getLoggedInUser().getActiveRestaurant().getFoods();
        if(activeRestaurantFoods.isEmpty()) {
            System.out.println("There is no food in your restaurant");
        } else {
            for (Food food : activeRestaurantFoods) {
                System.out.print("food name: " + food.getName() + "  ID: " + food.getID() + "  price: " + food.getPrice() + "  active discount: " + food.getDiscount());
                if (food.getDiscount() != 0)
                    System.out.print(" discounted price: "+food.getDiscountedPrice());
                System.out.println("  food type:" + food.getType());
            }
        }

        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in Menu
        while(inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[14].find()) {
                processAddFood(matchers[14].group(1), Double.parseDouble(matchers[14].group(2)), matchers[14].group(3));
            } else if(matchers[15].find()) {
                processEditFoodName(Integer.parseInt(matchers[15].group(1)), matchers[15].group(2));
            } else if(matchers[16].find()) {
                processEditFoodPrice(Integer.parseInt(matchers[16].group(1)), Double.parseDouble(matchers[16].group(2)));
            } else if(matchers[17].find()) {
                processDeleteFood(Integer.parseInt(matchers[17].group(1)));
            } else if(matchers[18].find()) {
                processDeActiveFood(Integer.parseInt(matchers[18].group(1)));
            } else if(matchers[19].find()) {
                processActiveFood(Integer.parseInt(matchers[19].group(1)));
            } else if (matchers[29].find()) {
                processDiscount(Integer.parseInt(matchers[29].group(1)), Double.parseDouble(matchers[29].group(2)), Integer.parseInt(matchers[29].group(3)));
            } else if(matchers[30].find()) {
                processDisplayRating();
            } else if(matchers[22].find()) {
                if(processSelectingFood(Integer.parseInt(matchers[22].group(1)))) {
                    System.out.println("food selected");
                    runOrders = RunOrders.FOOD_MENU_USED_BY_OWNER;
                    inThisMenu = false;
                }
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                //no process
                System.out.println("back to restaurant menu");
                runOrders = RunOrders.RESTAURANT_MENU_USED_BY_OWNER;
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
            case FOOD_ALREADY_EXIST -> System.out.println("This food already exist in this restaurant");
            case FOOD_ADDED -> System.out.println("Food added successfully");
            case NO_FOOD_WITH_THIS_ID -> System.out.println("There is no food with this ID in your restaurant");
            case FOOD_NAME_EDITED -> System.out.println("Food name edited successfully");
            case FOOD_PRICE_EDITED -> System.out.println("Food price edited successfully");
            case FOOD_DELETED -> System.out.println("Food deleted successfully");
            case FOOD_ACTIVATED -> System.out.println("Food activated successfully");
            case FOOD_DEACTIVATED -> System.out.println("Food deactivated successfully");
            case THERE_ARE_FOODS_IN_ORDER -> System.out.println("There are still foods in orders with this ID");
            case FOOD_ALREADY_DISCOUNTED -> System.out.println("This food is already discounted");
            case WRONG_PERCENT_AMOUNT -> System.out.println("wrong percent amount!");
            case FOOD_DISCOUNTED -> System.out.println("Food discounted successfully");
            case INVALID_TIME -> System.out.println("Invalid time!");
        }
    }

    private void processAddFood(String foodName, double foodPrice, String foodType) {
        outputPrinter(manager.addFood(foodName, foodPrice, foodType));
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
    private void processDiscount(int ID, double discountPercent, int hours) {
        outputPrinter(manager.discountFood(ID, discountPercent, hours));
    }
}
