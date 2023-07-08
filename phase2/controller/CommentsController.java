package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import phase2.model.Comment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommentsController extends MenuController implements Initializable {

    @FXML
    public VBox commentsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood() == null) {
            for (Comment comment : getManager().getLoggedInUser().getActiveRestaurant().getComments()) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxcomment.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                commentsList.getChildren().add(loader.getRoot());
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
                commentsList.getChildren().add(loader.getRoot());
                ((CommentsBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
                ((CommentsBoxController) loader.getController()).chooseComment(comment);
            }
        }
    }
}
