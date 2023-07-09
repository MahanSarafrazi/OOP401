package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phase2.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

public class OpenOrdersController extends MenuController {
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene,ArrayList<Order> orders,int ID) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        this.orders=orders;
        this.ID2.setText(String.valueOf(getManager().getOrderByID(orders,ID).getRestaurantID()));
    }

    private ArrayList<Order> orders = new ArrayList<>();

    @FXML
    public TextField totalPrice;

    @FXML
    public TextField ID2;

    @FXML
    public Button showCustomerLocation;


    @FXML
    public TabPane tabPane;

    @FXML
    public ArrayList<VBox> list;
    @FXML
    public VBox vBox;
    @FXML
    public TextField restaurantName;
    private Order order;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    public void chooseOrder(Order order) {
        this.order = order;
        ID2.setText(Integer.toString(order.getID()));
        ID2.setEditable(false);
        totalPrice.setText(Double.toString(order.totalPrice()));
        totalPrice.setEditable(false);
        if (getManager().getLoggedInUser() instanceof RestaurantOwner)
            list = new ArrayList<>();
        else {
            restaurantName.setText(order.getRestaurantName());
            restaurantName.setEditable(false);
        }
        setFoods();
    }

    public void setFoods() {
        if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
            tabPane.getTabs().clear();
            list.clear();
            LinkedHashSet<FoodType> foodTypeLol = order.getType();
            ArrayList<FoodType> foodTypes = new ArrayList<>();
            for (FoodType foodType : foodTypeLol)
                foodTypes.add(foodType);
            for (FoodType foodType : foodTypes) {
                Tab tab = new Tab();
                tab.setText(String.valueOf(foodType));
                ScrollPane scrollPane = new ScrollPane();
                VBox vBox = new VBox();
                scrollPane.setContent(vBox);
                list.add(vBox);
                tab.setContent(scrollPane);
                tabPane.getTabs().add(tab);
            }

            FXMLLoader loader;
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).getChildren().clear();
                ArrayList<Food> foods = order.getFoods();
                int count = -1;
                for (Food food : foods) {
                    count++;
                    for (int j = 0; j < order.getFoodsCount().get(count); j++) {
                        if (food.getType().equals(foodTypes.get(i))) {
                            loader = new FXMLLoader(this.getClass().getResource("../view/BoxFoodbycustomer.fxml"));
                            try {
                                loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            ((FoodBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                            ((FoodBoxController) loader.getController()).chooseFood(food, false);
                            list.get(i).getChildren().add(loader.getRoot());
                        }
                    }
                }
            }
        } else {
            FXMLLoader loader;
            ArrayList<Food> foods = order.getFoods();
            int count = -1;
            for (Food food : foods) {
                count++;
                for (int i = 0; i < order.getFoodsCount().get(count); i++) {
                    loader = new FXMLLoader(this.getClass().getResource("../view/BoxFoodbycustomer.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ((FoodBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                    ((FoodBoxController) loader.getController()).chooseFood(food, false);
                    vBox.getChildren().add(loader.getRoot());
                }
            }
        }
    }

    @FXML
    public void showCustomerLocationHandler(ActionEvent actionEvent) {
    }
    @FXML
    public void backHandler() {
        back();
    }
}
