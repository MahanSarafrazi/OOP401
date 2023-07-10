package phase2.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import phase2.model.Customer;
import phase2.model.Restaurant;

import java.io.IOException;
import java.util.Random;

public class MenuController {

    private final Manager manager = Manager.getManagerInstance();

    private Stage stage;
    private Scene mainScene;
    private Scene previousScene;
    private MenuController fatherStageController;

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String theme) {
        MenuController.theme = theme;
    }

    private static String theme = "gray";

    public MenuController getFatherStageController() {
        return fatherStageController;
    }

    public void setFatherStageController(MenuController fatherStageController) {
        this.fatherStageController = fatherStageController;
    }

    public Manager getManager() {
        return manager;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        if (!getClass().getName().equals("RestaurantBoxController") && !getClass().getName().equals("FoodBoxController")
                && getManager().getLoggedInUser() !=null && getManager().getLoggedInUser() instanceof Customer customer &&
        !getManager().favoriteRestaurants(null,"").isEmpty()) {
            Random random = new Random();
            if (random.nextInt()%25 == 0) {
                Stage stage1 = new Stage();
                Text text = new Text("check out your favorite restaurant and get a new discount token !");
                Restaurant restaurant = getManager().favoriteRestaurants(null,"").get(0);
                FXMLLoader restaurantLoader = new FXMLLoader(this.getClass().getResource("../view/boxRestaurantByOwner.fxml"));
                try {
                    restaurantLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ((RestaurantBoxController) restaurantLoader.getController()).initialize(stage1, getFatherStageController(), getMainScene(), null,true);
                ((RestaurantBoxController) restaurantLoader.getController()).chooseRestaurant(restaurant.getName(), restaurant.getFoodType().get(0), restaurant.getID());
                ((RestaurantBoxController) restaurantLoader.getController()).choosePics(getManager().favoriteRestaurants(null,""));
                VBox vBox = new VBox(text, restaurantLoader.getRoot());
                AnchorPane anchorPane = new AnchorPane(vBox);
                Scene scene = new Scene(anchorPane);
                stage1.setScene(scene);
                stage1.show();
            }
        }
        this.stage = stage;
        this.mainScene = mainScene;
        this.previousScene = previousScene;
        this.fatherStageController = fatherStageController;
    }

    public void back() {
        stage.setScene(previousScene);
    }
}
