package view;

import model.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class
RestaurantMenuUsedByOwner extends Menu {
    private RestaurantMenuUsedByOwner() {
        super();
    }
    private static RestaurantMenuUsedByOwner restaurantMenuUsedByOwner;
    public static RestaurantMenuUsedByOwner getRestaurantMenuUsedByOwnerInstance() {
        if(restaurantMenuUsedByOwner == null) {
            restaurantMenuUsedByOwner = new RestaurantMenuUsedByOwner();
        }
        return restaurantMenuUsedByOwner;
    }

    //in Menu
    @Override
    public RunOrders openMenu() {
        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;
        Matcher[] matchers = new Matcher[Inputs.values().length];

        while(inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[10].find()) {
                processShowFoodType();
            } else if(matchers[11].find()) {
                processEditFoodType(matchers[11].group(1), matchers[11].group(2));
            } else if(matchers[12].find()) {
                processAddFoodType(matchers[12].group(1));
            } else if(matchers[26].find()) {
                processDisplayRating();
            } else if(matchers[23].find()) {
                processDisplayComment();
            } else if(matchers[30].find()) {
                processDisplayRatings();
            } else if(matchers[31].find()) {
                processAddResponse(Integer.parseInt(matchers[31].group(1)));
            } else if(matchers[32].find()) {
                processEditResponse(Integer.parseInt(matchers[32].group(1)));
            } else if (matchers[33].find()) {
                processShowLocation();
            } else if (matchers[34].find()) {
                processEditLocation(Integer.parseInt(matchers[34].group(1)));
            } else if (matchers[35].find()) {
                processShowOrderHistory();
            } else if (matchers[44].find()) {
                processShowOpenOrders();
            } else if(matchers[45].find()) {
                processEditOrder(Integer.parseInt(matchers[45].group(1)));
            } else if(matchers[13].find()) {
                //no process
                runOrders = RunOrders.FOODS_MENU_USED_BY_OWNER;
                System.out.println("menu opened");
                inThisMenu = false;
            } else if(input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
                runOrders = RunOrders.LOGIN_MENU;
                inThisMenu = false;
            } else if (input.matches(Inputs.BACK.commandingPattern.pattern())) {
                processBack();
                runOrders = RunOrders.RESTAURANT_OWNER_MENU;
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
            case NO_SUCH_FOOD_TYPE_IN_RESTAURANT -> {System.out.println("There is no such food type in this restaurant!");
            break;}
            case NO_SUCH_FOOD_TYPE_IN_GENERAL -> {System.out.println("There is no food type with this name!");
            break;}
            case EQUAL_FOOD_TYPES -> {System.out.println("These food types are the same!");
            break;}
            case THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE -> {System.out.println("There is still orders with this food type!");
            break;}
            case FOOD_TYPE_ALREADY_EXIST -> {System.out.println("This food type already exist in this restaurant!");
            break;}
            case FOOD_TYPE_ADDED -> {System.out.println("Food type added successfully");
            break;}
            case FOOD_TYPE_EDITED ->{ System.out.println("Food type edited successfully");
            break;}
            case SURE_EDIT_FOOD_TYPE ->{ System.out.println("Are you sure you want to change your restaurant food type?");
            break;}
            case EDIT_FOOD_TYPE_CANCELED -> {System.out.println("Edit food type canceled");
            break;}
            case LOCATION_SET -> {System.out.println("location changed");
            break;}
            case EQUAL_LOCATION -> {System.out.println("restaurant is there right now!");
            break;}
        }
    }
    private void processShowFoodType() {
        System.out.println(manager.getLoggedInUser().getActiveRestaurant().getFoodType().toString());
    }
    private void processAddFoodType(String foodType) {
        outputPrinter(manager.addFoodType(foodType));
    }
    private void processShowLocation() {
        System.out.println("Restaurant is in the node" + manager.getLoggedInUser().getActiveRestaurant().getLocation());
    }
    private void processEditLocation(int location) {
        outputPrinter(manager.editLocation(location));
    }
    private void processEditFoodType(String firstType, String secondType) {
        Output temp = manager.processEditFoodType(firstType, secondType);
        outputPrinter(temp);
        if(temp.equals(Output.SURE_EDIT_FOOD_TYPE)) {
            outputPrinter(manager.editFoodType(firstType, secondType, scanner.nextLine()));
        }
    }
    private void processDisplayComment() {
        if(manager.getLoggedInUser().getActiveRestaurant().getComments().isEmpty()) {
            System.out.println("There is no comment for this food");
        } else {
            for (Comment comment : manager.getLoggedInUser().getActiveRestaurant().getComments()) {
                System.out.println(comment.getUser().getUserName() + " said : " + comment.getComment() + " (comment ID : "+comment.getID()+" )");
                if (comment.hasResponse) {
                    System.out.println("        You " + comment.getResponse().getUser().getUserName() +
                            " have responded : " + comment.getResponse().getComment());
                }
            }
        }
    }
    private void processShowOrderHistory() {
        Restaurant restaurant = manager.getLoggedInUser().getActiveRestaurant();
        if (restaurant.getOrders().isEmpty())
            System.out.println("no one ordered from you yet");
        else {
            for (Order order : restaurant.getOrders()) {
                double totalPrice = 0;
                System.out.println("order id : " + order.getID());
                for (int i=0; i<order.getFoods().size();i++) {
                    System.out.println("Food name : " + order.getFoods().get(i).getName() + " food price : " +
                            order.getFoods().get(i).getDiscountedPrice() + " food Id : " + order.getFoods().get(i).getID() +
                            " count : " + order.getFoods().get(i));
                    totalPrice += order.getFoodsCount().get(i) * order.getFoods().get(i).getDiscountedPrice();
                }
                System.out.println("total price : " + totalPrice);
            }
        }
    }
    private void processShowOpenOrders() {
        ArrayList<Order> orders = manager.getActiveOrders();
        if (orders.isEmpty())
            System.out.println("there is no active order");
        else {
            for (Order order : orders) {
                double totalPrice = 0;
                System.out.println("order id : " + order.getID());
                for (int i=0; i<order.getFoods().size();i++) {
                    System.out.println("Food name : " + order.getFoods().get(i).getName() + " food price : " +
                            order.getFoods().get(i).getDiscountedPrice() + " food Id : " + order.getFoods().get(i).getID() +
                            " count : " + order.getFoodsCount().get(i));
                    totalPrice += order.getFoodsCount().get(i) * order.getFoods().get(i).getDiscountedPrice();
                }
                System.out.println("total price : "+totalPrice);
            }
        }
    }
    private void processEditOrder(int ID) {
        if(manager.editOrderStatus(ID)) {
            System.out.println("order status edited");
        }
        System.out.println("There is no order with this ID");
    }
}
