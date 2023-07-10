package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import phase2.model.Customer;
import phase2.model.Food;
import phase2.model.FoodType;
import phase2.model.RestaurantOwner;

import java.io.IOException;

public class FoodBoxController extends MenuController {

    @FXML
    public TextField foodName;

    @FXML
    public TextField foodType;

    @FXML
    public TextField foodPrice;

    @FXML
    public Button buttonBOX;

    @FXML
    public AnchorPane view;

    boolean isButton;

    boolean activation;

    private Food food;

    @FXML
    public void buttonBOXHandler(ActionEvent actionEvent) {
        if(isButton) {
            if (getFatherStageController() instanceof RestaurantsMenuController) {
                getManager().getLoggedInUser().setActiveRestaurant(food.getRestaurantID());
                getManager().getLoggedInUser().getActiveRestaurant().setOpenedFood(food.getID());
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/FoodMenuByCustomer.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(loader.getRoot());
                getStage().setScene(scene);
                ((FoodMenuUsedByCustomerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
                getStage().show();
            }
            else {
                getManager().getLoggedInUser().setActiveRestaurant(food.getRestaurantID());
                getManager().getLoggedInUser().getActiveRestaurant().setOpenedFood(food.getID());
                if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/FoodMenuByOwner.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(loader.getRoot());
                    getStage().setScene(scene);
                    ((FoodMenuByOwnerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
                    getStage().show();
                } else {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/FoodMenuByCustomer.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(loader.getRoot());
                    getStage().setScene(scene);
                    ((FoodMenuUsedByCustomerController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getMainScene());
                    getStage().show();
                }
            }
        }
    }

    @FXML
    public void chooseFood(Food food, boolean isButton) {
        this.food = food;
        this.foodName.setText(food.getName());
        this.foodName.setEditable(false);
        this.foodType.setText(food.getType().name());
        this.foodType.setEditable(false);
        this.foodPrice.setText(Double.toString(food.getPrice()));
        this.foodPrice.setEditable(false);
        this.isButton = isButton;
        this.activation = food.getActivation();
        if(!activation) {
            buttonBOX.setStyle("-fx-background-color: silver");
            view.setStyle("-fx-background-color: silver");
        }
    }
}
