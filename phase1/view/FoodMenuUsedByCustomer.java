package phase1.view;

import phase1.model.Comment;
import phase1.model.Customer;

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
                processEditComment(Integer.parseInt(matchers[25].group(1)));
            } else if (matchers[26].find()) {
                processDisplayRating();
            } else if (matchers[27].find()) {
                processAddRating(Double.parseDouble(matchers[27].group(1)));
            } else if (matchers[28].find()) {
                processEditRating(Double.parseDouble(matchers[28].group(1)));
            } else if (matchers[30].find()){
                processDisplayRatings();
            } else if (matchers[42].find()) {
                processAddFoodToCart(Integer.parseInt(matchers[42].group(1)));
            } else if (matchers[43].find()) {
                processRemoveFoodFromCart();
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                runOrders = RunOrders.RESTAURANT_MENU_USED_BY_CUSTOMER;
                inThisMenu = false;
            } else if(input.matches(Inputs.EXIT_PROGRAM.commandingPattern.pattern())) {
                processExit();
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
                System.out.println("        Owner "+comment.getResponderName()+
                        " has responded : "+comment.getResponse()+" (comment ID : "+comment.getID()+" )");
        }
    }
    private void processAddFoodToCart(int count) {
        Customer customer = (Customer) manager.getLoggedInUser();
        if (count<1)
            System.out.println("ordered count should be positive");
        else {
            if (customer.getOrderedRestaurant() == null || customer.getOrderedRestaurant().getID() == customer.getActiveRestaurant().getID()) {
                if (customer.getOrderedRestaurant() == null)
                    customer.setOrderedRestaurant(customer.getActiveRestaurant());
                customer.getCart().put(customer.getActiveRestaurant().getOpenedFood(), count);
                System.out.println("added to cart");
            }
            else {
                System.out.println("you can't order from two different restaurants.");
            }
        }
    }
    private void processRemoveFoodFromCart() {
        Customer customer = (Customer) manager.getLoggedInUser();
        if(customer.getCart().getFoods().contains(customer.getActiveRestaurant().getOpenedFood())) {
            customer.getCart().remove(customer.getActiveRestaurant().getOpenedFood());
            System.out.println("removed");
        }
        else
            System.out.println("food is not in cart");
    }
}
