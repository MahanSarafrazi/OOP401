package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import phase2.model.Comment;

public class CommentsBoxController extends MenuController {

    @FXML
    public Label customerName;

    @FXML
    public TextField commentText;

    @FXML
    public TextField response;

    @FXML
    public Button tick;

    private Comment comment;

    public void chooseComment(Comment comment) {
        this.comment = comment;
        this.customerName.setText(comment.getUser().getUserName());
        this.commentText.setText(comment.getComment());
        if(comment.hasResponse) {
            response.setText(comment.getResponse());
        }
    }

    @FXML
    public void tickHandler(ActionEvent actionEvent) {
        if(!comment.hasResponse) {
            comment.addResponse(getManager().getLoggedInUser(), response.getText());
        } else {
            comment.editComment(response.getText());
        }
        response.setText(comment.getResponse());
    }


    public Comment getComment() {
        return comment;
    }
}
