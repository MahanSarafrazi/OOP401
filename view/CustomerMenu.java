package view;

import model.Customer;

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
            if (matchers[20].find()) {

            } else if (matchers[8].find()) {

            }
        }

        return runOrders;
    }



}
