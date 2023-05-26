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
    //owner
    private RestaurantOwner owner;
    private Restaurant activeRestaurant;
    public RestaurantOwner getOwner() {
        return owner;
    }

    //in Menu
    @Override
    public RunOrders openMenu() {
        owner = (RestaurantOwner) manager.getLoggedInUser();
        if(owner.getRestaurants().size() > 1) {
            System.out.println("Welcome! Here is the list of your restaurants");
            owner.getRestaurants().sort(Comparator.comparing(Restaurant::getName).reversed().thenComparing(Restaurant::getID).reversed());
            for (Restaurant restaurant : owner.getRestaurants()) {
                System.out.println(restaurant.getName());
            }
        } else if(owner.getRestaurants().size() == 1) {
            System.out.println("Welcome to your restaurant!");
            activeRestaurant = owner.getRestaurants().get(0);
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
            if(activeRestaurant == null && matchers[8].find()) {
                processSelectingRestaurant(Long.parseLong(matchers[8].group(1)));
            } else if(matchers[9].find()) {
                processAddingRestaurant(matchers[9].group(1));
            } else if(matchers[10].find()) {
                processShowFoodType();
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            }
        }

        return runOrders;
    }


    private void processSelectingRestaurant(long ID) {
        Restaurant temp = manager.selectRestaurant(ID);
        if(temp == null) {
            System.out.println("There is no restaurant with this ID!");
        } else {
            activeRestaurant = temp;
            System.out.println("Restaurant selected successfully");
        }
    }
    private void processAddingRestaurant(String name) {
        outputPrinter(manager.addRestaurant(name));
    }
    private void processShowFoodType() {
        ArrayList<FoodType> foodTypes = manager.showFoodType();
        System.out.println("food types are:");
        for (FoodType foodType : foodTypes) {
            System.out.println(foodType);
        }
    }
}
