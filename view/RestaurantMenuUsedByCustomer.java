package view;

import java.util.regex.Matcher;

public class RestaurantMenuUsedByCustomer extends Menu {
    private RestaurantMenuUsedByCustomer() {
        super();
    }

    private static RestaurantMenuUsedByCustomer restaurantMenuUsedByCustomerInstance;

    public static RestaurantMenuUsedByCustomer getRestaurantMenuUsedByCustomerInstance() {
        if (restaurantMenuUsedByCustomerInstance == null) {
            restaurantMenuUsedByCustomerInstance = new RestaurantMenuUsedByCustomer();
        }
        return restaurantMenuUsedByCustomerInstance;
    }


    //Menu managing

    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in Menu
        while (inThisMenu) {
            input = scanner.nextLine();
            for (int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if (matchers[21].find()) {

            } else if (matchers[22].find()) {

            } else if (matchers[23].find()) {

            } else if (matchers[24].find()) {

            } else if (matchers[25].find()) {

            } else if (matchers[26].find()) {

            } else if (matchers[27].find()) {

            } else if (matchers[28].find()) {

            }
        }

        return runOrders;
    }
}
