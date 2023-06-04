package view;

import model.Comment;
import model.Rate;

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
        switch (output) {
            case NO_COMMENT_WITH_ID -> System.out.println("There is no comment with this ID!");
            case RESPONSE_ADDED -> System.out.println("Response added successfully");
            case NO_RESPONSE -> System.out.println("There in no response for this comment!");
            case RESPONSE_EDITED -> System.out.println("Response edited successfully");
        }
    }
    private void processDisplayComment() {
        if(manager.getFoodComments().size() == 0) {
            System.out.println("There is no comment for this food");
        } else {
            for (Comment comment : manager.getFoodComments()) {
                System.out.println(comment.getUser().getUserName() + " said : " + comment.getComment() + " ID : " + comment.getID());
                if (comment.hasResponse) {
                    System.out.println("        You " + comment.getResponse().getUser().getUserName() +
                            " have responded : " + comment.getResponse().getComment());
                }
            }
        }
    }
    private void processDisplayRating() {
        if(manager.getFoodRating().size() == 0) {
            System.out.println("there is no rating");
        } else {
            System.out.println("rate of this food is: " + manager.averageFoodRating());
        }
    }
    private void processDisplayRatings() {
        if(manager.getFoodRating().size() == 0) {
            System.out.println("there is no rating");
        } else {
            for (Rate rate : manager.getFoodRating()) {
                System.out.println(rate.getUser().getUserName() + " : " + rate.getRating());
            }
        }
    }
    private void processAddResponse(int ID) {
        System.out.println("please write your response:");
        String comment = scanner.nextLine();
        outputPrinter(manager.addResponse(ID, comment));
    }
    private void processEditResponse(int ID) {
        System.out.println("please write your response:");
        String comment = scanner.nextLine();
        outputPrinter(manager.editResponse(ID, comment));
    }
    private void processLoggingOut () {
        outputPrinter(manager.logoutFromFoodMenuUsedByOwner());
    }
    private void processBack() {
        manager.backFromFoodMenuUsedByOwner();
    }
}
