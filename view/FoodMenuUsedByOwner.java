package view;

import java.util.regex.Matcher;

public class FoodMenuUsedByOwner extends Menu {
    private FoodMenuUsedByOwner() {super();}
    private static FoodMenuUsedByOwner foodMenuUsedByOwnerInstance;
    public static FoodMenuUsedByOwner getFoodMenuUsedByOwnerInstance() {
        if(foodMenuUsedByOwnerInstance == null) {
            foodMenuUsedByOwnerInstance = new FoodMenuUsedByOwner();
        }
        return foodMenuUsedByOwnerInstance;
    }

    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in Menu
        while (inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[30].find()) {

            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLoggingOut();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if(input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                runOrders = RunOrders.FOODS_MENU_USED_BY_OWNER;
                inThisMenu = false;
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                runOrders = RunOrders.EXIT;
                inThisMenu = false;
            }  else {
                System.out.println("invalid command");
            }
        }

        return runOrders;
    }

    @Override
    protected void outputPrinter(Output output) {
        super.outputPrinter(output);
    }

    private void processLoggingOut () {
        outputPrinter(manager.logoutFromFoodMenuUsedByOwner());
    }
    private void processBack() {
        manager.backFromFoodMenuUsedByOwner();
    }
}
