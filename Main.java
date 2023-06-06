
import controller.DataBase;
import controller.Manager;
import model.*;
import view.Run;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shahkar");
        Run runner = Run.getRun();
        runner.running();
    }
}