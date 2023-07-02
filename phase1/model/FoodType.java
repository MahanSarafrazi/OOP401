package phase1.model;

import java.util.regex.Pattern;

public enum FoodType {
    FAST_FOOD("^\\s*(?i)fastFood\\s*$"),
    IRANIAN("^\\s*(?i)IRANIAN\\s*$"),
    ITALIAN("^\\s*(?i)ITALIAN\\s*$"),
    TURKISH("^\\s*(?i)TURKISH\\s*$"),
    INTERNATIONAL_FOOD("^\\s*(?i)internationalFood\\s*$"),
    DESSERT("^\\s*(?i)DESSERT\\s*$"),
    DRINK("^\\s*(?i)DRINK\\s*$"),
    ;
    public final Pattern commandingPattern;

    FoodType(String s) {
        this.commandingPattern = Pattern.compile(s);
    }
}

