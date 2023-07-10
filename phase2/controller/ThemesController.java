package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ThemesController extends MenuController {

    @FXML
    public VBox list;

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        list.getChildren().add(makeTheme("gray"));
        list.getChildren().add(makeTheme("linear-gradient(from 0.0% 100.0% to 53.0806% 100.0%, #1fafda 0.0%, #1fafda 0.6711%, #ffffff 100.0%)"));
    }

    private javafx.scene.control.Button makeTheme(String theme) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/BoxTheme.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((BoxThemeController) loader.getController()).initialize(getStage(), null, getMainScene(), null);
        ((BoxThemeController) loader.getController()).chooseColor(theme);
        return loader.getRoot();
    }
}
