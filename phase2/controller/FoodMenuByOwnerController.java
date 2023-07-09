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
    public ChoiceBox discountTime;

    @FXML
    public TextField type;

    @FXML
    public Text nameError;

    @FXML
    public Text priceError;

    @FXML
    public Text discountError;

    @FXML
    public TextField timeLeft;

    @FXML
    public CheckBox activation;

    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getID();

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        Food openedFood = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        name.setText(openedFood.getName());
        type.setText(openedFood.getType().name());
        price.setText(Double.toString(openedFood.getPrice()));
        discount.setText(Double.toString(getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getDiscount()));
        score.setText(getManager().averageRating());
        activation.setSelected(!getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getActivation());
        startTimeLeft();
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
            Output editPriceResult = getManager().editFoodPrice(ID, Double.parseDouble(price.getText()));
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

        try {
            Output setDiscountError = getManager().discountFood(ID, Double.parseDouble(discount.getText()), Integer.parseInt((String) discountTime.getValue()));
            if(setDiscountError.equals(Output.FOOD_DISCOUNTED)) {
                discountError.setFill(Paint.valueOf("green"));
                discount.setText(Double.toString(food.getDiscount()));
                startTimeLeft();
            } else {
                discountError.setFill(Paint.valueOf("red"));
            }
            OutputChecker.outputString(setDiscountError);
        } catch (Exception e) {
            discountError.setFill(Paint.valueOf("red"));
            discountError.setText("invalid inputs");
        }

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> {
            nameError.setText("");
            priceError.setText("");
            discountError.setText("");
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

        ((RestaurantMenuByOwnerController) getFatherStageController()).updateRestaurantInformation();
        back();
    }

    public void startTimeLeft() {
        Food food = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        Thread thread = new Thread(() -> {
            while (food.isDiscounted()) {
                long time = food.getTimeLeft();
                long seconds = time / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                minutes %= 60;
                seconds %= 60;
                timeLeft.setText(hours + ":" + minutes + ":" + seconds);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(!getStage().isShowing()) {
                    break;
                }
            }
            if(getStage().isShowing()) {
                discount.setText(Double.toString(0));
                timeLeft.setText("");
            }
        });
        thread.start();
    }

    @FXML
    public void activationHandler(ActionEvent actionEvent) {
        int ID = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood().getID();
        if(activation.isSelected()) {
            getManager().deActiveFood(ID);
        } else {
            getManager().activeFood(ID);
        }
        ((RestaurantMenuByOwnerController) getFatherStageController()).updateRestaurantInformation();
    }
}
