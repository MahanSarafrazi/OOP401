package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import phase2.model.Comment;

import java.io.IOException;
import java.util.ArrayList;

public class WideController extends MenuController{
    @FXML
    public Button add;

    @FXML
    public TextField comment;

    @FXML
    public void addController(){
        if (getManager().addComment(comment.getText())) {
            CommentsController controller = (CommentsController) getFatherStageController();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxcomment.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayList<Comment> comments ;
            if (getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood() == null)
                comments = getManager().getLoggedInUser().getActiveRestaurant().getComments();
            else
                comments = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments();
            controller.vBox.getChildren().add(loader.getRoot());
            ((CommentsBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
            ((CommentsBoxController) loader.getController()).chooseComment(comments.get(comments.size() - 1));
            getStage().show();
        } else {
            comment.setText("No Order!");
            comment.setStyle("-fx-text-fill: red");
        }
    }
}
