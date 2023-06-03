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
    ADD_RESTAURANT("^\\s*add\\s+restaurant\\s+(\\S+)\\s+(\\S+)\\s*$"), //9
    SHOW_FOOD_TYPE("^\\s*show\\s+food\\s+type\\s*$"), //10
    EDIT_FOOD_TYPE("^\\s*edit\\s+food\\s+type\\s+(\\S+)\\s+(\\S+)\\s*$"), //11
    ADD_FOOD_TYPE("^\\s*add\\s+food\\s+type(\\S+)\\s*$"), //12
    ADD_FOOD("^\\s*add\\s+food\\s+(\\S+)\\s+(\\d*\\.?\\d+)\\s+(\\S+)\\s*$"), //13
    SHOW_FOODS("^\\s*show\\s+foods\\s*$"), //14
    EDIT_FOOD_NAME("^\\s*edit\\s+food\\s+(\\d+)\\s+name\\s+(\\S+)\\s*$"), //15
    EDIT_FOOD_PRICE("^\\s*edit\\s+food\\s+(\\d+)\\s+price\\s+(\\d*\\.?\\d+)\\s*$"), //16
    DELETE_FOOD("^\\s*delete\\s+food\\s+(\\d+)\\s*$"), //17
    DE_ACTIVE_FOOD("^\\s*deactive\\s+food\\s+(\\d+)\\s*$"), //18
    ACTIVE_FOOD("^\\s*active\\s+food\\s+(\\d+)\\s*$"), //19
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
