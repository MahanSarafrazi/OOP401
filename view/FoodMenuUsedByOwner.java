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
        return runOrders;
    }
}
