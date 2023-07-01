package view;

import model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.regex.Matcher;

public class CustomerMenu extends Menu {
    //making the program have just one LoginMenu object
    private CustomerMenu() {
        super();
    }
    private static CustomerMenu customerMenuInstance;
    public static CustomerMenu getCustomerMenuInstance() {
        if(customerMenuInstance == null) {
            customerMenuInstance = new CustomerMenu();
        }
        return customerMenuInstance;
    }


    //Menu managing

    @Override
    public RunOrders openMenu() {
        System.out.println("Welcome! Here is the list of restaurants");
        RestaurantList.restaurants.sort(Comparator.comparing(Restaurant::getName).thenComparing(Restaurant::getID));
        for (Restaurant restaurant : RestaurantList.restaurants) {
            System.out.println(restaurant.getName()+" "+restaurant.getID());
        }
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
            if(matchers[6].find()) {
                processAddingRestoreQuestion();
            } else if (matchers[20].find()) {
                processSearchingRestaurant(matchers[20].group(1));
            } else if (matchers[35].find()) {
                processShowOrderHistory();
            } else if (matchers[36].find()) {
                processSelectOrder(Integer.parseInt(matchers[36].group(1)));
            } else if (matchers[37].find()) {
                processDisplayCart();
            } else if (matchers[38].find()) {
                processConfirmOrder(Integer.parseInt(matchers[38].group(1)));
            } else if (matchers[39].find()) {
                processShowEstimatedTime();
            } else if (matchers[40].find()) {
                processChargeAccount(Double.parseDouble(matchers[40].group(1)));
            } else if (matchers[41].find()) {
                processDisplayAccountCharge();
            } else if(matchers[46].find()) {
                processSearchNearRestaurants(Integer.parseInt(matchers[46].group(1)));
            } else if(matchers[47].find()) {
                processSearchNearRestaurants(Integer.parseInt(matchers[47].group(2)), matchers[47].group(1));
            } else if (matchers[8].find()) {
                if(processSelectingRestaurant(Integer.parseInt(matchers[8].group(1)))) {
                    runOrders = RunOrders.RESTAURANT_MENU_USED_BY_CUSTOMER;
                    inThisMenu = false;
                }
            } else if (input.matches(Inputs.LOGOUT.commandingPattern.pattern())) {
                processLogout();
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
    protected void outputPrinter(Output output) {
        super.outputPrinter(output);
        switch (output) {
            case ORDER_CONFIRMED -> System.out.println("order confirmed");
            case NOT_ENOUGH_CHARGE -> System.out.println("you dont have enough charge to buy your cart");
            case EMPTY_CART -> System.out.println("your cart is empty");
        }
    }
    private void processSearchingRestaurant(String name) {
        for (Restaurant restaurant : RestaurantList.restaurants)
            if (restaurant.getName().equals(name)) {
                System.out.print("ID : "+restaurant.getID()+" with food types:");
                for (FoodType foodType : restaurant.getFoodType())
                    System.out.print(" "+foodType);
                System.out.println(" and location : "+restaurant.getLocation());
            }
        if (RestaurantList.restaurants.isEmpty())
            System.out.println("there is no restaurant in city , lol!");
    }
    private void processShowOrderHistory() {
        Customer customer = (Customer) manager.getLoggedInUser();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Order order : customer.getOrders()) {
            ids.add(order.getID());
        }
        if (ids.isEmpty())
            System.out.println("you didn't order yet");
        else 
            System.out.println(ids);
    }
    private void processSelectOrder(int ID) {
        Order order = manager.selectOrder(ID);
        if (order == null)
            System.out.println("There is no order with this ID!");
        else {
            System.out.println("Restaurant name : " + order.getRestaurantName());
            for (Map.Entry<Food,Integer> entry : order.getFoods().entrySet())
                System.out.println("Food name : "+entry.getKey().getName()+" food price : "+entry.getKey().getPrice()
                +" food Id : "+entry.getKey().getID()+" count : "+entry.getValue());
            System.out.println("total price : "+order.totalPrice());
        }
    }
    private void processDisplayCart() {
        Customer customer = (Customer) manager.getLoggedInUser();
        if (customer.getCart().isEmpty())
            System.out.println("cart is empty");
        else {
            double totalPrice = 0;
            for (Map.Entry<Food,Integer> entry : customer.getCart().entrySet()) {
                System.out.println("Food name : " + entry.getKey().getName() + " food price : " + entry.getKey().getDiscountedPrice()
                        + " food Id : " + entry.getKey().getID() + " count : " + entry.getValue());
                totalPrice+=entry.getValue()*entry.getKey().getDiscountedPrice();
            }
            System.out.println("total price : "+totalPrice);
        }
    }
    private void processConfirmOrder(int customerLocation) {
        outputPrinter(manager.confirmOrder(customerLocation));
    }
    private void processShowEstimatedTime() {
        ArrayList<String> estimations = manager.estimateOrderTime();
        if(estimations.size() == 0) {
            System.out.println("You have no order!");
        } else {
            System.out.println("Your orders will be delivered in these times:");
            for (String estimation : estimations) {
                System.out.println(estimation);
            }
        }
    }
    private void processChargeAccount(double charge) {
        if (charge<0)
            System.out.println("are you kidding me?");
        else {
            Customer customer = (Customer) manager.getLoggedInUser();
            customer.charge(charge);
            System.out.println("account charged");
        }
    }
    private void processDisplayAccountCharge() {
        Customer customer = (Customer)manager.getLoggedInUser();
        System.out.println(customer.getCharge());
    }
    private void processSearchNearRestaurants(int location) {
        ArrayList<Restaurant> restaurants = manager.searchForNearRestaurants(location);
        if(restaurants.size() == 0) {
            System.out.println("There is no restaurant near you");
        } else {
            for (Restaurant restaurant : restaurants) {
                System.out.print("ID : "+restaurant.getID()+" with food types:");
                for (FoodType foodType : restaurant.getFoodType())
                    System.out.print(" "+foodType);
                System.out.println(" and location : "+restaurant.getLocation());
            }
        }
    }
    private void processSearchNearRestaurants(int location, String foodType) {
        ArrayList<Restaurant> restaurants = manager.searchForNearRestaurants(location, foodType);
        if(restaurants == null) {
            System.out.println("This food type doesn't exist!");
        } else if(restaurants.size() == 0) {
            System.out.println("There is no restaurant near you");
        } else {
            for (Restaurant restaurant : restaurants) {
                System.out.print("ID : "+restaurant.getID()+" with food types:");
                for (FoodType foodType1 : restaurant.getFoodType())
                    System.out.print(" "+foodType1);
                System.out.println(" and location : "+restaurant.getLocation());
            }
        }
    }
}
