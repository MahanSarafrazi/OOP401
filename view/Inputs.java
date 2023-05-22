package view;

import java.util.regex.Pattern;

public enum Inputs {
    ADD_CUSTOMER("^\\s*add\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"),
    ADD_ADMIN("^\\s*add\\s+admin\\s+(\\S+)\\s+(\\S+)\\s*$"),
    LOGIN_CUSTOMER("^\\s*login\\s+customer\\s+(\\S+)\\s+(\\S+)\\s*$"),
    EXIT_PROGRAM("^\\s*exit\\s+program\\s*$")
    ;

    final Pattern commandingPattern;

    Inputs(String s) {
        this.commandingPattern = Pattern.compile(s);
    }
}
