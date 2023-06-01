package view;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DelivererMenu extends Menu {
    public DelivererMenu() {
        super();
    }
    private static ArrayList<DelivererMenu> delivererMenus;
    public static ArrayList<DelivererMenu> getDelivererMenus() {
        return delivererMenus;
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
        }
    }
}
