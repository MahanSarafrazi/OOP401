package phase2.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import phase2.model.Comment;
import phase2.model.Food;
import phase2.model.FoodType;
import phase2.model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantMenuByCustomerController extends MenuController {
    @FXML
    public ImageView photo;

    @FXML
    public Rectangle photoPlace;

    @FXML
    public AnchorPane pane;

    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        this.name.setText(restaurant.getName());
        this.name.setEditable(false);
        this.type.setText(restaurant.getFoodType().toString());
        this.type.setEditable(false);
        this.ID.setText(String.valueOf(restaurant.getID()));
        this.ID.setEditable(false);
        this.score.setText(getManager().averageRating());
        this.score.setEditable(false);
        if(getManager().getLoggedInUser().getActiveRestaurant().getPhotoPath() != null) {
            Image image = new Image(getManager().getLoggedInUser().getActiveRestaurant().getPhotoPath(), photoPlace.getWidth(), photoPlace.getHeight(), false, false);
            photo.setImage(image);
        }
        for (FoodType foodType : restaurant.getFoodType()) {
            VBox vBox = new VBox();
            for (Food food : getManager().getActiveRestaurantActiveFoods()) {
                if (food.getType() == foodType) {
                    FXMLLoader foodLoader = new FXMLLoader(this.getClass().getResource("../view/boxFoodByCustomer.fxml"));
                    try {
                        foodLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ((FoodBoxController) foodLoader.getController()).initialize(getStage(), getFatherStageController(), getMainScene(), null);
                    ((FoodBoxController) foodLoader.getController()).chooseFood(food, true);
                    vBox.getChildren().add(foodLoader.getRoot());
                }
            }
            ScrollPane scrollPane = new ScrollPane(vBox);
            Tab tab = new Tab(foodType.toString(),scrollPane) ;
            tabPane.getTabs().add(tab);
            pane.setStyle("-fx-background-color: " + getTheme());
        }
    }

    @FXML
    public TextField name;

    @FXML
    public TextField type;

    @FXML
    public TextField ID;

    @FXML
    public TextField score;

    @FXML
    public ChoiceBox<String> scoreBox;

    @FXML
    public TabPane tabPane;

    @FXML
    public void backHandler() {
        getManager().getLoggedInUser().deActiveRestaurant();
        back();
    }

    @FXML
    public void confirmHandler() {
        if (scoreBox.getValue() == null) {
            score.setText("NO RATING!");
            score.setStyle("-fx-text-fill: red");
        } else if (!getManager().addRating(Double.parseDouble(scoreBox.getValue()))) {
            score.setText("No order!");
            score.setStyle("-fx-text-fill: red");
        } else {
            score.setText(getManager().averageRating());
            score.setStyle("-fx-text-fill: black");
        }
    }

    @FXML
    public void commentHandler() {
        FXMLLoader commentsLoader = new FXMLLoader(this.getClass().getResource("../view/Comments.fxml"));
        try {
            commentsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene commentsScene = new Scene(commentsLoader.getRoot());
        ((CommentsController) commentsLoader.getController()).initialize(getStage(), this, commentsScene, getMainScene());
        ((CommentsController) commentsLoader.getController()).getStage().setScene(commentsScene);
        ((CommentsController) commentsLoader.getController()).getStage().show();
    }
}
