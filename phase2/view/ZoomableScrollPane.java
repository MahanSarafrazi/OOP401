package phase2.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import phase2.controller.Manager;
import phase2.model.UserList;

public class ZoomableScrollPane extends ScrollPane {
    private double scaleValue = 0.7;
    private double zoomIntensity = 0.02;
    private Node target;
    private Node zoomNode;

    public ZoomableScrollPane(Node target) {
        super();
        this.target = target;
        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));

        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToHeight(true); //center
        setFitToWidth(true); //center

        updateScale();
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(e -> {
            e.consume();
            onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
        });
        return outerNode;
    }

    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void updateScale() {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }

    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }

    public void baseMap(Stage primaryStage) {
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
        int shortest = manager.getMap().findShortestPath(50, 57, true);
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
