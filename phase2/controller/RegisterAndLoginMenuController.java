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
    public Button theme;

    @FXML
    public void loginHandler(ActionEvent actionEvent) {
        FXMLLoader loginLoader = new FXMLLoader(this.getClass().getResource("../view/LoginMenu.fxml"));
        try {
            loginLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene loginScene = new Scene(loginLoader.getRoot());
        ((LoginMenuController) loginLoader.getController()).initialize(getStage(), null, loginScene, getMainScene());
        super.getStage().setScene(loginScene);
    }

    @FXML
    public void exitHandler(ActionEvent actionEvent) {
        getStage().close();
    }

    @FXML
    public void registerHandler(ActionEvent actionEvent) {
        FXMLLoader registerLoader = new FXMLLoader(this.getClass().getResource("../view/RegisterMenu.fxml"));
        try {
            registerLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene registerScene = new Scene(registerLoader.getRoot());
        ((RegisterMenuController) registerLoader.getController()).initialize(getStage(), null, registerScene, getMainScene());
        super.getStage().setScene(registerScene);
    }

    @FXML
    public void themeHandler(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Themes.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setScene(scene);
        ((ThemesController) loader.getController()).initialize(stage, null, scene, null);
        ((ThemesController) loader.getController()).getStage().show();
    }
}
