package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import phase2.model.FoodType;

import java.awt.*;
import java.io.IOException;

public class FoodTypesController extends MenuController {
    @FXML
    public VBox vBox;

    public void initialize() {
        vBox.getChildren().add(new StackPane(new Text("Here are the types")));
        for (FoodType foodType : getManager().getLoggedInUser().getActiveRestaurant().getFoodType()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/FoodTypeBox.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((FoodTypeBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
            ((FoodTypeBoxController) loader.getController()).chooseType(foodType);
            vBox.getChildren().add(loader.getRoot());
        }
    }
}
