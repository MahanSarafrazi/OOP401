package view;

import java.util.regex.Pattern;

public enum Inputs {
    ADD_CUSTOMER("^\\s*add\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_ADMIN("^\\s*add\\s+admin\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ;

    final Pattern commandingPattern;

    Inputs(String s) {
        this.commandingPattern = Pattern.compile(s);
    }
}
