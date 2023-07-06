package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CustomerMenuController extends MenuController {
    @FXML
    public ComboBox searchType;

    @FXML
    public ComboBox restaurantType;

    @FXML
    public TextField searchField;

    @FXML
    public void searchHandler() {
        System.out.println(restaurantType.getValue());
    }
}

