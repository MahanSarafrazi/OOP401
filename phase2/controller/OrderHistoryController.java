package phase2.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class OrderHistoryController extends MenuController {

    @FXML
    public TextField totalPrice;

    @FXML
    public TextField orderID;

    @FXML
    public Button showCustomerLocation;
    @FXML
    public TabPane tabPane;

    @FXML
    public ArrayList<VBox> list;

    private Order order;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    public void chooseOrder(Order order) {
        this.order = order;
        orderID.setText(Integer.toString(order.getID()));
        totalPrice.setText(Double.toString(order.totalPrice()));
        list = new ArrayList<>();
        setFoods();
    }

    public void setFoods() {

        tabPane.getTabs().clear();
        list.clear();
        LinkedHashSet<FoodType> foodTypeLinkedHashSet = order.getType();
        ArrayList<FoodType> foodTypes = new ArrayList<>();
        for (FoodType foodType : foodTypeLinkedHashSet)
            foodTypes.add(foodType);
        for (int i = 0; i < foodTypes.size(); ++i) {
            Tab tab = new Tab();
            tab.setText(String.valueOf(foodTypes.get(i)));
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
                    if (food.getType().equals(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(i))) {
                        loader = new FXMLLoader(this.getClass().getResource("../view/BoxFoodbycustomer.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ((FoodBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                        ((FoodBoxController) loader.getController()).chooseFood(food.getName(), food.getType(), food.getPrice(), food.getID(), false);
                        list.get(i).getChildren().add(loader.getRoot());
                    }
                }
            }
        }
    }

    @FXML
    public void showCustomerLocationHandler(ActionEvent actionEvent) {
    }
}