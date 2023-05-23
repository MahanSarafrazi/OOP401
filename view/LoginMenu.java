package view;

import controller.Manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Menu {
    //making the program have just one LoginMenu object
    private LoginMenu() {
        super();
    }

    private static LoginMenu loginMenu;

    public static LoginMenu getLoginMenu() {
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


    //OutputPrinter
    private void outputPrinter(Output output) {
        switch (output) {
            case INVALID_USER_NAME -> {
                System.out.println("user name is invalid");
            } case INVALID_PASSWORD -> {
                System.out.println("password is invalid");
            } case SUCCESSFUL_LOGIN -> {
                System.out.println("logged in successfully");
            }
        }
    }
}
