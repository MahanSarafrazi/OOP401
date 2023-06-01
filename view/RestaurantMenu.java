package view;

import model.RestaurantOwner;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RestaurantOwnerMenu extends Menu {
    //only one class
    public RestaurantOwnerMenu() {
        super();
    }
    private static ArrayList<RestaurantOwnerMenu> restaurantOwnerMenus;
    public static ArrayList<RestaurantOwnerMenu> getRestaurantOwnerMenus() {
        return restaurantOwnerMenus;
    }

    //who is the owner?
    private RestaurantOwner restaurantOwner;

    //in Menu
    @Override
    public RunOrders openMenu() {
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
        }
    }
}
