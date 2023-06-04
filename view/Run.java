package view;

import controller.DataBase;

public class Run {
    //making the program have just one Run object
    private Run() {}

    private static Run run;

    public static Run getRun() {
        if(run == null) {
            run = new Run();
        }
        return run;
    }
    //setting openedMenu
    private Menu openedMenu;

    private void setOpenMenu(Menu menu) {
        this.openedMenu = menu;
    }
    //running the program from scratch
    public void running() {
        DataBase dataBase = new DataBase("resources/graph.txt", "resources/user list.txt");
        dataBase.load();

        setOpenMenu(LoginMenu.getLoginMenu());
        RunOrders runOrders;
        while(!(runOrders = openedMenu.openMenu()).equals(RunOrders.EXIT)) {
            setOpenMenu(getMenuByEnum(runOrders));
        }
        System.out.println("thanks for trusting us!");

        dataBase.save();
    }

    //a helping method for getting Menu by enum
    private Menu getMenuByEnum(RunOrders runOrders) {
        switch (runOrders) {
            case LOGIN_MENU -> {
                return LoginMenu.getLoginMenu();
            } case CUSTOMER_MENU -> {
                return CustomerMenu.getCustomerMenuInstance();
            } case DELIVERER_MENU -> {
                return DelivererMenu.getDelivererMenuInstance();
            } case RESTAURANT_OWNER_MENU -> {
                return RestaurantOwnerMenu.getRestaurantOwnerMenuInstance();
            } case RESTAURANT_MENU_USED_BY_OWNER -> {
                return RestaurantMenuUsedByOwner.getRestaurantMenuUsedByOwnerInstance();
            } case RESTAURANT_MENU_USED_BY_CUSTOMER -> {
                return RestaurantMenuUsedByCustomer.getRestaurantMenuUsedByCustomerInstance();
            } case FOOD_MENU_USED_BY_CUSTOMER -> {
                return FoodMenuUsedByCustomer.getFoodMenuUsedByCustomerInstance();
            } case FOODS_MENU_USED_BY_OWNER -> {
                return FoodsMenuUsedByOwner.getFoodsMenuUsedByOwnerInstance();
            } case FOOD_MENU_USED_BY_OWNER -> {
                return FoodMenuUsedByOwner.getFoodMenuUsedByOwnerInstance();
            }
        }
        //not used
        return null;
    }
}
