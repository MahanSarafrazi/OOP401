package view;

import model.RestaurantOwner;

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

    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in Menu
        while(inThisMenu) {

        }
    }
}
