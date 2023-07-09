package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase2.model.Customer;
import phase2.model.Food;
import phase2.model.Restaurant;
import phase2.model.RestaurantList;

import java.io.IOException;

public class FoodMenuUsedByCustomerController extends MenuController{
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        Food food = getManager().getLoggedInUser().getActiveRestaurant().getOpenedFood();
        name.setText(food.getName());
        name.setEditable(false);
        price.setText(String.valueOf(food.getDiscountedPrice()));
        price.setEditable(false);
        ID.setText(String.valueOf(food.getID()));
        ID.setEditable(false);
        score.setText(getManager().averageRating());
        score.setEditable(false);
        Customer customer = (Customer) getManager().getLoggedInUser();
        if (customer.getCart().getFoods().contains(food))
            count.setText("add "+customer.getCart().foodCount(food)+" foods to cart");
        restaurantName.setText(customer.getActiveRestaurant().getName());
        rID=customer.getActiveRestaurant().getID();
    }
    private int rID;
    @FXML
    public TextField restaurantName;

    @FXML
    public TextField name;

    @FXML
    public TextField price;

    @FXML
    public TextField ID;

    @FXML
    public TextField score;

    @FXML
    public ChoiceBox<String > scoreBox;

    @FXML
    public TextField count;

    @FXML
    public void backHandler() {
        if (getFatherStageController() instanceof CartController controller) {
            controller.update();
        }
        getManager().getLoggedInUser().getActiveRestaurant().closeFood();
        back();
    }

    @FXML
    public void resetHandler() {
        count.setText("add 0 food to cart");
        scoreBox.setValue(null);
    }

    @FXML
    public void minusHandler() {
        String[] words = count.getText().split(" ");
        int count = Integer.parseInt(words[1]);
        if (count>0)
            count--;
        this.count.setText("add "+count+" foods to cart");
    }

    @FXML
    public void plusHandler() {
        String[] words = count.getText().split(" ");
        int count = Integer.parseInt(words[1]);
        count++;
        this.count.setText("add "+count+" foods to cart");
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

    @FXML
    public void confirmHandler() {
        Customer customer = (Customer) getManager().getLoggedInUser();
        if (customer.getOrderedRestaurant() != null && customer.getOrderedRestaurant().getID() != rID)
            restaurantName.setStyle("-fx-text-fill: red");
        else {
            customer.setOrderedRestaurant(RestaurantList.getRestaurant(rID));
            if (scoreBox.getValue() != null) {
                if (!getManager().addRating(Double.parseDouble(scoreBox.getValue()))) {
                    score.setStyle("-fx-text-fill: red");
                    score.setText("No order!");
                } else
                    score.setText(getManager().averageRating());
            }
            String[] words = count.getText().split(" ");
            int count = Integer.parseInt(words[1]);
            if (count == 0)
                customer.getCart().remove(customer.getActiveRestaurant().getOpenedFood());
            else
                customer.getCart().put(customer.getOrderedRestaurant().getOpenedFood(), count);
        }
    }
}
