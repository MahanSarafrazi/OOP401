package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    public void backHandler(ActionEvent actionEvent) {
        back();
    }
}
