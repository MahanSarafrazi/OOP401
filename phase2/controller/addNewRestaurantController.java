package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import phase2.view.Output;

public class addNewRestaurantController extends MenuController {

    @FXML
    public TextField restaurantName;

    @FXML
    public TextField mainFoodType;

    @FXML
    public TextField Location;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Text error;


    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        int location = 0;
        try {
            location = Integer.parseInt(Location.getText());
            Output output = getManager().addRestaurant(restaurantName.getText(), mainFoodType.getText(), location);
            if(output.equals(Output.SUCCESSFUL_REGISTER)) {
                error.setFill(Paint.valueOf("green"));
            } else {
                error.setFill(Paint.valueOf("red"));
            }
            error.setText(OutputChecker.outputString(output));

        } catch (Exception e) {
            error.setFill(Paint.valueOf("red"));
            error.setText("invalid location");
        }

        ((RestaurantOwnerMenuController) getFatherStageController()).update();

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }


    @FXML
    public void resetHandler(ActionEvent actionEvent) {
        restaurantName.setText("");
        mainFoodType.setText("");
        Location.setText("");
    }
}
