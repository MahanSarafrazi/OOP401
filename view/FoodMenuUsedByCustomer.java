package view;

import model.Comment;

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
                processDisplayComments();
            } else if (matchers[24].find()) {
                processAddComment();
            } else if (matchers[25].find()) {
                processEditComment(Integer.parseInt(matchers[25].group()));
            } else if (matchers[26].find()) {
                processDisplayRating();
            } else if (matchers[27].find()) {
                processAddRating(Double.parseDouble(matchers[27].group(1)));
            } else if (matchers[28].find()) {
                processEditRating(Double.parseDouble(matchers[28].group(1)));
            } else if (matchers[30].find()){
                processDisplayRatings();
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                runOrders = RunOrders.RESTAURANT_MENU_USED_BY_CUSTOMER;
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
    private void processDisplayComments() {
        if(manager.getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments().isEmpty())
            System.out.println("There is no comment for this food");
        for (Comment comment :manager.getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments()) {
            System.out.println(comment.getUser().getUserName()+" said : "+comment.getComment()+" (comment ID : "+comment.getID()+" )");
            if (comment.hasResponse)
                System.out.println("        Owner "+comment.getResponse().getUser().getUserName()+
                        " has responded : "+comment.getResponse().getComment());
        }
    }
}
