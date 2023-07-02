package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegisterAndLoginMenuController extends MenuController {
    @FXML
    public Button exit;
    @FXML
    public Button login;

    @FXML
    public Button register;

    @FXML
    public void loginHandler(ActionEvent actionEvent) {
        FXMLLoader loginLoader = new FXMLLoader(this.getClass().getResource("../view/LoginMenu.fxml"));
        try {
            loginLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene loginScene = new Scene(loginLoader.getRoot());
        ((LoginMenuController) loginLoader.getController()).initialize(getStage(), loginScene, getMainScene());
        super.getStage().setScene(loginScene);
    }

    @FXML
    public void exitHandler(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void registerHandler(ActionEvent actionEvent) {
        System.out.println("test");
    }
}
