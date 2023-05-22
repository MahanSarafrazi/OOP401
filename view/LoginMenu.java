package view;

public class LoginMenu extends Menu {
    //making the program have just one LoginMenu object
    private LoginMenu() {}

    private static LoginMenu loginMenu;

    public static LoginMenu getLoginMenu() {
        if(loginMenu == null) {
            loginMenu = new LoginMenu();
        }
        return loginMenu;
    }

}
