package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import phase2.model.User;

public class RestorePasswordController extends MenuController {
    public User user;

    @FXML
    public TextField restoreQuestion;

    @FXML
    public TextField restoreAnswer;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button back;

    @FXML
    public Text error;
    public Label kk;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }
    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        String answer = restoreAnswer.getText();
        if (answer.equals(user.getRestoreAnswer())) {
            answer = user.getPassword();
            error.setFill(Paint.valueOf("green"));
        } else {
            answer = "wrong answer!";
            error.setFill(Paint.valueOf("red"));
        }
        error.setText(answer);
        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();

    }

    @FXML
    public void resetHandler(ActionEvent actionEvent) {
        restoreAnswer.setText("");
    }
}
