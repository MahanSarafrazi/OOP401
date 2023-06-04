package view;

import model.Restaurant;
import model.RestaurantOwner;

import java.util.Comparator;
import java.util.regex.Matcher;

public class RestaurantOwnerMenu extends Menu {
    private RestaurantOwnerMenu() {
        super();
        assumption = true;
    }
    private static RestaurantOwnerMenu restaurantOwnerMenu;
    public static RestaurantOwnerMenu getRestaurantOwnerMenuInstance() {
        if(restaurantOwnerMenu == null) {
            restaurantOwnerMenu = new RestaurantOwnerMenu();
        }
        return restaurantOwnerMenu;
    }
    private boolean assumption ;
    public void setAssumption(boolean value) {
        assumption = value;
    }

    //in Menu
    @Override
    public RunOrders openMenu() {
        RestaurantOwner owner = (RestaurantOwner) manager.getLoggedInUser();
        if(owner.getRestaurants().size() > 1 || !assumption) {
            System.out.println("Welcome! Here is the list of your restaurants");
            owner.getRestaurants().sort(Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getID));
            for (Restaurant restaurant : owner.getRestaurants()) {
                System.out.println(restaurant.getName()+" "+restaurant.getID());
            }
        }
        else if(owner.getRestaurants().size() == 1) {
            System.out.println("Welcome to your restaurant!");
            owner.setActiveRestaurant(owner.getRestaurants().get(0));
            return RunOrders.RESTAURANT_MENU_USED_BY_OWNER;
        }
        else {
            System.out.println("Welcome! please register a restaurant");
        }

        String input;
        boolean inThisMenu = true;
        RunOrders runOrders = null;
        Matcher[] matchers = new Matcher[Inputs.values().length];

        while(inThisMenu) {
            input = scanner.nextLine();
            for(int i = 0; i < Inputs.values().length; ++i) {
                matchers[i] = Inputs.getPatterns()[i].matcher(input);
            }
            if(matchers[6].find()) {
                processAddingRestoreQuestion();
            }
            else if(owner.getActiveRestaurant() == null && matchers[8].find()) {
                if(processSelectingRestaurant(Integer.parseInt(matchers[8].group(1)))) {
                    System.out.println("Welcome to your restaurant!");
                    runOrders = RunOrders.RESTAURANT_MENU_USED_BY_OWNER;
                    inThisMenu = false;
                }
            } else if(matchers[9].find()) {
                processAddingRestaurant(matchers[9].group(1), matchers[9].group(2));
            } else if (input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLoggingOut();
                runOrders = RunOrders.LOGIN_MENU;
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
            case SUCCESSFUL_REGISTER : {
                RestaurantOwner owner = (RestaurantOwner) manager.getLoggedInUser();
                owner.getRestaurants().sort(Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getID));
                for (Restaurant restaurant : owner.getRestaurants()) {
                    System.out.println(restaurant.getName()+" "+restaurant.getID());
                }}
            case SUCCESSFUL_SELECT_RESTAURANT : System.out.println("Restaurant selected successfully");
            case INVALID_RESTAURANT_ID : System.out.println("There is no restaurant with this ID!");
            case NO_ACTIVE_RESTAURANT : System.out.println("You haven't logged in in any restaurant!");
            case NO_SUCH_FOOD_TYPE_IN_RESTAURANT : System.out.println("There is no such food type in this restaurant!");
            case NO_SUCH_FOOD_TYPE_IN_GENERAL -> System.out.println("There is no food type with this name!");
            case EQUAL_FOOD_TYPES -> System.out.println("These food types are the same!");
            case THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE -> System.out.println("There is still orders with this food type!");
            case FOOD_TYPE_ALREADY_EXIST -> System.out.println("This food type already exist in this restaurant!");
            case FOOD_TYPE_ADDED -> System.out.println("Food type added successfully");
            case FOOD_TYPE_EDITED -> System.out.println("Food type edited successfully");
            case SURE_EDIT_FOOD_TYPE -> System.out.println("Are you sure you want to change your restaurant food type?");
            case EDIT_FOOD_TYPE_CANCELED -> System.out.println("Edit food type canceled");
            case FOOD_ALREADY_EXIST -> System.out.println("This food already exist in this restaurant");
            case FOOD_ADDED -> System.out.println("Food added successfully");
            case LOGOUT -> System.out.println("Logged out successfully");
            case ADD_RESTORE_QUESTION -> System.out.println("Please set your restore question");
            case ADD_RESTORE_ANSWER -> System.out.println("Please set the answer");
            case RESTORE_QUESTION_EXISTS -> System.out.println("Restore question already exists");
            case RESTORE_QUESTION_ADDED -> System.out.println("Restore question added");
        }
    }

    //passing to manager
    private boolean processSelectingRestaurant(int ID) {
        Output temp = manager.selectRestaurant(ID);
        outputPrinter(temp);
        return temp == Output.SUCCESSFUL_SELECT_RESTAURANT;
    }
    private void processAddingRestaurant(String name, String foodType) {
        outputPrinter(manager.addRestaurant(name, foodType));
    }
    private void processAd dingRestoreQuestion() {
        Output temp = manager.checkRestoreQuestion();
        outputPrinter(temp);
        if (temp == Output.ADD_RESTORE_QUESTION) {
            String question = scanner.nextLine();
            outputPrinter(Output.ADD_RESTORE_ANSWER);
            String answer = scanner.nextLine();
            manager.setRestore(question,answer);
            outputPrinter(Output.RESTORE_QUESTION_ADDED);
        }
    }
    private void processLoggingOut() {
        outputPrinter(manager.logoutFromRestaurantOwnerMenu());
    }

}
