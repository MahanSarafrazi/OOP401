package phase1.view;

import phase1.model.Comment;

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
            if(matchers[26].find()) {
                processDisplayRating();
            } else if(matchers[23].find()) {
                processDisplayComment();
            } else if(matchers[30].find()) {
                processDisplayRatings();
            } else if(matchers[31].find()) {
                processAddResponse(Integer.parseInt(matchers[31].group(1)));
            } else if(matchers[32].find()) {
                processEditResponse(Integer.parseInt(matchers[32].group(1)));
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if(input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                System.out.println("back to foods menu");
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

    private void processDisplayComment() {
        if(manager.getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments().isEmpty()) {
            System.out.println("There is no comment for this food");
        } else {
            for (Comment comment : manager.getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments()) {
                System.out.println(comment.getUser().getUserName() + " said : " + comment.getComment() + " (comment ID : "+comment.getID()+" )");
                if (comment.hasResponse) {
                    System.out.println("        You " + comment.getResponse().getUser().getUserName() +
                            " have responded : " + comment.getResponse().getComment());
                }
            }
        }
    }
}
