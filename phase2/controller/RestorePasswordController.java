package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.User;

public class RestorePasswordController extends MenuController {
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene,User user) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        this.user = user;
        this.restoreQuestion.setText(user.getRestoreQuestion()+" ?");
        this.restoreQuestion.setEditable(false);
    }
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
    public void backHandler() {
        back();
    }
    @FXML
    public void confirmHandler() {
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
    public void resetHandler() {
        restoreAnswer.setText("");
    }
}
