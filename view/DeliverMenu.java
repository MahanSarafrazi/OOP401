package view;

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

        }
    }
}
