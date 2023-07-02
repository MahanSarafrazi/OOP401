package phase2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import phase2.controller.RegisterAndLoginMenuController;

import java.awt.*;

public class Main extends Application {
    //this is for phase2
     public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/RegisterAndLoginMenu.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        ((RegisterAndLoginMenuController) loader.getController()).initialize(primaryStage, scene, null);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
