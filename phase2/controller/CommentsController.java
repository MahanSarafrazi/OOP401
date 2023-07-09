package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phase2.model.Comment;
import phase2.model.RestaurantOwner;

import java.awt.*;
import java.io.IOException;

public class CommentsController extends MenuController {

    @FXML
    public VBox vBox;

    @FXML
    public GridPane gridPane;

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
            if (getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood() == null) {
                for (Comment comment : getManager().getLoggedInUser().getActiveRestaurant().getComments()) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxcomment.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    vBox.getChildren().add(loader.getRoot());
                    ((CommentsBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
                    ((CommentsBoxController) loader.getController()).chooseComment(comment);
                }
            } else {
                for (Comment comment : getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments()) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxcomment.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    vBox.getChildren().add(loader.getRoot());
                    ((CommentsBoxController) loader.getController()).initialize(getStage(), fatherStageController, getMainScene(), null);
                    ((CommentsBoxController) loader.getController()).chooseComment(comment);
                }
            }
        } else {
            if (getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood() == null) {
                for (Comment comment : getManager().getLoggedInUser().getActiveRestaurant().getComments()) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxcomment.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    vBox.getChildren().add(loader.getRoot());
                    ((CommentsBoxController) loader.getController()).initialize(getStage(), fatherStageController, getMainScene(), null);
                    ((CommentsBoxController) loader.getController()).chooseComment(comment);
                }
            } else {
                for (Comment comment : getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments()) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxcomment.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    vBox.getChildren().add(loader.getRoot());
                    ((CommentsBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
                    ((CommentsBoxController) loader.getController()).chooseComment(comment);
                }
            }
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/wide.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            gridPane.add(loader.getRoot(),0,3);
            ((WideController) loader.getController()).initialize(getStage(),this,getMainScene(),null);
        }
    }

    @FXML
    public void backHandler() {
        vBox.getChildren().clear();
        back();
    }
}
