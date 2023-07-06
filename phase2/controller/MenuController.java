package phase2.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    private final Manager manager = Manager.getManagerInstance();

    private Stage stage;
    private Scene mainScene;
    private Scene previousScene;
    private MenuController fatherStageController;

    public MenuController getFatherStageController() {
        return fatherStageController;
    }

    public void setFatherStageController(MenuController fatherStageController) {
        this.fatherStageController = fatherStageController;
    }

    public Manager getManager() {
        return manager;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        this.stage = stage;
        this.mainScene = mainScene;
        this.previousScene = previousScene;
        this.fatherStageController = fatherStageController;
    }

    public void back() {
        stage.setScene(previousScene);
    }
}
