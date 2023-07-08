package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import phase2.model.FoodType;

public class FoodTypesController extends MenuController {
    @FXML
    public VBox vBox;

    public void initialize() {
        vBox.getChildren().add(new StackPane(new Text("Here are the types")));
        for (FoodType foodType : getManager().getLoggedInUser().getActiveRestaurant().getFoodType()) {
            Text text = new Text(foodType.name());
            StackPane stackPane = new StackPane(text);
            vBox.getChildren().add(stackPane);
        }
    }
}
