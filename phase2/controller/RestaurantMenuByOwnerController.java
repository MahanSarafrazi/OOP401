package phase2.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import phase2.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RestaurantMenuByOwnerController extends MenuController implements Initializable {

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

    final FileChooser fileChooser = new FileChooser();
    private final int ID = getManager().getLoggedInUser().getActiveRestaurant().getID();

    @FXML
    public void seeCommentsHandler(ActionEvent actionEvent) {
        FXMLLoader commentsLoader = new FXMLLoader(this.getClass().getResource("../view/Comments.fxml"));
        try {
            commentsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene commentsScene = new Scene(commentsLoader.getRoot());
        ArrayList<Comment> comments = getManager().getLoggedInUser().getActiveRestaurant().getComments();
        for(int i = 0; i < getManager().getLoggedInUser().getActiveRestaurant().getComments().size(); ++i) {
            HBox hBox = new HBox();
            Text text = new Text(comments.get(i).getComment());
            Button button = new Button("add response");
            button.setId("button" + i);
            hBox.getChildren().add(text);
            hBox.getChildren().add(button);
            ((VBox) commentsLoader.getRoot()).getChildren().add(hBox);
        }
        ((CommentsController) commentsLoader.getController()).initialize(new Stage(), this, commentsScene, null);
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
        if(getManager().getLoggedInUser().getActiveRestaurant().getPhotoPath() != null) {
            Image image = new Image(getManager().getLoggedInUser().getActiveRestaurant().getPhotoPath(), photoPlace.getWidth(), photoPlace.getHeight(), false, false);
            photo.setImage(image);
        }
        list = new ArrayList<>();
        for (int i = 0; i < tabPane.getTabs().size(); ++i) {
            list.add((VBox) (((ScrollPane)(tabPane.getTabs().get(i).getContent())).getContent()));
            tabPane.getTabs().get(i).setText(String.valueOf(getManager().getLoggedInUser().getActiveRestaurant().getFoodType().get(i)));
        }
        update();
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

    public void update() {

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
                    ((FoodBoxController) loader.getController()).chooseFood(food.getName(), food.getType(), food.getPrice(), food.getID());
                    list.get(i).getChildren().add(loader.getRoot());
                }
            }
        }
    }

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
        update();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderHistory.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Orderhistorybyowner.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(loader.getRoot());
                TabPane tabPane = ((TabPane) ((AnchorPane) scene.getRoot()).getChildren().get(0));
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(1);
                ((OrderHistoryController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getPreviousScene());
                ((OrderHistoryController) loader.getController()).getStage().setScene(scene);
            }
        });

        openOrders.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Openordersbyowner.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(loader.getRoot());
                TabPane tabPane = ((TabPane) ((AnchorPane) scene.getRoot()).getChildren().get(0));
                SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                selectionModel.select(2);
                ((OpenOrdersController) loader.getController()).initialize(getStage(), getFatherStageController(), scene, getPreviousScene());
                ((OpenOrdersController) loader.getController()).getStage().setScene(scene);
            }
        });
    }
}
