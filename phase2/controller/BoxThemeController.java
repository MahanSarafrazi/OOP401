package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BoxThemeController extends MenuController {


    @FXML
    public Button ButtonBOX;

    private String theme;

    @FXML
    public void ButtonBOXHandler(ActionEvent actionEvent) {
        setTheme(theme);
        getStage().close();
    }

    public void chooseColor(String colorName) {
        theme = colorName;
        ButtonBOX.setStyle("-fx-background-color: " + colorName);
    }
}
