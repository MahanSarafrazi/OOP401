package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import phase2.model.Comment;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantOwnerMenuController extends MenuController {

    @FXML
    public Button uploadPhoto;

    @FXML
    public Button seeComments;

    @FXML
    public Label restaurantNAme;

    @FXML
    public Label restaurantid;

    @FXML
    public Label score;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button logout;

    @FXML
    public Button addFood;

    @FXML
    public void seeCommentsHandler(ActionEvent actionEvent) {
        FXMLLoader commentsLoader = new FXMLLoader(this.getClass().getResource("../view/Comments.fxml"));
        try {
            commentsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene commentsScene = new Scene(commentsLoader.getRoot());
        ArrayList<Comment> comments = getManager().getLoggedInUser().getActiveRestaurant().getComments();
        for(int i = 0; i < getManager().getLoggedInUser().getActiveRestaurant().getComments().size(); ++i) {
            HBox hBox = new HBox();
            Text text = new Text(comments.get(i).getComment());
            Button button = new Button("add response");
            button.setId("button" + i);
            hBox.getChildren().add(text);
            hBox.getChildren().add(button);
            ((VBox) commentsLoader.getRoot()).getChildren().add(hBox);
        }
        ((CommentsController) commentsLoader.getController()).initialize(new Stage(), commentsScene, null);
        ((CommentsController) commentsLoader.getController()).getStage().setScene(commentsScene);
        ((CommentsController) commentsLoader.getController()).getStage().show();
    }
}
