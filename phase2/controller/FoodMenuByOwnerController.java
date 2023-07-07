package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.Comment;
import phase2.model.Food;
import phase2.view.Output;

import java.io.IOException;
import java.util.ArrayList;

public class FoodMenuByOwnerController extends MenuController {

    @FXML
    public TextField name;

    @FXML
    public TextField price;

    @FXML
    public TextField discount;

    @FXML
    public CheckBox activationDiscount;

    @FXML
    public Button seeComments;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button logout;

    @FXML
    public Button deleteFood;

    @FXML
    public TextField score;

    @FXML
    public ChoiceBox addtype;

    @FXML
    public TextField type;

    @FXML
    public Text nameError;

    @FXML
    public Text priceError;

    @FXML
    public Text discountError;

    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getID();

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        Food openedFood = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        name.setText(openedFood.getName());
        type.setText(openedFood.getType().name());
        price.setText(Double.toString(openedFood.getPrice()));
        score.setText(Double.toString(getManager().averageRating()));
        discount.setText(Double.toString(getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getDiscount()));
    }


    public void backHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().getActiveRestaurant().closeFood();
        back();
    }


    public void confirmHandler(ActionEvent actionEvent) {
        Food food = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();

        Output editNameResult = getManager().editFoodName(food.getID(), name.getText());
        if(editNameResult.equals(Output.FOOD_NAME_EDITED)) {
            nameError.setFill(Paint.valueOf("green"));
        } else {
            nameError.setFill(Paint.valueOf("red"));
            name.setText(food.getName());
        }
        nameError.setText(OutputChecker.outputString(editNameResult));

        try {
            Output editPriceResult = getManager().editFoodPrice(food.getID(), Double.parseDouble(price.getText()));
            if(editPriceResult.equals(Output.FOOD_PRICE_EDITED)) {
                priceError.setFill(Paint.valueOf("green"));
            } else {
                priceError.setFill(Paint.valueOf("red"));
                price.setText(Double.toString(food.getPrice()));
            }
            priceError.setText(OutputChecker.outputString(editPriceResult));
        } catch (Exception e) {
            priceError.setFill(Paint.valueOf("red"));
            priceError.setText("invalid Price");
            price.setText(Double.toString(food.getPrice()));
        }


        /*Output editTypeResult = getManager().processEditFoodType(food.getType().name(), type.getText());
        if(editTypeResult.equals(Output.SURE_EDIT_FOOD_TYPE)) {
            Output output = getManager().editFoodType(food.getType().name(), type.getText(), "yes");
            typeError.setFill(Paint.valueOf("green"));
            typeError.setText(OutputChecker.outputString(output));
        } else {
            typeError.setFill(Paint.valueOf("red"));
            typeError.setText(OutputChecker.outputString(editTypeResult));
        }
        type.setText(food.getType().name());*/

        //getManager().discountFood()
        //Output editDiscountResult = getManager().discountFood(ID, );

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> {
            nameError.setText("");
            priceError.setText("");
            //typeError.setText("");
        });
        hitAnimation.playFromStart();
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
        for(int i = 0; i < getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getComments().size(); ++i) {
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

    public void resetHandler(ActionEvent actionEvent) {
        Food food = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        name.setText(food.getName());
        price.setText(Double.toString(food.getPrice()));
        discount.setText(Double.toString(food.getDiscount()));
    }


    public void deleteFoodHandler(ActionEvent actionEvent) {
        Food food = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        getManager().getLoggedInUser().getActiveRestaurant().closeFood();
        getManager().getLoggedInUser().getActiveRestaurant().deleteFood(food.getID());

        ((RestaurantMenuByOwnerController) getFatherStageController()).update();
        back();
    }
}
