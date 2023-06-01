package view;

import java.util.regex.Pattern;

public enum Inputs {
    ADD_CUSTOMER("^\\s*add\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"), //0
    ADD_DELIVERER("^\\s*add\\s+deliverer\\s+(\\S+)\\s+(\\S+)\\s*$"), //1
    ADD_RESTAURANT_OWNER("^\\s*add\\s+restaurant owner\\s+(\\S+)\\s+(\\S+)\\s*$"), //2
    LOGIN_CUSTOMER("^\\s*login\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"), //3
    LOGIN_DELIVERER("^\\s*login\\s+deliverer\\s+(\\S+)\\s+(\\S+)\\s*$"), //4
    LOGIN_RESTAURANT_OWNER("^\\s*login\\s+restaurant owner\\s+(\\S+)\\s+(\\S+)\\s*$"), //5
    SELECT_RESTAURANT("^\\s*select\\s+restaurant\\s+(\\d+)\\s*$"), //6
    ADD_RESTAURANT("^\\s*add\\s+restaurant\\s+(\\S+)\\s+(\\S+)\\s*$"), //7
    SHOW_FOOD_TYPE("^\\s*show\\s+food\\s+type\\s*$"), //8
    EDIT_FOOD_TYPE("^\\s*edit\\s+food\\s+type\\s+(\\S+)\\s+(\\S+)\\s*$"), //9
    ADD_FOOD_TYPE("^\\s*add\\s+food\\s+type(\\S+)\\s*$"), //10
    ADD_FOOD("^\\s*add\\s+food\\(\\S+)\\s+(\\d*\\.?\\d+)\\s+(\\S+)\\s*$"), //11
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
