package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

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

    @FXML
    public Text error;

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
        score.setText(Double.toString(getManager().averageRating()));
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
            ((FoodBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
            ((FoodBoxController) loader.getController()).chooseFood(food.getName(), food.getType(), food.getPrice(), food.getID());
            list.getChildren().add(loader.getRoot());
        }
    }

    public void backHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().setActiveRestaurant(null);
        back();
    }

    @FXML
    public void logoutHandler(ActionEvent actionEvent) {
        getStage().close();
        getManager().logout();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RegisterAndLoginMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RegisterAndLoginMenuController) loader.getController()).initialize(getStage(), null, scene, null);
        super.getStage().setScene(scene);
        super.getStage().show();
    }


    public void uploadPhotoHandler(ActionEvent actionEvent) {

    }

    public void confirmHandler(ActionEvent actionEvent) {
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();

        restaurant.setName(name.getText());
        FoodType replacingType = null;
        for (FoodType value : FoodType.values()) {
            if (value.commandingPattern.matcher(foodType.getText()).find()) {
                replacingType = value;
                break;
            }
        }
        if(replacingType != null) {
            restaurant.editFoodType(restaurant.getFoodType().get(0), replacingType);
            error.setFill(Paint.valueOf("green"));
            error.setText("successful");
        } else {
            error.setFill(Paint.valueOf("red"));
            error.setText("invalid food type");
        }

        foodType.setText(restaurant.getFoodType().get(0).name());

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }

    public void resetHandler(ActionEvent actionEvent) {
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        name.setText(restaurant.getName());
        foodType.setText(restaurant.getFoodType().get(0).name());
    }
}
