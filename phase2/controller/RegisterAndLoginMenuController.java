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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Captcha.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene captcha = new Scene(loader.getRoot());
        ((CaptchaController) loader.getController()).initialize(getStage(), null, captcha, getMainScene(),1);
        super.getStage().setScene(captcha);
    }

    @FXML
    public void exitHandler(ActionEvent actionEvent) {
        getStage().close();
    }

    @FXML
    public void registerHandler(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Captcha.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene captcha = new Scene(loader.getRoot());
        ((CaptchaController) loader.getController()).initialize(getStage(), null, captcha, getMainScene(),2);
        super.getStage().setScene(captcha);
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
