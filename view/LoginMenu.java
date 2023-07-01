package view;

import model.UserType;

import java.util.regex.Matcher;

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
                processAddUser(matchers[0].group(1), matchers[0].group(2),UserType.CUSTOMER);
            } else if(matchers[1].find()) {
                processAddUser(matchers[1].group(1), matchers[1].group(2),UserType.DELIVERER);
            } else if(matchers[2].find()) {
                processAddUser(matchers[2].group(1), matchers[2].group(2),UserType.RESTAURANT_OWNER);
            } else if(matchers[3].find()) {
                if(processLogInUser(matchers[3].group(1), matchers[3].group(2),UserType.CUSTOMER)) {
                    runOrders = RunOrders.CUSTOMER_MENU;
                    inThisMenu = false;
                }
            } else if(matchers[4].find()) {
                if(processLogInUser(matchers[4].group(1), matchers[4].group(2),UserType.DELIVERER)) {
                    runOrders = RunOrders.DELIVERER_MENU;
                    inThisMenu = false;
                }
            } else if(matchers[5].find()) {
                if(processLogInUser(matchers[5].group(1), matchers[5].group(2),UserType.RESTAURANT_OWNER)) {
                    runOrders = RunOrders.RESTAURANT_OWNER_MENU;
                    inThisMenu = false;
                }
            } else if(matchers[7].find()) {
                processRestoringPassword(matchers[7].group(1));
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            } else  {
                System.out.println("invalid command!");
            }
        }

        //changing conditions
        return runOrders;
    }
    @Override
    protected void outputPrinter(Output output) {
        super.outputPrinter(output);
        switch (output) {
            case INVALID_PASSWORD -> {System.out.println("password is invalid");
            break;}
            case SUCCESSFUL_LOGIN -> {System.out.println("logged in successfully");
            break;}
            case NO_RESTORE_QUESTION -> {System.out.println("you didn't set any question!");
            break;}
        }
    }

    // passing to manager
    private void processAddUser (String username, String password, UserType userType) {
        outputPrinter(manager.addUser(username,password,userType));
    }
    private boolean processLogInUser(String username, String password, UserType userType) {
        Output temp = manager.logInUser(username,password,userType);
        outputPrinter(temp);
        return temp.equals(Output.SUCCESSFUL_LOGIN);
    }
    private void processRestoringPassword(String username) {
        Output temp = manager.getRestoreQuestion(username);
        if (temp != Output.SHOW_RESTORE_QUESTION)
            outputPrinter(temp);
        else  {
            System.out.println(manager.getUser(username).getRestoreQuestion()+"?");
            if (!manager.getUser(username).getRestoreAnswer().equals(scanner.nextLine()))
                System.out.println("wrong answer!");
            else
                System.out.println("Your password is : \n"+manager.getLoggedInUser().getPassword());

        }
    }
}
