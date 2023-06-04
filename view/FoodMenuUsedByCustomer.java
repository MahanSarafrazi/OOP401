package view;

import model.Comment;
import model.Rate;

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
                processDisplayRatings();
            } else if (matchers[27].find()) {
                processAddRating(Double.parseDouble(matchers[27].group(1)));
            } else if (matchers[28].find()) {
                processEditRating(Double.parseDouble(matchers[28].group(1)));
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLoggingOut();
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
        for (Comment comment :manager.displayFoodComments()) {
            System.out.println(comment.getUser().getUserName()+" said : "+comment.getComment()+" ID : "+comment.getID());
            if (comment.hasResponse)
                System.out.println("        Owner "+comment.getResponse().getUser().getUserName()+
                        " has responded : "+comment.getResponse().getComment());
        }
    }
    private void processAddComment() {
        System.out.println("please write your comment : ");
        String comment = scanner.nextLine();
        manager.addFoodComment(comment);
        System.out.println("commented!");
    }
    private void processEditComment(int ID) {
        if (manager.checkFoodCommentID(ID) == Output.WRONG_ID)
            System.out.println("wrong id!");
        else {
            System.out.println("please write new comment :");
            String comment = scanner.nextLine();
            manager.editFoodComment(ID,comment);
            System.out.println("edited!");
        }
    }
    private void processDisplayRatings() {
        if (manager.getLoggedInUser().getActiveRestaurant().getActiveFood().getRates().size() == 0)
            System.out.println("there is no rating");
        else {
            System.out.println("average rating is : " + manager.averageFoodRating());
            for (Rate rate : manager.displayFoodRatings())
                System.out.println(rate.getUser().getUserName() + " : " + rate.getRating());
        }
    }
    private void processAddRating(double rating) {
        if (rating>5 || rating<0)
            System.out.println("rating should be between 0 and 5");
        else if (manager.addFoodRating(rating))
            System.out.println("already rated!");
        else {
            System.out.println("rated successfully!");
        }
    }
    private void processEditRating(double rating) {
        if (rating>5 || rating<0)
            System.out.println("rating should be between 0 and 5");
        else if (!manager.editFoodRating(rating))
            System.out.println("you didn't rate!");
        else {
            System.out.println("edited successfully!");
        }
    }
    private void processLoggingOut () {
        outputPrinter(manager.logoutFromFoodMenuUsedByCustomer());
    }
    private void processBack() {
        manager.backFromFoodMenuUsedByCustomer();
    }
}
