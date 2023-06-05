package view;

import java.util.regex.Pattern;

public enum Inputs {
    ADD_CUSTOMER("^\\s*add\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"), //0
    ADD_DELIVERER("^\\s*add\\s+deliverer\\s+(\\S+)\\s+(\\S+)\\s*$"), //1
    ADD_RESTAURANT_OWNER("^\\s*add\\s+restaurant owner\\s+(\\S+)\\s+(\\S+)\\s*$"), //2
    LOGIN_CUSTOMER("^\\s*login\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"), //3
    LOGIN_DELIVERER("^\\s*login\\s+deliverer\\s+(\\S+)\\s+(\\S+)\\s*$"), //4
    LOGIN_RESTAURANT_OWNER("^\\s*login\\s+restaurant owner\\s+(\\S+)\\s+(\\S+)\\s*$"), //5
    ADD_RESTORE_QUESTION("^\\s*add\\s+restore\\s+question\\s*$"), //6
    
    RESTORE_PASSWORD("^\\s*restore\\s+password\\s+(\\S+)\\s*$"), //7
    SELECT_RESTAURANT("^\\s*select\\s+restaurant\\s+(\\d+)\\s*$"), //8
    ADD_RESTAURANT("^\\s*add\\s+restaurant\\s+(\\S+)\\s+(\\S+)\\s+(\\d+)\\s*$"), //9
    SHOW_FOOD_TYPE("^\\s*show\\s+food\\s+type\\s*$"), //10
    EDIT_FOOD_TYPE("^\\s*edit\\s+food\\s+type\\s+(\\S+)\\s+(\\S+)\\s*$"), //11
    ADD_FOOD_TYPE("^\\s*add\\s+food\\s+type(\\S+)\\s*$"), //12
    OPEN_FOODS_MENU("^\\s*open\\s+foods\\s+menu\\s*$"), //13
    ADD_FOOD("^\\s*add\\s+food\\s+(\\S+)\\s+(\\d*\\.?\\d+)\\s+(\\S+)\\s*$"), //14
    EDIT_FOOD_NAME("^\\s*edit\\s+food\\s+(\\d+)\\s+name\\s+(\\S+)\\s*$"), //15
    EDIT_FOOD_PRICE("^\\s*edit\\s+food\\s+(\\d+)\\s+price\\s+(\\d*\\.?\\d+)\\s*$"), //16
    DELETE_FOOD("^\\s*delete\\s+food\\s+(\\d+)\\s*$"), //17
    DE_ACTIVE_FOOD("^\\s*(?i)deActive\\s+food\\s+(\\d+)\\s*$"), //18
    ACTIVE_FOOD("^\\s*active\\s+food\\s+(\\d+)\\s*$"), //19
    SEARCH_RESTAURANT("^\\s*search\\s+restaurant\\s+(\\S+)\\s*$"), //20
    SEARCH_FOOD("^\\s*search\\s+food\\s+(\\S+)\\s*$"), //21
    SELECT_FOOD("^\\s*select\\s+food\\s+(\\d+)\\s*$"), //22
    DISPLAY_COMMENTS("^\\s*display\\s+comments\\s*$"), //23
    ADD_COMMENT("^\\s*add\\s+comment\\s*$"), //24
    EDIT_COMMENT("^\\s*edit\\s+comment\\s+(\\d+)\\s*$"), //25
    DISPLAY_RATING("^\\s*display\\s+rating\\s*$"), //26
    SUBMIT_RATING("^\\s*submit\\s+rating\\s+(\\d+)\\s*$"), //27
    EDIT_RATING("^\\s*edit\\s+rating\\s+(\\d+)\\s*$"), //28
    SET_DISCOUNT("^\\s*discount\\s+food\\s+(\\d+)\\s+(\\d*\\.?\\d+)\\s+(\\d+)\\s+hours\\s*$"), //29
    DISPLAY_RATINGS("^\\s*display\\s+ratings\\s*$"), //30
    ADD_RESPONSE("^\\s*add\\s+new\\s+response\\s+(\\d+)\\s*$"), //31
    EDIT_RESPONSE("^\\s*edit\\s+response\\s+(\\d+)\\s*$"), //32
    SHOW_LOCATION("^\\s*show\\s+location\\s*$"), //33
    EDIT_LOCATION("^\\s*edit\\s+location\\s+(\\d+)\\s*$"), //34
    ORDER_HISTORY("^\\s*show\\s+order\\s+history\\s*$"), //35
    SELECT_ORDER("^\\s*select\\s+order\\s+(\\d+)\\s*$"), //36
    DISPLAY_CART("^\\s*show\\s+cart\\s*$"), //37
    CONFIRM_ORDER("^\\s*confirm\\s+order\\s+(\\d+)\\s*$"), //38
    SHOW_ESTIMATED_DELIVERY_TIME("^\\s*show\\s+estimated\\s+delivery\\s+time\\s*$"), //39
    CHARGE_ACCOUNT("^\\s*charge\\s+account\\s+(\\d+)\\s*$"), //40
    DISPLAY_ACCOUNT_CHARGE("^\\s*display\\s+account\\s+charge\\s*$"), //41
    ADD_FOOD_TO_CART("^\\s*add\\s+(\\d+)\\s+foods\\s+to\\s+cart\\s*$"), //42
    REMOVE_FOOD_FROM_CART("^\\s*remove\\s+food\\s+from\\s+cart\\s*$"), //43
    OPEN_ORDERS("^\\s*show\\s+open\\s+orders\\s*$"), //44
    BACK("^\\s*back\\s*$"),
    LOGOUT("^\\s*logout\\s*$"),
    EXIT_PROGRAM("^\\s*exit\\s+program\\s*$")
    ;

    final Pattern commandingPattern;

    Inputs(String s) {
        this.commandingPattern = Pattern.compile(s);
    }

    public static Pattern[] getPatterns() {

        Pattern[] patterns = new Pattern[values().length];

        for(int i = 0; i < values().length; ++i) {
            patterns[i] = values()[i].commandingPattern;
        }

        return patterns;
    }
}
