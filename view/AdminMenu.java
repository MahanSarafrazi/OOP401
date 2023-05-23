package view;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class AdminMenu extends Menu {
    public AdminMenu() {
        super();
    }
    private static ArrayList<AdminMenu> adminMenus;
    public static ArrayList<AdminMenu> getAdminMenuInstance() {
        return adminMenus;
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
