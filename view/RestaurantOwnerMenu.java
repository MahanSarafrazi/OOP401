package view;

import model.Restaurant;
import model.RestaurantOwner;

import java.util.Comparator;
import java.util.regex.Matcher;

public class RestaurantOwnerMenu extends Menu {
    private RestaurantOwnerMenu() {
        super();
        assumption = true;
    }
    private static RestaurantOwnerMenu restaurantOwnerMenu;
    public static RestaurantOwnerMenu getRestaurantOwnerMenuInstance() {
        if(restaurantOwnerMenu == null) {
            restaurantOwnerMenu = new RestaurantOwnerMenu();
        }
        return restaurantOwnerMenu;
    }
    private boolean assumption ;
    public void setAssumption(boolean value) {
        assumption = value;
    }

    //in Menu
    @Override
    public RunOrders openMenu() {
        RestaurantOwner owner = (RestaurantOwner) manager.getLoggedInUser();
        if(owner.getRestaurants().size() > 1 || !assumption) {
            System.out.println("Welcome! Here is the list of your restaurants");
            owner.getRestaurants().sort(Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getID));
            for (Restaurant restaurant : owner.getRestaurants()) {
                System.out.println(restaurant.getName()+" "+restaurant.getID());
            }
        } else if(owner.getRestaurants().size() == 1) {
            System.out.println("Welcome to your restaurant!");
            owner.setActiveRestaurant(owner.getRestaurants().get(0));
            return RunOrders.RESTAURANT_MENU_USED_BY_OWNER;
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
            if(matchers[6].find()) {
                processAddingRestoreQuestion();
            } else if(matchers[8].find()) {
                if(processSelectingRestaurant(Integer.parseInt(matchers[8].group(1)))) {
                    System.out.println("Welcome to your restaurant!");
                    runOrders = RunOrders.RESTAURANT_MENU_USED_BY_OWNER;
                    inThisMenu = false;
                }
            } else if(matchers[9].find()) {
                processAddingRestaurant(matchers[9].group(1), matchers[9].group(2), Integer.parseInt(matchers[9].group(3)));
            } else if (input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
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
            case SUCCESSFUL_REGISTER : {
                RestaurantOwner owner = (RestaurantOwner) manager.getLoggedInUser();
                owner.getRestaurants().sort(Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getID));
                for (Restaurant restaurant : owner.getRestaurants()) {
                    System.out.println(restaurant.getName()+" "+restaurant.getID());
                }}
            case NO_SUCH_FOOD_TYPE_IN_GENERAL : System.out.println("There is no food type with this name!");}
    }

    //passing to manager
    private void processAddingRestaurant(String name, String foodType, int location) {
        outputPrinter(manager.addRestaurant(name, foodType, location));
    }

}
