package view;

import model.FoodType;
import model.Restaurant;
import model.RestaurantList;

import java.util.Comparator;
import java.util.regex.Matcher;

public class CustomerMenu extends Menu {
    //making the program have just one LoginMenu object
    private CustomerMenu() {
        super();
    }
    private static CustomerMenu customerMenuInstance;
    public static CustomerMenu getCustomerMenuInstance() {
        if(customerMenuInstance == null) {
            customerMenuInstance = new CustomerMenu();
        }
        return customerMenuInstance;
    }


    //Menu managing

    @Override
    public RunOrders openMenu() {
        System.out.println("Welcome! Here is the list of restaurants");
        RestaurantList.restaurants.sort(Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getID));
        for (Restaurant restaurant : RestaurantList.restaurants) {
            System.out.println(restaurant.getName()+" "+restaurant.getID());
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
            if(matchers[6].find()) {
                processAddingRestoreQuestion();
            } else if (matchers[20].find()) {
                processSearchingRestaurant(matchers[20].group(1));
            } else if (matchers[8].find()) {
                if(processSelectingRestaurant(Integer.parseInt(matchers[8].group(1)))) {
                    runOrders = RunOrders.RESTAURANT_MENU_USED_BY_CUSTOMER;
                    inThisMenu = false;
                }
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
    private void processSearchingRestaurant(String name) {
        for (Restaurant restaurant : RestaurantList.restaurants)
            if (restaurant.getName().equals(name)) {
                System.out.print("ID : "+restaurant.getID()+" with food types:");
                for (FoodType foodType : restaurant.getFoodType())
                    System.out.print(" "+foodType);
                System.out.println(" and location : "+restaurant.getLocation());
            }
        if (RestaurantList.restaurants.isEmpty())
            System.out.println("there is no restaurant in city , lol!");
    }
}
