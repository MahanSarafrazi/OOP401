package view;

import controller.Manager;

import java.util.Scanner;

public abstract class Menu {
    protected final Scanner scanner;
    protected final Manager manager = Manager.getManagerInstance();
    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public abstract RunOrders openMenu();



    //OutputPrinter
    protected void outputPrinter(Output output) {
        switch (output) {
            case INVALID_USER_NAME -> System.out.println("user name is invalid");
            case INVALID_PASSWORD -> System.out.println("password is invalid");
            case SUCCESSFUL_LOGIN -> System.out.println("logged in successfully");
            case SUCCESSFUL_REGISTER -> System.out.println("registered successfully");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case NO_RESTORE_QUESTION -> System.out.println("you didn't set any question!");
            case SHOW_RESTORE_QUESTION -> System.out.println(manager.getLoggedInUser().getRestoreQuestion());
            case CORRECT_ANSWER -> System.out.println(manager.getLoggedInUser().getPassword());
            case WRONG_ANSWER -> System.out.println("wrong answer!");
        }
    }

}
