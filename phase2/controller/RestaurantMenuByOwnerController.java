package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import phase2.model.Comment;
import phase2.model.Food;
import phase2.model.Restaurant;
import phase2.model.RestaurantOwner;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantMenuByOwnerController extends MenuController {

    @FXML
    public Button uploadPhoto;

    @FXML
    public Button seeComments;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button logout;

    @FXML
    public Button addFood;

    @FXML
    public TextField name;

    @FXML
    public TextField restaurantID;

    @FXML
    public TextField foodType;

    @FXML
    public TextField score;

    @FXML
    public VBox list;

    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getID();

    @FXML
    public void seeCommentsHandler(ActionEvent actionEvent) {
        FXMLLoader commentsLoader = new FXMLLoader(this.getClass().getResource("../view/Comments.fxml"));
        try {
            commentsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene commentsScene = new Scene(commentsLoader.getRoot());
        ArrayList<Comment> comments = getManager().getLoggedInUser().getActiveRestaurant().getComments();
        for(int i = 0; i < getManager().getLoggedInUser().getActiveRestaurant().getComments().size(); ++i) {
            HBox hBox = new HBox();
            Text text = new Text(comments.get(i).getComment());
            Button button = new Button("add response");
            button.setId("button" + i);
            hBox.getChildren().add(text);
            hBox.getChildren().add(button);
            ((VBox) commentsLoader.getRoot()).getChildren().add(hBox);
        }
        ((CommentsController) commentsLoader.getController()).initialize(new Stage(), this, commentsScene, null);
        ((CommentsController) commentsLoader.getController()).getStage().setScene(commentsScene);
        ((CommentsController) commentsLoader.getController()).getStage().show();
    }

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        name.setText(getManager().getLoggedInUser().getActiveRestaurant().getName());
        restaurantID.setText(Integer.toString(ID));
        foodType.setText(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(0).name());
        update();
    }


    public void addFoodHandler(ActionEvent actionEvent) {
        FXMLLoader addLoader = new FXMLLoader(this.getClass().getResource("../view/addFood.fxml"));
        try {
            addLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(addLoader.getRoot());
        Stage stage = new Stage();
        stage.setTitle("add food");
        stage.setScene(scene);
        ((addFoodController) addLoader.getController()).initialize(stage, this, scene, null);
        ((addFoodController) addLoader.getController()).getStage().show();
    }

    public void update() {
        FXMLLoader loader;
        list.getChildren().clear();
        ArrayList<Food> foods = ((RestaurantOwner) getManager().getLoggedInUser()).getActiveRestaurant().getFoods();
        for (Food food : foods) {
            loader = new FXMLLoader(this.getClass().getResource("../view/BoxFoodbycustomer.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((FoodBoxController) loader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
            ((FoodBoxController) loader.getController()).chooseFood(food.getName(), food.getType(), food.getPrice(), food.getID());
            list.getChildren().add(loader.getRoot());
        }
    }

    public void backHandler(ActionEvent actionEvent) {
        back();
    }
}
