package view;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    //making the program have just one LoginMenu object
    private LoginMenu() {
        super();
    }

    private static LoginMenu loginMenu;

    public static LoginMenu getLoginMenuInstance() {
        if(loginMenu == null) {
            loginMenu = new LoginMenu();
        }
        return loginMenu;
    }
    //Menu managing
    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in menu
        while (inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[0].find()) {
                processAddCustomer(matchers[0].group(1), matchers[0].group(2));
            } else if(matchers[1].find()) {
                processAddAdmin(matchers[1].group(1), matchers[1].group(2));
            } else if(matchers[2].find()) {
                processAddDeliverer(matchers[2].group(1), matchers[2].group(2));
            } else if(matchers[3].find()) {
                processAddRestaurantOwner(matchers[3].group(1), matchers[3].group(2));
            } else if(matchers[4].find()) {
                if(processLoginCustomer(matchers[4].group(1), matchers[4].group(2))) {
                    runOrders = RunOrders.CUSTOMER_MENU;
                    inThisMenu = false;
                }
            } else if(matchers[5].find()) {
                if(processLoginAdmin(matchers[5].group(1), matchers[5].group(2))) {
                    runOrders = RunOrders.ADMIN_MENU;
                    inThisMenu = false;
                }
            } else if(matchers[6].find()) {
                if(processLoginDeliverer(matchers[6].group(1), matchers[6].group(2))) {
                    runOrders = RunOrders.DELIVERER_MENU;
                    inThisMenu = false;
                }
            } else if(matchers[7].find()) {
                if(processLoginRestaurantOwner(matchers[7].group(1), matchers[7].group(2))) {
                   runOrders = RunOrders.RESTAURANT_OWNER_MENU;
                   inThisMenu = false;
                }
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            }
        }

        //changing condition
        return runOrders;
    }
    // passing to manager
    private void processAddCustomer(String username, String password) {
        outputPrinter(manager.addCustomer(username, password));
    }
    private void processAddAdmin(String username, String password) {
        outputPrinter(manager.addAdmin(username, password));
    }
    private void processAddDeliverer(String username, String password) {
        outputPrinter(manager.addDeliverer(username, password));
    }
    private void processAddRestaurantOwner(String username, String password) {
        outputPrinter(manager.addRestaurantOwner(username, password));
    }
    private boolean processLoginCustomer(String username, String password) {
        Output temp = manager.logInCustomer(username, password);
        outputPrinter(temp);
        return temp == Output.SUCCESSFUL_LOGIN;
    }
    private boolean processLoginAdmin(String username, String password) {
        Output temp = manager.logInAdmin(username, password);
        outputPrinter(temp);
        return temp == Output.SUCCESSFUL_LOGIN;
    }
    private boolean processLoginDeliverer(String username, String password) {
        Output temp = manager.logInDeliverer(username, password);
        outputPrinter(temp);
        return temp == Output.SUCCESSFUL_LOGIN;
    }
    private boolean processLoginRestaurantOwner(String username, String password) {
        Output temp = manager.logInRestaurantOwner(username, password);
        outputPrinter(temp);
        return temp == Output.SUCCESSFUL_LOGIN;
    }
}
