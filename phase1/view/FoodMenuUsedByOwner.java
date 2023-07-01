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

    @Override
    protected void outputPrinter(Output output) {
        super.outputPrinter(output);
        switch (output) {
            case NO_COMMENT_WITH_ID -> System.out.println("There is no comment with this ID!");
            case RESPONSE_ADDED -> System.out.println("Response added successfully");
            case NO_RESPONSE -> System.out.println("There in no response for this comment!");
            case RESPONSE_EDITED -> System.out.println("Response edited successfully");
        }
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
