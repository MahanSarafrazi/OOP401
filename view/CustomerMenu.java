package view;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CustomerMenu extends Menu {
    //making the program have just one LoginMenu object
    public CustomerMenu() {
        super();
    }
    private static ArrayList <CustomerMenu> customerMenus;
    public static ArrayList<CustomerMenu> getCustomerMenus() {
        return customerMenus;
    }

    //Menu managing

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
