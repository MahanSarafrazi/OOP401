package view;

public class LoginMenu extends Menu {
    //making the program have just one LoginMenu object
    private LoginMenu() {
        super();
    }

    private static LoginMenu loginMenu;
    private boolean inThisMenu;
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
        inThisMenu = true;
        RunOrders runOrders = null;

        //in menu
        while (inThisMenu) {
            input = scanner.nextLine();
            if(input.matches(Inputs.ADD_CUSTOMER.commandingPattern.pattern())) {
                processAddCustomer();
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            }
        }

        //changing condition
        return runOrders;
    }
    // passing to manager
    private void processAddCustomer() {
        System.out.println("add");
    }
}
