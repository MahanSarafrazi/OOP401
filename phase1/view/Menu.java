package phase1.view;

import phase1.controller.Manager;
import phase1.model.Customer;
import phase1.model.Rate;

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
            case NO_SUCH_FOOD_TYPE_IN_RESTAURANT -> System.out.println("There is no such food type in this restaurant!");
            case NO_SUCH_FOOD_TYPE_IN_GENERAL -> System.out.println("There is no food type with this name!");
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
            case LOCATION_NOT_IN_THE_MAP -> System.out.println("This location is not in the map!");
            case RESPONSE_EXISTS -> System.out.println("Response already exists");
            case NO_COMMENT_WITH_ID -> System.out.println("There is no comment with this ID!");
            case RESPONSE_ADDED -> System.out.println("Response added successfully");
            case NO_RESPONSE -> System.out.println("There in no response for this comment!");
            case RESPONSE_EDITED -> System.out.println("Response edited successfully");
            case COMMENT_EDITED -> System.out.println("Comment edited successfully");
            case NOT_YOUR_COMMENT -> System.out.println("this comment is not your comment!");
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
            manager.setRestore(question, answer);
            outputPrinter(Output.RESTORE_QUESTION_ADDED);
        }
    }

    protected void processLogout() {
        manager.logout();
        System.out.println("logged out");
    }
    protected void processExit() {
        Customer customer = (Customer) manager.getLoggedInUser();
        customer.clearCart();
    }

    protected void processBack() {
        manager.back();
    }

    protected boolean processSelectingFood(int ID) {
        boolean selected = manager.getLoggedInUser().getActiveRestaurant().setOpenedFood(ID);
        if (!selected)
            System.out.println("There is no food with this ID or it is deActive!");
        return selected;
    }

    protected void processAddComment() {
        boolean canComment = true;
        if (manager.getLoggedInUser() instanceof Customer customer) {
            if (!customer.orderedFood(customer.getActiveRestaurant()))
                canComment =false;
            else if (customer.getActiveRestaurant().getOpenedFood() != null &&
                    !customer.orderedFood(customer.getActiveRestaurant().getOpenedFood()))
                canComment=false;
        }
        if (canComment) {
            System.out.println("please write your comment -> ");
            manager.addComment(scanner.nextLine());
            System.out.println("Comment added successfully!");
        }
        else
            System.out.println("you have to order from this food/restaurant first!");
    }

    protected void processEditComment(int ID) {
        System.out.println("please write new comment ->");
        outputPrinter(manager.editComment(ID, scanner.nextLine()));
    }

    protected void processDisplayRating() {
        if (manager.isThereRating())
            System.out.println("there is no rating");
        else
            System.out.println("average rating is -> " + manager.averageRating());
    }

    protected void processDisplayRatings() {
        if (manager.isThereRating()) {
            System.out.println("there is no rating");
        } else {
            for (Rate rate : manager.getRating()) {
                System.out.println(rate.getUser().getUserName() + " -> " + rate.getRating());
            }
        }
    }

    protected void processAddRating(double rating) {
        outputPrinter(manager.addRating(rating));
    }

    protected void processEditRating(double rating) {
        outputPrinter(manager.editRating(rating));
    }

    protected void processAddResponse(int ID) {
        System.out.println("please write your response ->");
        outputPrinter(manager.addResponse(ID, scanner.nextLine()));
    }

    protected void processEditResponse(int ID) {
        System.out.println("please write new response ->");
        outputPrinter(manager.editResponse(ID, scanner.nextLine()));
    }
}
