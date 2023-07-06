package phase2;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import phase2.controller.DataBase;
import phase2.controller.RegisterAndLoginMenuController;
import phase2.model.Customer;
import phase2.model.UserList;

public class Main extends Application {
    //this is for phase2
     public static void main(String[] args) {
         DataBase dataBase = new DataBase("resources/graph.txt", "resources/user list.txt");
         dataBase.load();
         launch(args);
         dataBase.save();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/RegisterAndLoginMenu.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        ((RegisterAndLoginMenuController) loader.getController()).initialize(primaryStage, null, scene, null);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shahkar");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
