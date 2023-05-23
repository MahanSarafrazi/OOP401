package view;

public class Run {
    //making the program have just one Run object
    private Run() {}

    private static Run run;

    public static Run getRun() {
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
        openedMenu.openMenu();
    }
}
