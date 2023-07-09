package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.Restaurant;
import phase2.view.Output;
import phase2.view.ZoomableScrollPane;

public class addNewRestaurantController extends MenuController {

    @FXML
    public TextField restaurantName;

    @FXML
    public TextField mainFoodType;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Text error;

    @FXML
    public Button map;
    public TextField locationBugger;


    @FXML
    public void confirmHandler(ActionEvent actionEvent) {
        int location = 0;
        try {
            location = Integer.parseInt(locationBugger.getText());
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
        locationBugger.setText("");
    }

    public void mapHandler(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        AnchorPane pane = new AnchorPane();
        int[][] coordinates = getManager().getMap().coordinates;
        Manager manager = Manager.getManagerInstance();
        int[][] adjacency = manager.getMap().getAdjacencyMatrix();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (adjacency[i][j] != 0) {
                    Line line = new Line();
                    line.setStartX(coordinates[i][0]);
                    line.setEndX(coordinates[j][0]);
                    line.setStartY(coordinates[i][1]);
                    line.setEndY(coordinates[j][1]);
                    pane.getChildren().add(line);
                }
            }
        }
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (adjacency[i][j] != 0) {
                    Circle circle = new Circle(coordinates[i][0], coordinates[i][1], 25, Paint.valueOf("red"));
                    int nodeLocation = i + 1;
                    circle.setOnMouseClicked(mouseEvent -> {
                        locationBugger.setText(Integer.toString(nodeLocation));
                        primaryStage.close();
                    });
                    pane.getChildren().add(circle);
                }
            }
        }
        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(pane);
        zoomableScrollPane.setMaxHeight(400);
        zoomableScrollPane.setMaxWidth(600);
        Scene scene = new Scene(zoomableScrollPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Map");
        primaryStage.show();
    }
}
