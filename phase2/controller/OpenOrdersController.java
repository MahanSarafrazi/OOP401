package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phase2.model.*;

import java.io.IOException;
import java.util.ArrayList;

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

    private int ID;
    @FXML
    public VBox vBox;
    @FXML
    public TextField restaurantName;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    public void chooseOrder(int ID) {
        this.ID = ID;
        ID2.setText(Integer.toString(ID));
        ID2.setEditable(false);
        totalPrice.setText(Double.toString(getOrderByID().totalPrice()));
        totalPrice.setEditable(false);
        if (getManager().getLoggedInUser() instanceof  RestaurantOwner)
            list = new ArrayList<>();
        else {
            restaurantName.setText(getOrderByID().getRestaurantName());
            restaurantName.setEditable(false);
        }
        setFoods();
    }

    public Order getOrderByID() {
        if (getManager().getLoggedInUser() instanceof  RestaurantOwner) {
            Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
            for (Order order : restaurant.getOrders()) {
                if (order.getID() == ID) {
                    return order;
                }
            }
        }
        else {
            for (Order order : orders) {
                if (order.getID() == ID) {
                    return order;
                }
            }
        }
        return null;
    }

    public void setFoods() {
        if (getManager().getLoggedInUser() instanceof RestaurantOwner) {
            tabPane.getTabs().clear();
            list.clear();
            ArrayList<FoodType> foodTypes = getManager().getLoggedInUser().getActiveRestaurant().getFoodType();
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
                ArrayList<Food> foods = getOrderByID().getFoods();
                for (Food food : foods) {
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
        } else {
            FXMLLoader loader;
            ArrayList<Food> foods = getOrderByID().getFoods();
            int count = -1;
            for (Food food : foods) {
                count++;
                for (int i = 0; i < getOrderByID().getFoodsCount().get(count); i++) {
                    loader = new FXMLLoader(this.getClass().getResource("../view/BoxFoodbycustomer.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ((FoodBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                    ((FoodBoxController) loader.getController()).chooseFood(food.getName(), food.getType(), food.getPrice(), food.getID(), false);
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
