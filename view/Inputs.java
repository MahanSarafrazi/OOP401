package view;

import java.util.regex.Pattern;

public enum Inputs {
    ADD_CUSTOMER("^\\s*add\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_ADMIN("^\\s*add\\s+admin\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_DELIVERER("^\\s*add\\s+deliverer\\s+(\\S+)\\s+(\\S+)\\s*$"), //Added by F
    ADD_RESTAURANT_OWNER("^\\s*add\\s+restaurant owner\\s+(\\S+)\\s+(\\S+)\\s*$"), //By F
    LOGIN_CUSTOMER("^\\s*login\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"),
    LOGIN_ADMIN("^\\s*login\\s+admin\\s+(\\S+)\\s+(\\S+)\\s*$"), //By F
    LOGIN_DELIVERER("^\\s*login\\s+deliverer\\s+(\\S+)\\s+(\\S+)\\s*$"), //By F
    LOGIN_RESTAURANT_OWNER("^\\s*login\\s+restaurant owner\\s+(\\S+)\\s+(\\S+)\\s*$"), //By F
    SELECT_RESTAURANT("^\\s*select\\s+restaurant\\s+(\\d+)\\s*$"),
    ADD_RESTAURANT("^\\s*add\\s+restaurant\\s+(\\S+)\\s+(\\S+)\\s*$"),
    SHOW_FOOD_TYPE("^\\s*show\\s+food\\s+type\\s*$"),
    EDIT_FOOD_TYPE("^\\s*edit\\s+food\\s+type\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_FOOD_TYPE("^\\s*add\\s+food\\s+type(\\S+)\\s*$"),
    ADD_FOOD("^\\s*add\\s+food\\(\\S+)\\s+(\\d*\\.?\\d+)\\s+(\\S+)\\s*$"),
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
