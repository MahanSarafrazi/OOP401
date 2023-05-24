package view;

import java.util.regex.Matcher;

public class AdminMenu extends Menu {
    private AdminMenu() {
        super();
    }
    private static AdminMenu adminMenuInstance;
    public static AdminMenu getAdminMenuInstance() {
        if(adminMenuInstance == null) {
            adminMenuInstance = new AdminMenu();
        }
        return adminMenuInstance;
    }


    //in Menu

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
