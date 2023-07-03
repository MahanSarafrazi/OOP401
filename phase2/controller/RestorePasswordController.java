package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class RestorePasswordController extends MenuController {
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

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }
    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        String username = LoginMenuController.userNameText;
        restoreQuestion.setText(getManager().getUser(username).getRestoreQuestion());
        restoreQuestion.setEditable(false);
        String answer = restoreAnswer.getText();
        if (answer.equals(getManager().getUser(username).getRestoreAnswer())) {
            answer = getManager().getUser(username).getPassword();
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
        restoreQuestion.setText("");
    }
}
