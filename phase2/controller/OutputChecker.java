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
            case FOOD_TYPE_ADDED -> string = "Food type added successfully";
            case FOOD_TYPE_EDITED -> string = "Food type edited successfully";
            case SURE_EDIT_FOOD_TYPE -> string = "Are you sure you want to change your restaurant food type?";
            case EDIT_FOOD_TYPE_CANCELED -> string = "Edit food type canceled";
            case INVALID_COMMAND -> string = "Invalid command";
            case FOOD_ALREADY_EXIST -> string = "This food already exist in this restaurant";
            case FOOD_ADDED -> string = "Food Added successfully";
            case NO_FOOD_WITH_THIS_ID -> string = "There is no food with this ID";
            case FOOD_NAME_EDITED -> string = "Food name edited successfully";
            case FOOD_PRICE_EDITED -> string = "Food price edited successfully";
            case FOOD_DELETED -> string = "Food deleted successfully";
            case FOOD_DEACTIVATED -> string = "Food deactivated successfully";
            case FOOD_ACTIVATED -> string = "Food activated successfully";
            case THERE_ARE_FOODS_IN_ORDER -> string = "There are still foods in orders with this ID";
            case NO_COMMENT_WITH_ID -> string = "There is no comment with this ID!";
            case RESPONSE_ADDED -> string = "Response added successfully";
            case NO_RESPONSE -> string = "There in no response for this comment!";
            case RESPONSE_EDITED -> string = "Response edited successfully";
            case COMMENT_EDITED -> string = "Comment edited successfully";
            case NOT_YOUR_COMMENT -> string = "this comment is not your comment!";
            case LOCATION_NOT_IN_THE_MAP -> string = "This location is not in the map!";
            case RATING_OUT_OUT_OF_RANGE -> string = "rating should be between 0 and 5";
            case RATING_EXISTS -> string = "rating already exists";
            case RATED -> string = "rated successfully";
            case NO_RATING -> string = "there is no rating";
            case EQUAL_LOCATION -> string = "Restaurant is there right now!";
            case LOCATION_SET -> string = "Location changed";
            case RESPONSE_EXISTS -> string = "Response already exists";
            case EMPTY_CART -> string = "Your cart is empty";
            case NO_LOCATION -> string = "lol";
            case NOT_ENOUGH_CHARGE -> string = "You dont have enough charge to buy your cart";
            case ORDER_CONFIRMED -> string = "Order confirmed";
            case ORDER_CONFIRMED2 -> string = "Order confirmed and you got a new discount token !";
            case WRONG_DISCOUNT_TOKEN -> string = "Discount token is not valid !";
            case FOOD_ALREADY_DISCOUNTED -> string = "Food already has discount!";
            case WRONG_PERCENT_AMOUNT -> string = "Discount percent should be between 0 and 50";
            case FOOD_DISCOUNTED -> string = "Come on , it's not possible . Enter positive time";
            case INVALID_TIME -> string = "Food discounted successfully";
        }
        return string;
    }
    public static boolean hasStringOutput(Output output) {
        boolean bool = true;
        switch (output) {
            case SUCCESSFUL_LOGIN, LOGOUT, ADD_RESTORE_QUESTION, ADD_RESTORE_ANSWER, SHOW_RESTORE_QUESTION -> bool = false;
        }
        return bool;
    }
    public static Output outputEnum(Output output) {
        return Output.ORDER_CONFIRMED;
    }
}
