package view;

import model.Deliverer;

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
    private static Character owner;


    //Menu managing

    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in Menu
        while(inThisMenu) {

        }

        return runOrders;
    }
}
