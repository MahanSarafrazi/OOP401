package view;

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
        setOpenMenu(LoginMenu.getLoginMenu());
        RunOrders runOrders;
        while(!(runOrders = openedMenu.openMenu()).equals(RunOrders.EXIT)) {
            setOpenMenu(getMenuByEnum(runOrders));
        }
        System.out.println("thanks for trusting us!");
    }
    //a helping method for getting Menu by enum
    private Menu getMenuByEnum(RunOrders runOrders) {
        switch (runOrders) {
            case LOGIN_MENU -> {
                return LoginMenu.getLoginMenu();
            }
        }
        //not used
        return null;
    }
}
