package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.Collections;

public class CustomerMenuController extends MenuController {
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        Customer customer = (Customer) getManager().getLoggedInUser();
        this.accountCharge.setText(String.valueOf(customer.getCharge()));
        this.accountCharge.setEditable(false);
        if (customer.getRestoreQuestion() != null) {
            this.restoreQuestion.setText(customer.getRestoreQuestion());
            this.restoreSolve.setText(customer.getRestoreAnswer());
        }
        this.username.setText(customer.getUserName());
        this.username.setEditable(false);
        this.password.setText(customer.getPassword());
        this.password.setEditable(false);
        this.totalSpending.setText(String.valueOf(customer.getSpentMoney()));
        this.totalSpending.setEditable(false);
        Collections.reverse(customer.getOrders());
        for (Order order : customer.getOrders()) {
            if (order.getOrderStatus() != OrderStatus.SENT) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxopenorders.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((OpenOrdersBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                ((OpenOrdersBoxController) loader.getController()).chooseOrder(order);
                openOrders.getChildren().add(loader.getRoot());
            }
            else {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxopenorders.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((OpenOrdersBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                ((OpenOrdersBoxController) loader.getController()).chooseOrder(order);
                sentOrders.getChildren().add(loader.getRoot());
            }
        }
        Collections.reverse(customer.getOrders());
        this.locationBugger.setText(Integer.toString(customer.location));
        locationBugger.setEditable(false);
    }
    @FXML
    public ComboBox<String> searchType;

    @FXML
    public ComboBox<String> restaurantType;

    @FXML
    public TextField searchField;

    @FXML
    public TextField totalSpending;

    @FXML
    public TextField username;

    @FXML
    public TextField password;

    @FXML
    public TextField restoreQuestion;

    @FXML
    public TextField restoreSolve;

    @FXML
    public TextField accountCharge;

    @FXML
    public ChoiceBox<String> chargeBox;

    @FXML
    public VBox openOrders;

    @FXML
    public VBox sentOrders;

    @FXML
    public Button map;

    @FXML
    private TextField locationBugger;

    @FXML
    public void searchHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants;
            if (searchType.getValue() == null)
                restaurants = getManager().normalSearch(searchField.getText(), null);
            else if (searchType.getValue().equals("near restaurants"))
                restaurants = getManager().searchForNearRestaurants(Integer.parseInt(locationBugger.getText()), restaurantType.getValue(), searchField.getText());
            else if (searchType.getValue().equals("favorite restaurants"))
                restaurants = getManager().favoriteRestaurants(restaurantType.getValue(), searchField.getText());
            else if (restaurantType.getValue() != null)
                restaurants = getManager().normalSearch(searchField.getText(), FoodType.valueOf(restaurantType.getValue()));
            else
                restaurants = getManager().normalSearch(searchField.getText(), null);
            openRestaurantsMenu(restaurants);
        }
    }


    @FXML
    public void resetHandler1() {
        searchType.setValue(null);
        restaurantType.setValue(null);
        searchField.setText(null);
    }

    @FXML
    public void logoutHandler() {
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
    public void cartHandler() {
        getStage().close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Cart.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((CartController) loader.getController()).initialize(getStage(), this, scene, getMainScene());
        super.getStage().setScene(scene);
        super.getStage().show();
    }

    @FXML
    public void iranianHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants = getManager().typeSearch(Integer.parseInt(locationBugger.getText()), FoodType.IRANIAN);
            openRestaurantsMenu(restaurants);
        }
    }

    @FXML
    public void fastHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants = getManager().typeSearch(Integer.parseInt(locationBugger.getText()), FoodType.FAST_FOOD);
            openRestaurantsMenu(restaurants);
        }
    }

    @FXML
    public void drinkHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants = getManager().typeSearch(Integer.parseInt(locationBugger.getText()), FoodType.DRINK);
            openRestaurantsMenu(restaurants);
        }
    }

    @FXML
    public void dessertHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants = getManager().typeSearch(Integer.parseInt(locationBugger.getText()), FoodType.DESSERT);
            openRestaurantsMenu(restaurants);
        }
    }

    @FXML
    public void turkishHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants = getManager().typeSearch(Integer.parseInt(locationBugger.getText()), FoodType.TURKISH);
            openRestaurantsMenu(restaurants);
        }
    }

    @FXML
    public void interNationalHandler() {
        if (locationBugger.getText().equals("0"))
            locationBugger.setStyle("-fx-text-fill: red");
        else {
            ArrayList<Restaurant> restaurants = getManager().typeSearch(Integer.parseInt(locationBugger.getText()), FoodType.INTERNATIONAL_FOOD);
            openRestaurantsMenu(restaurants);
        }
    }
    private void openRestaurantsMenu(ArrayList<Restaurant> restaurants) {
        super.getStage().close();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/RestaurantsMenu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        ((RestaurantsMenuController) loader.getController()).initialize(getStage(), null, scene, getMainScene(),restaurants);
        super.getStage().setScene(scene);
        super.getStage().show();
    }

    @FXML
    public void resetHandler() {
        chargeBox.setValue(null);
        restoreQuestion.setText("");
        restoreSolve.setText("");
    }

    @FXML
    public void confirmHandler() {
        Customer customer = (Customer) getManager().getLoggedInUser();
        int charge = 0;
        if (chargeBox.getValue() != null) {
            String [] word = chargeBox.getValue().split(" ");
            charge = Integer.parseInt(word[0]);
        }
        if (!restoreSolve.getText().isEmpty() && !restoreQuestion.getText().isEmpty() )
            getManager().setRestore(restoreQuestion.getText(),restoreSolve.getText());
        else
            getManager().setRestore(null,null);
        customer.charge(charge);
        this.accountCharge.setText(String.valueOf(customer.getCharge()));
        chargeBox.setValue(null);
    }
    public void update() {
        Customer customer = (Customer) getManager().getLoggedInUser();
        totalSpending.setText(String.valueOf(customer.getSpentMoney()));
        accountCharge.setText(String.valueOf(customer.getCharge()));
        Collections.reverse(customer.getOrders());
        openOrders.getChildren().clear();
        sentOrders.getChildren().clear();
        for (Order order : customer.getOrders()) {
            if (order.getOrderStatus() != OrderStatus.SENT) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxopenorders.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((OpenOrdersBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                ((OpenOrdersBoxController) loader.getController()).chooseOrder(order);
                openOrders.getChildren().add(loader.getRoot());
            }
            else {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxopenorders.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((OpenOrdersBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                ((OpenOrdersBoxController) loader.getController()).chooseOrder(order);
                sentOrders.getChildren().add(loader.getRoot());
            }
        }
        Collections.reverse(customer.getOrders());
    }
    public void mapHandler() {
        Customer customer = (Customer) getManager().getLoggedInUser();
        Stage primaryStage = new Stage();
        AnchorPane pane = new AnchorPane();
        int[][] coordinates = UserList.getUserListInstance().coordinates;
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
                    int nodeLocation = i + 1;
                    circle.setOnMouseClicked(mouseEvent -> {
                        customer.location=nodeLocation;
                        locationBugger.setText(Integer.toString(customer.location));
                        primaryStage.close();
                    });
                    pane.getChildren().add(circle);
                }
            }
        }
        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(pane);
        zoomableScrollPane.setMaxHeight(400);
        zoomableScrollPane.setMaxWidth(600);
        Scene scene = new Scene(zoomableScrollPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Map");
        primaryStage.show();
    }
}

