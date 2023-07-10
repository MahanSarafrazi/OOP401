package phase2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import phase2.model.*;
import phase2.view.OrderStatus;
import phase2.view.ZoomableScrollPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;

public class OpenOrdersController extends MenuController {

    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene, ArrayList<Order> orders, int ID) {
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

    @FXML
    public CheckBox orderStatus;
    private Order order;

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        back();
    }

    public void chooseOrder(Order order) {
        this.order = order;
        ID2.setText(Integer.toString(order.getID()));
        ID2.setEditable(false);
        if (getManager().getLoggedInUser() instanceof RestaurantOwner)
            totalPrice.setText(Double.toString(order.totalPrice()));
        else
            totalPrice.setText(Double.toString(order.totalDeliveryPrice()));
        totalPrice.setEditable(false);
        if (getManager().getLoggedInUser() instanceof RestaurantOwner)
            list = new ArrayList<>();
        else {
            restaurantName.setText(order.getRestaurantName());
            restaurantName.setEditable(false);
        }
        if(order.getOrderStatus().equals(OrderStatus.ON_THE_WAY)) {
            orderStatus.setSelected(true);
            orderStatus.setDisable(true);
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

    @FXML
    public void showPath() {
        Stage primaryStage = new Stage();
        AnchorPane pane = new AnchorPane();
        int[][] coordinates = Manager.getManagerInstance().getMap().coordinates;
        Manager manager = Manager.getManagerInstance();
        int[][] adjacency = manager.getMap().getAdjacencyMatrix();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (adjacency[i][j] != 0) {
                    Line line = new Line();
                    line.setStartX(coordinates[i][0]);
                    line.setEndX(coordinates[j][0]);
                    line.setStartY(coordinates[i][1]);
                    line.setEndY(coordinates[j][1]);
                    pane.getChildren().add(line);
                }
            }
        }
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (adjacency[i][j] != 0) {
                    Circle circle = new Circle(coordinates[i][0], coordinates[i][1], 25, Paint.valueOf("red"));
                    pane.getChildren().add(circle);
                }
            }
        }
        manager.getMap().findShortestPath(order.getRestaurantLocation(), order.getCustomerLocation(), true);
        for (int i = 1; i < manager.getMap().path.size(); i++) {
            Line line = new Line();
            line.setStroke(Paint.valueOf("green"));
            line.setStrokeWidth(25);
            line.setStartX(coordinates[manager.getMap().path.get(i - 1)][0]);
            line.setEndX(coordinates[manager.getMap().path.get(i)][0]);
            line.setStartY(coordinates[manager.getMap().path.get(i - 1)][1]);
            line.setEndY(coordinates[manager.getMap().path.get(i)][1]);
            if (i == 1) {
                Circle circle = new Circle(line.getStartX(), line.getStartY(), 50, Paint.valueOf("orange"));
                pane.getChildren().add(circle);
            }
            String color = "yellow";
            int size = 25;
            if (i == manager.getMap().path.size() - 1) {
                color = "blue";
                size = 50;
            }
            Circle circle = new Circle(line.getEndX(), line.getEndY(), size, Paint.valueOf(color));
            pane.getChildren().add(line);
            pane.getChildren().add(circle);
        }
        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(pane);
        zoomableScrollPane.setMaxHeight(400);
        zoomableScrollPane.setMaxWidth(600);
        Scene scene = new Scene(zoomableScrollPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Map");
        primaryStage.show();
    }
    @FXML
    public TextField showTime;

    @FXML
    public void estimateTime() {
        showTime.setText(order.estimateTime());
        showTime.setEditable(false);
    }

    public void orderStatusHandler(ActionEvent actionEvent) {
        if(orderStatus.isSelected()) {
            order.setOrderStatus(OrderStatus.ON_THE_WAY);
            orderStatus.setDisable(true);
        }
    }
}
