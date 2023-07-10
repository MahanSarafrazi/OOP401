package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.*;
import phase2.view.OrderStatus;
import phase2.view.ZoomableScrollPane;

import java.io.*;
import java.util.ArrayList;

public class RestaurantMenuByOwnerController extends MenuController {

    @FXML
    public Button uploadPhoto;

    @FXML
    public Button seeComments;

    @FXML
    public Button confirm;

    @FXML
    public Button reset;

    @FXML
    public Button logout;

    @FXML
    public Button addFood;

    @FXML
    public TextField name;

    @FXML
    public TextField restaurantID;

    @FXML
    public TextField foodType;

    @FXML
    public TextField score;

    @FXML
    public ArrayList<VBox> list;

    @FXML
    public Text error;

    @FXML
    public ImageView photo;

    @FXML
    public Rectangle photoPlace;

    @FXML
    public TabPane tabPane;

    @FXML
    public Button moreTypes;

    @FXML
    public Tab restaurantsInformation;

    @FXML
    public Tab orderHistory;

    @FXML
    public Tab openOrders;

    @FXML
    public VBox orderHistoryList;

    @FXML
    public VBox openOrdersList;

    @FXML
    public Button map;

    @FXML
    public TextField locationBugger;

    final FileChooser fileChooser = new FileChooser();
    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getID();

    @FXML
    public AnchorPane pane;

    @FXML
    public void seeCommentsHandler(ActionEvent actionEvent) {
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

    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        name.setText(getManager().getLoggedInUser().getActiveRestaurant().getName());
        restaurantID.setText(Integer.toString(ID));
        foodType.setText(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(0).name());
        score.setText(getManager().averageRating());
        pane.setStyle("-fx-background-color: " + getTheme());
        if(getManager().getLoggedInUser().getActiveRestaurant().getPhotoPath() != null) {
            Image image = new Image(getManager().getLoggedInUser().getActiveRestaurant().getPhotoPath(), photoPlace.getWidth(), photoPlace.getHeight(), false, false);
            photo.setImage(image);
        }
        locationBugger.setText(Integer.toString(getManager().getLoggedInUser().getActiveRestaurant().getLocation()));
        list = new ArrayList<>();
        for (int i = 0; i < tabPane.getTabs().size(); ++i) {
            list.add((VBox) (((ScrollPane)(tabPane.getTabs().get(i).getContent())).getContent()));
            tabPane.getTabs().get(i).setText(String.valueOf(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(i)));
        }
        updateRestaurantInformation();
    }


    public void addFoodHandler(ActionEvent actionEvent) {
        FXMLLoader addLoader = new FXMLLoader(this.getClass().getResource("../view/addFood.fxml"));
        try {
            addLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(addLoader.getRoot());
        Stage stage = new Stage();
        stage.setTitle("add food");
        stage.setScene(scene);
        ((addFoodController) addLoader.getController()).initialize(stage, this, scene, null);
        ((addFoodController) addLoader.getController()).getStage().show();
    }

    public void updateRestaurantInformation() {

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
            ArrayList<Food> foods = ((RestaurantOwner) getManager().getLoggedInUser()).getActiveRestaurant().getFoods();
            for (Food food : foods) {
                if(food.getType().equals(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(i))) {
                    loader = new FXMLLoader(this.getClass().getResource("../view/BoxFoodbycustomer.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ((FoodBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                    ((FoodBoxController) loader.getController()).chooseFood(food, true);
                    list.get(i).getChildren().add(loader.getRoot());
                }
            }
        }
    }

    public void updateOrderHistory() {
        orderHistoryList.getChildren().clear();
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        for (Order order : restaurant.getOrders()) {
            if(!order.getOrderStatus().equals(OrderStatus.NOT_READY)) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxorderhistory.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((OrderHistoryBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                ((OrderHistoryBoxController) loader.getController()).chooseOrder(order);
                orderHistoryList.getChildren().add(loader.getRoot());
            }
        }
    }

    public void updateOpenOrders() {
        openOrdersList.getChildren().clear();
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        for (Order order : restaurant.getOrders()) {
            if(order.getOrderStatus().equals(OrderStatus.NOT_READY)) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Boxopenorders.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((OpenOrdersBoxController) loader.getController()).initialize(getStage(), this, getMainScene(), null);
                ((OpenOrdersBoxController) loader.getController()).chooseOrder(order);
                openOrdersList.getChildren().add(loader.getRoot());
            }
        }
    }

    @FXML
    public void backHandler(ActionEvent actionEvent) {
        getManager().getLoggedInUser().setActiveRestaurant(null);
        back();
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


    public void uploadPhotoHandler(ActionEvent actionEvent) {
        File selectedFile = fileChooser.showOpenDialog(getStage());

        if (selectedFile != null) {

            final InputStream targetStream;
            try {
                targetStream = new DataInputStream(new FileInputStream(selectedFile));
                Image image = new Image(targetStream, photoPlace.getWidth(), photoPlace.getHeight(), false, false);
                photo.setImage(image);

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            getManager().getLoggedInUser().getActiveRestaurant().setPhotoPath(selectedFile.getPath());
            ((RestaurantOwnerMenuController) getFatherStageController()).update();

        }
    }

    public void confirmHandler(ActionEvent actionEvent) {
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();

        restaurant.setName(name.getText());
        FoodType replacingType = null;
        for (FoodType value : FoodType.values()) {
            if (value.commandingPattern.matcher(foodType.getText()).find()) {
                replacingType = value;
                break;
            }
        }
        if(replacingType != null) {
            boolean isThereFoods = false;
            for (Food food : getManager().getLoggedInUser().getActiveRestaurant().getFoods()) {
                if(food.getType().equals(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(0))) {
                    isThereFoods = true;
                    break;
                }
            }

            if(!isThereFoods) {


                for (FoodType type : getManager().getLoggedInUser().getActiveRestaurant().getFoodType()) {
                    if(type.equals(replacingType)) {
                        getManager().getLoggedInUser().getActiveRestaurant().getFoodType().remove(type);
                        break;
                    }
                }

                restaurant.editFoodType(restaurant.getFoodType().get(0), replacingType);
                for (Food food : restaurant.getFoods()) {
                    System.out.println(food.getType().name());
                }
                error.setFill(Paint.valueOf("green"));
                error.setText("successful");
            } else {
                error.setFill(Paint.valueOf("red"));
                error.setText("There is foods with this type");
            }
        } else {
            error.setFill(Paint.valueOf("red"));
            error.setText("invalid food type");
        }

        foodType.setText(restaurant.getFoodType().get(0).name());
        updateRestaurantInformation();

        PauseTransition hitAnimation = new PauseTransition(Duration.seconds(3));
        hitAnimation.setOnFinished(e -> error.setText(""));
        hitAnimation.playFromStart();
    }

    public void resetHandler(ActionEvent actionEvent) {
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        name.setText(restaurant.getName());
        foodType.setText(restaurant.getFoodType().get(0).name());
    }

    public void moreTypesHandler(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/FoodTypes.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        Stage stage = new Stage();
        stage.setTitle("food types");
        stage.setScene(scene);
        ((FoodTypesController) loader.getController()).initialize(stage, this, scene, null);
        ((FoodTypesController) loader.getController()).getStage().show();
    }


    @FXML
    public void orderHistoryHandler(Event event) {
        updateOrderHistory();
    }


    @FXML
    public void openOrdersHandler(Event event) {
        updateOpenOrders();
    }


    @FXML
    public void mapHandler(ActionEvent actionEvent) {
        Restaurant restaurant = getManager().getLoggedInUser().getActiveRestaurant();
        Stage primaryStage = new Stage();
        AnchorPane pane = new AnchorPane();
        int[][] coordinates = getManager().getMap().coordinates;
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
                        restaurant.setLocation(nodeLocation);
                        locationBugger.setText(Integer.toString(restaurant.getLocation()));
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
