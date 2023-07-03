package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import phase2.model.UserType;
import phase2.view.Output;

public class LoginMenuController extends MenuController {

    @FXML
    public TextField userName;

    @FXML
    public TextField passWord;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button forgetPassword;

    @FXML
    public Button back;

    @FXML
    public Text error;

    @FXML
    public ChoiceBox type;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        String username = this.userName.getText();
        String password = this.passWord.getText();
        String answer = "";
        if (type.getValue() == null) {
            answer = "You didn't choose a type";
            error.setText(answer);
        } else {
            Output output;
            if (type.getValue().equals("customer")) {
                output = super.getManager().logInUser(username, password, UserType.CUSTOMER);
                answer = OutputChecker.outputString(output);
            } else if (type.getValue().equals("restaurant owner")) {
                output = super.getManager().logInUser(username, password, UserType.RESTAURANT_OWNER);
                answer = OutputChecker.outputString(output);
            } else if (type.getValue().equals("deliverer")) {
                output = super.getManager().logInUser(username, password, UserType.DELIVERER);
                answer = OutputChecker.outputString(output);
            }
            error.setText(answer);
        }
        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }

    @FXML
    public void resetHandler(ActionEvent actionEvent) {
        userName.setText("");
        passWord.setText("");
        type.setValue(null);
    }
}