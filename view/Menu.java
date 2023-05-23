package view;

import java.util.Scanner;

public abstract class Menu {
    protected final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public abstract RunOrders openMenu();
}
