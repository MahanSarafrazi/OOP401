package view;

import java.util.regex.Matcher;

public class FoodMenuUsedByCustomer extends Menu {
    private FoodMenuUsedByCustomer() {super();}
    private static FoodMenuUsedByCustomer foodMenuUsedByCustomerInstance;
    public static FoodMenuUsedByCustomer getFoodMenuUsedByCustomerInstance() {
        if(foodMenuUsedByCustomerInstance == null) {
            foodMenuUsedByCustomerInstance = new FoodMenuUsedByCustomer();
        }
        return foodMenuUsedByCustomerInstance;
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
            if (matchers[23].find()) {
                processDisplayComments()
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
