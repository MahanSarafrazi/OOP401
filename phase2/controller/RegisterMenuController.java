package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import phase2.model.UserType;
import phase2.view.Output;

public class RegisterMenuController extends MenuController {

    @FXML
    public TextField userName;

    @FXML
    public TextField passWord;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button back;

    @FXML
    public ChoiceBox type;

    @FXML
    public Text error;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        String username = this.userName.getText();
        String password = this.passWord.getText();
        String answer = "";

        if(type.getValue() == null) {
            answer = "You didn't choose a type";
            error.setText(answer);
        } else {
            Output output = null;
            if(type.getValue().equals("customer")) {
                output = super.getManager().addUser(username, password, UserType.CUSTOMER);
                answer = OutputChecker.outputString(output);
            } else if(type.getValue().equals("restaurant owner")) {
                output = super.getManager().addUser(username, password, UserType.RESTAURANT_OWNER);
                answer = OutputChecker.outputString(output);
            } else if(type.getValue().equals("deliverer")) {
                output = super.getManager().addUser(username, password, UserType.DELIVERER);
                answer = OutputChecker.outputString(output);
            }

            if(output.equals(Output.SUCCESSFUL_REGISTER)) {
                error.setFill(Paint.valueOf("green"));
            } else {
                error.setFill(Paint.valueOf("red"));
            }
            error.setText(answer);
        }

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }
}
