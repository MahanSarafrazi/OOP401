package view;

import controller.Manager;

import java.util.Scanner;

public abstract class Menu {
    protected final Scanner scanner;
    protected final Manager manager = new Manager();
    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public abstract RunOrders openMenu();
}
