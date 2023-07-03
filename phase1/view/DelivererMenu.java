package phase1.view;

import phase1.controller.Manager;
import phase1.model.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DelivererMenu extends Menu {

    private DelivererMenu() {
        super();
    }
    private static DelivererMenu delivererMenuInstance;
    public static DelivererMenu getDelivererMenuInstance() {
        if(delivererMenuInstance == null) {
            delivererMenuInstance = new DelivererMenu();
        }
        return delivererMenuInstance;
    }

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
            if(matchers[6].find()) {
                processAddingRestoreQuestion();
            } else if (matchers[44].find()) {
                processShowOpenOrders();
            } else if (matchers[50].find()) {
                processSelectOrder(Integer.parseInt(matchers[50].group(1)),Integer.parseInt(matchers[50].group(2)));
            } else if (matchers[51].find()) {
                processShowPathToCustomer();
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
    private void processShowOpenOrders() {
        ArrayList<Order> orders = manager.getOrdersWithoutDeliverer();
        for (Order order : orders)
            System.out.println("order " + order.getID() + " with delivery price : " + order.totalDeliveryPrice() + " with restaurant" +
                    " and customer locations : " + order.getRestaurantLocation() + " " + order.getCustomerLocation());
        if (orders.isEmpty())
            System.out.println("there is no ready order without deliverer at the moment");
    }
    private void processSelectOrder(int ID,int location) {
        Deliverer deliverer = (Deliverer) manager.getLoggedInUser();
        if (deliverer.hasOrder())
            System.out.println("you already have an order");
        else {
            ArrayList<Order> orders = manager.getOrdersWithoutDeliverer();
            if (location < 0 || location > 1000)
                System.out.println("location not in the map");
            else {
                boolean hasID = false;
                for (Order order : orders)
                    if (order.getID() == ID) {
                        deliverer.order=order;
                        order.setDeliverer(location);
                        Map map = Manager.getManagerInstance().getMap();
                        map.findShortestPath(location,order.getRestaurantLocation(),true);
                        hasID = true;
                        break;
                    }
                if (!hasID)
                    System.out.println("There is no order with this ID");
            }
        }
    }
    private void processShowPathToCustomer() {
        Deliverer deliverer = (Deliverer) manager.getLoggedInUser();
        if (!deliverer.hasOrder())
            System.out.println("you don't have an order");
        else if (deliverer.order.reachedRestaurant()) {
            System.out.println("you didn't reach restaurant yet !");
        } else {
            Map map = Manager.getManagerInstance().getMap();
            map.findShortestPath(deliverer.order.getRestaurantLocation(),deliverer.order.getCustomerLocation(),true);
        }
    }
}
