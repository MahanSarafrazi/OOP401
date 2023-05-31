package model;

import java.util.regex.Pattern;

public enum FoodType {
    FAST_FOOD("^\\s*(?i)FAST\\s*FOOD\\s*$"),
    IRANIAN("^\\s*(?i)IRANIAN\\s*$"),
    ITALIAN("^\\s*(?i)ITALIAN\\s*$"),
    TURKISH("^\\s*(?i)TURKISH\\s*$"),
    ;
    public final Pattern commandingPattern;

    FoodType(String s) {
        this.commandingPattern = Pattern.compile(s);
    }
}
