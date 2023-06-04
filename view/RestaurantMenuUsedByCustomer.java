package view;

import model.Comment;
import model.Rate;

import java.util.regex.Matcher;

public class RestaurantMenuUsedByCustomer extends Menu {
    private RestaurantMenuUsedByCustomer() {
        super();
    }

    private static RestaurantMenuUsedByCustomer restaurantMenuUsedByCustomerInstance;

    public static RestaurantMenuUsedByCustomer getRestaurantMenuUsedByCustomerInstance() {
        if (restaurantMenuUsedByCustomerInstance == null) {
            restaurantMenuUsedByCustomerInstance = new RestaurantMenuUsedByCustomer();
        }
        return restaurantMenuUsedByCustomerInstance;
    }


    //Menu managing

    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;

        Matcher[] matchers = new Matcher[Inputs.values().length];
        //in Menu
        while (inThisMenu) {
            input = scanner.nextLine();
            for (int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if (matchers[21].find()) {

            } else if (matchers[22].find()) {

            } else if (matchers[23].find()) {
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
            }
        }

        return runOrders;
    }
    private void processDisplayComments() {
        for (Comment comment :manager.displayRestaurantComments()) {
            System.out.println(comment.getUser().getUserName()+" said : "+comment.getComment()+" ID : "+comment.getID());
            if (comment.hasResponse)
                System.out.println("        Owner "+comment.getResponse().getUser().getUserName()+
                        " has responded : "+comment.getResponse().getComment());
        }
    }
    private void processAddComment() {
        System.out.println("please write your comment : ");
        String comment = scanner.nextLine();
        manager.addRestaurantComment(comment);
        System.out.println("commented!");
    }
    private void processEditComment(int ID) {
        if (manager.checkRestaurantCommentID(ID) == Output.WRONG_ID)
            System.out.println("wrong id!");
        else {
            System.out.println("please write new comment :");
            String comment = scanner.nextLine();
            manager.editRestaurantComment(ID,comment);
            System.out.println("edited!");
        }
    }
    private void processDisplayRatings() {
        if (manager.getLoggedInUser().getActiveRestaurant().getRates().size() == 0)
            System.out.println("there is no rating");
        else {
            System.out.println("average rating is : " + manager.averageRestaurantRating());
            for (Rate rate : manager.displayRestaurantRatings())
                System.out.println(rate.getUser().getUserName() + " : " + rate.getRating());
        }
    }
    private void processAddRating(double rating) {
        if (rating>5 || rating<0)
            System.out.println("rating should be between 0 and 5");
        else if (manager.addRestaurantRating(rating))
            System.out.println("already rated!");
        else {
            System.out.println("rated successfully!");
        }
    }
    private void processEditRating(double rating) {
        if (rating>5 || rating<0)
            System.out.println("rating should be between 0 and 5");
        else if (!manager.editRestaurantRating(rating))
            System.out.println("you didn't rate!");
        else {
            System.out.println("edited successfully!");
        }
    }
}
