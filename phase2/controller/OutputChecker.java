package phase2.controller;

import phase2.view.Output;

public class OutputChecker {
    public static String outputString(Output output) {
        String string = null;
        switch (output) {
            case INVALID_USER_NAME -> string = "Invalid username";
            case INVALID_PASSWORD -> string = "Invalid password";
            case SUCCESSFUL_REGISTER -> string = "Registered successfully";
            case RESTORE_QUESTION_EXISTS -> string = "Restore question already exists";
            case RESTORE_QUESTION_ADDED -> string = "Restore question added";
            case NO_RESTORE_QUESTION -> string = "There is no restore question";
            case NO_SUCH_FOOD_TYPE_IN_RESTAURANT -> string = "There is no such food type in this restaurant!";
            case NO_SUCH_FOOD_TYPE_IN_GENERAL -> string = "There is no food type with this name!";
            case EQUAL_FOOD_TYPES -> string = "These food types are the same!";
            case THERE_IS_ORDERS_WITH_THIS_FOOD_TYPE -> string = "There is still orders with this food type!";
            case FOOD_TYPE_ALREADY_EXIST -> string = "This food type already exist in this restaurant!";
            case FOOD_TYPE_ADDED -> string = "";
            case SURE_EDIT_FOOD_TYPE -> string = "";
            case EDIT_FOOD_TYPE_CANCELED -> string = "";
            case INVALID_COMMAND -> string = "";
            case FOOD_ALREADY_EXIST -> string = "";
            case FOOD_ADDED -> string = "";
            case NO_FOOD_WITH_THIS_ID -> string = "";
            case FOOD_NAME_EDITED -> string = "";
            case FOOD_PRICE_EDITED -> string = "";
            case FOOD_DELETED -> string = "";
            case FOOD_DEACTIVATED -> string = "";
            case FOOD_ACTIVATED -> string = "";
            case THERE_ARE_FOODS_IN_ORDER -> string = "";
            case NO_COMMENT_WITH_ID -> string = "";
            case RESPONSE_ADDED -> string = "";
            case NO_RESPONSE -> string = "";
            case RESPONSE_EDITED -> string = "";
            case COMMENT_EDITED -> string = "";
            case NOT_YOUR_COMMENT -> string = "";
            case LOCATION_NOT_IN_THE_MAP -> string = "";
            case RATING_OUT_OUT_OF_RANGE -> string = "";
            case RATING_EXISTS -> string = "";
            case RATED -> string = "";
            case NO_RATING -> string = "";
            case EQUAL_LOCATION -> string = "";
            case LOCATION_SET -> string = "";
            case RESPONSE_EXISTS -> string = "";
            case EMPTY_CART -> string = "";
            case NO_LOCATION -> string = "";
            case NOT_ENOUGH_CHARGE -> string = "";
            case ORDER_CONFIRMED -> string = "";
            case ORDER_CONFIRMED2 -> string = "";
            case WRONG_DISCOUNT_TOKEN -> string = "";
            case FOOD_ALREADY_DISCOUNTED -> string = "";
            case WRONG_PERCENT_AMOUNT -> string = "";
            case FOOD_DISCOUNTED -> string = "";
            case INVALID_TIME -> string = "";
        }
        return string;
    }
    public static boolean hasStringOutput(Output output) {
        boolean bool = true;
        switch (output) {
            case SUCCESSFUL_LOGIN, LOGOUT, ADD_RESTORE_QUESTION, ADD_RESTORE_ANSWER, SHOW_RESTORE_QUESTION,
                    FOOD_TYPE_EDITED-> bool = false;
        }
        return bool;
    }
    public static Output outputEnum(Output output) {
        return Output.ORDER_CONFIRMED;
    }
}
