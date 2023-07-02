package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.logging.Handler;
import javafx.scene.control.Button;

public class RegisterAndLoginMenuController {

    private Manager manager = Manager.getManagerInstance();


    @FXML
    public Button exit;
    @FXML
    public Button login;

    @FXML
    public Button register;

    @FXML
    public void loginHandler(ActionEvent actionEvent) {
    }

    @FXML
    public void exitHandler(ActionEvent actionEvent) {
    }

    @FXML
    public void registerHandler(ActionEvent actionEvent) {
        System.out.println("test");
    }
}
