package Phase1.view;

import Phase1.model.*;

import java.util.Comparator;
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
        System.out.println("Welcome! Here is the list of restaurant foods");
        manager.getActiveRestaurantActiveFoods().sort(Comparator.comparing(Food::getName).thenComparing(Food::getID));
        for (Food food : manager.getActiveRestaurantActiveFoods()) {
            System.out.println(food.getName()+" "+ food.getID());
        }
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
                processSearchingFood(matchers[21].group(1));
            } else if (matchers[23].find()) {
                processDisplayComments();
            } else if (matchers[24].find()) {
                processAddComment();
            } else if (matchers[25].find()) {
                processEditComment(Integer.parseInt(matchers[25].group(1)));
            } else if (matchers[26].find()) {
                processDisplayRating();
            } else if (matchers[27].find()) {
                processAddRating(Double.parseDouble(matchers[27].group(1)));
            } else if (matchers[28].find()) {
                processEditRating(Double.parseDouble(matchers[28].group(1)));
            } else if (matchers[30].find()){
                processDisplayRatings();
            } else if (matchers[22].find()) {
                if(processSelectingFood(Integer.parseInt(matchers[22].group(1)))) {
                    System.out.println("food selected");
                    runOrders = RunOrders.FOOD_MENU_USED_BY_CUSTOMER;
                    inThisMenu = false;
                }
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                runOrders = RunOrders.CUSTOMER_MENU;
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
    private void processSearchingFood(String name) {
        for (Food food : manager.getActiveRestaurantActiveFoods())
            if (food.getName().equals(name)) {
                System.out.println("ID : "+food.getID()+" with discounted price : "+food.getDiscountedPrice()+" and type : "+food.getType());
            }
        if (manager.getActiveRestaurantActiveFoods().isEmpty())
            System.out.println("there is no active food in this restaurant.");
    }
    private void processDisplayComments() {
        if(manager.getLoggedInUser().getActiveRestaurant().getComments().isEmpty())
            System.out.println("There is no comment for this food");
        for (Comment comment : manager.getLoggedInUser().getActiveRestaurant().getComments()) {
            System.out.println(comment.getUser().getUserName()+" said : "+comment.getComment()+" (comment ID : "+comment.getID()+" )");
            if (comment.hasResponse)
                System.out.println("        Owner "+comment.getResponse().getUser().getUserName()+
                        " has responded : "+comment.getResponse().getComment());
        }
    }
}
