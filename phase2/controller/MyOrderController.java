package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import phase2.model.Map;
import phase2.model.Deliverer;
import phase2.model.Order;
import phase2.view.ZoomableScrollPane;

public class MyOrderController extends MenuController {

    @FXML
    public TextField deliveryPrice;

    @FXML
    public Button showPath;

    private Order order;

    public void chooseOrder(Order order) {
        this.order = order;
        deliveryPrice.setText(Double.toString(order.totalDeliveryPrice()));
        order.setDeliverer(((Deliverer)getManager().getLoggedInUser()).getLocation());
    }

    public Order getOrder() {
        return order;
    }
    @FXML
    public void showPath() {
        Deliverer deliverer = (Deliverer) getManager().getLoggedInUser();
        Map map = Manager.getManagerInstance().getMap();
        if (deliverer.getOrder().reachedRestaurant()) {
            map.findShortestPath(deliverer.getLocation(),deliverer.getOrder().getRestaurantLocation(),true);
        } else {
            map.findShortestPath(deliverer.getOrder().getRestaurantLocation(),deliverer.getOrder().getCustomerLocation(),true);
        }
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
}
