package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import phase2.model.Comment;

public class CommentsBoxController extends MenuController {

    @FXML
    public AnchorPane pane;

    @FXML
    public Label user;

    @FXML
    public HBox hBox1;

    @FXML
    public TextField userText;

    @FXML
    public Button tick;

    private Comment comment;

    public void chooseComment(Comment comment) {
        this.comment = comment;
        this.user.setText(comment.getUser().getUserName());
        this.userText.setText(comment.getComment());
        userText.setEditable(comment.getUser().getUserName().equals(getManager().getLoggedInUser().getUserName()));
        if (getFatherStageController() instanceof RestaurantMenuByCustomerController ||
                getFatherStageController() instanceof FoodMenuUsedByCustomerController ||
        getFatherStageController() instanceof  CommentsController) {
            if (comment.hasResponse) {
                Label owner = new Label("owner");
                owner.setMaxWidth(82);
                owner.setMinWidth(82);
                owner.setMaxHeight(22);
                owner.setMinHeight(22);
                owner.setFont(Font.font(15));
                Text text = new Text("answered :");
                text.setFont(Font.font(15));
                text.setWrappingWidth(92.65796661376953);
                TextField ownerText = new TextField();
                ownerText.setMaxWidth(337);
                ownerText.setMinWidth(337);
                ownerText.setMaxHeight(51);
                ownerText.setMinHeight(51);
                ownerText.setEditable(false);
                ownerText.setText(comment.getResponse());
                Button responseTick = new Button();
                responseTick.setMinWidth(46);
                responseTick.setMaxWidth(46);
                responseTick.setMinHeight(49);
                responseTick.setMaxHeight(49);
                responseTick.setStyle("-fx-background-color: transparent");
                Image image = new Image("@../../resources/Pics/cart.jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(44);
                imageView.setFitWidth(45);
                responseTick.setGraphic(imageView);
                responseTick.setMinWidth(65);
                responseTick.setMinHeight(51);
                HBox hBox = new HBox(owner, text, ownerText,responseTick);
                hBox.setLayoutX(0);
                hBox.setLayoutY(65);
                pane.getChildren().add(hBox);
            }
        }
        else {
            Label owner = new Label("you");
            owner.setMaxWidth(82);
            owner.setMinWidth(82);
            owner.setMaxHeight(22);
            owner.setMinHeight(22);
            owner.setFont(Font.font(15));
            Text text = new Text("answered :");
            text.setFont(Font.font(15));
            text.setWrappingWidth(92.65796661376953);
            TextField ownerText = new TextField();
            ownerText.setMaxWidth(337);
            ownerText.setMinWidth(337);
            ownerText.setMaxHeight(51);
            ownerText.setMinHeight(51);
            ownerText.setEditable(true);
            if (comment.hasResponse)
                ownerText.setText(comment.getResponse());
            Button responseTick = new Button();
            responseTick.setMinWidth(46);
            responseTick.setMaxWidth(46);
            responseTick.setMinHeight(49);
            responseTick.setMaxHeight(49);
            responseTick.setStyle("-fx-background-color: transparent");
            Image image = new Image("@../../resources/Pics/cart.jpg");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(44);
            imageView.setFitWidth(45);
            responseTick.setGraphic(imageView);
            responseTick.setMinWidth(65);
            responseTick.setMinHeight(51);
            responseTick.setOnAction(actionEvent -> {
                if (comment.hasResponse && !ownerText.getText().isEmpty())
                    comment.editResponse(ownerText.getText());
                else if (comment.hasResponse &&ownerText.getText().isEmpty())
                    comment.removeResponse();
                else
                    comment.addResponse(getManager().getLoggedInUser(), ownerText.getText());
            });
            HBox hBox = new HBox(owner, text, ownerText,responseTick);
            hBox.setLayoutX(0);
            hBox.setLayoutY(65);
            pane.getChildren().add(hBox);
        }
    }

    @FXML
    public void tickHandler() {
        comment.editComment(userText.getText());
    }


    public Comment getComment() {
        return comment;
    }
}
