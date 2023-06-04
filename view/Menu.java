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
            case SUCCESSFUL_REGISTER -> System.out.println("registered successfully");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case ADD_RESTORE_QUESTION -> System.out.println("Please set your restore question");
            case ADD_RESTORE_ANSWER -> System.out.println("Please set the answer");
            case RESTORE_QUESTION_EXISTS -> System.out.println("Restore question already exists");
            case RESTORE_QUESTION_ADDED -> System.out.println("Restore question added");
            case RATING_OUT_OUT_OF_RANGE -> System.out.println("rating should be between 0 and 5");
            case RATING_EXISTS -> System.out.println("rating already exists");
            case RATED -> System.out.println("rated successfully");
            case NO_RATING -> System.out.println("there is no rating");
        }
    }
    protected boolean processSelectingRestaurant(int ID) {
        boolean selected = manager.getLoggedInUser().setActiveRestaurant(ID);
        if (!selected)
            System.out.println("There is no restaurant with this ID!");
        return selected;
    }
    protected void processAddingRestoreQuestion() {
        Output temp = manager.checkRestoreQuestion();
        outputPrinter(temp);
        if (temp == Output.ADD_RESTORE_QUESTION) {
            String question = scanner.nextLine();
            outputPrinter(Output.ADD_RESTORE_ANSWER);
            String answer = scanner.nextLine();
            manager.setRestore(question,answer);
            outputPrinter(Output.RESTORE_QUESTION_ADDED);
        }
    }
    protected void processLogout() {
        manager.logout();
        System.out.println("logged out");
    }
    protected boolean processSelectingFood(int ID) {
        boolean selected = manager.getLoggedInUser().getActiveRestaurant().setOpenedFood(ID);
        if (!selected)
            System.out.println("There is no food with this ID or it is deActive!");
        return selected;
    }
    protected void processAddComment() {
        System.out.println("please write your comment : ");
        String comment = scanner.nextLine();
        manager.addComment(comment);
        System.out.println("commented!");
    }

    protected void processEditComment(int ID) {
        if (!manager.checkCommentID(ID))
            System.out.println("wrong id!");
        else {
            System.out.println("please write new comment :");
            String comment = scanner.nextLine();
            manager.editComment(ID,comment);
            System.out.println("edited!");
        }
    }
    protected void processDisplayRatings() {
        if (manager.isThereRating())
            System.out.println("there is no rating");
        else
            System.out.println("average rating is : " + manager.averageRating());
    }
    protected void processAddRating(double ID) {
        outputPrinter(manager.addRating(ID));
    }
    protected void processEditRating(double ID) {
        outputPrinter(manager.editRating(ID));
    }
}
