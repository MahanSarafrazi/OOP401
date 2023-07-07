package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import phase2.model.Cart;
import phase2.model.Customer;

import java.io.IOException;

public class CartController extends MenuController{
    @Override
    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene) {
        super.initialize(stage,fatherStageController,mainScene,previousScene);
        FXMLLoader foodLoader;
        Customer customer = (Customer) getManager().getLoggedInUser();
        double totalPrice = 0;
        Cart cart = customer.getCart();
        for (int i=0;i<cart.getFoodsCount().size();i++)
            totalPrice+=cart.getFoodsCount().get(i)*cart.getFoods().get(i).getDiscountedPrice();
        int shortestPath=0;
        if (!customer.getCart().getFoods().isEmpty())
            shortestPath = getManager().getMap().findShortestPath(50, customer.getOrderedRestaurant().getLocation(),false)/30;
        double totalDeliveryPrice = (1.1 + 0.1 * (double) shortestPath)*totalPrice;
        this.totalPrice.setText(String.valueOf(totalDeliveryPrice));
        this.totalPrice.setEditable(false);
        this.accountCharge.setText(String.valueOf(customer.getCharge()));
        this.accountCharge.setEditable(false);
        for (String string : customer.getDiscountTokens())
            this.useDiscountTokens.getItems().add(string);
        for (int i = 0 ; i < customer.getCart().getFoods().size() ; ++i) {
            foodLoader = new FXMLLoader(this.getClass().getResource("../view/boxFoodByCustomer.fxml"));
            try {
                foodLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((FoodBoxController) foodLoader.getController()).initialize(getStage(), getFatherStageController(), null, null);
            ((FoodBoxController) foodLoader.getController()).chooseFood(customer.getCart().getFoods().get(i).getName(),
                    customer.getCart().getFoods().get(i).getType(), customer.getCart().getFoods().get(i).getDiscountedPrice(),customer.getCart().getFoods().get(i).getID());
            this.vBox.getChildren().add(foodLoader.getRoot());
        }
    }

    @FXML
    public TextField totalPrice;

    @FXML
    public TextField accountCharge;

    @FXML
    public ChoiceBox<String> useDiscountTokens;

    @FXML
    public VBox vBox;

    @FXML
    public void resetHandler() {
        useDiscountTokens.setValue(null);
    }

    @FXML
    public void backHandler() {
        back();
    }

    @FXML
    public void confirmHandler() {
        if ((useDiscountTokens.getValue() == null && Double.parseDouble(accountCharge.getText()) > Double.parseDouble(totalPrice.getText()))
        || (useDiscountTokens.getValue() != null && Double.parseDouble(accountCharge.getText()) > 0.8*Double.parseDouble(totalPrice.getText()))
                || vBox.getChildren().isEmpty())
            accountCharge.setStyle("-fx-text-fill: red");
        else {
            vBox.getChildren().clear();
            if (useDiscountTokens.getValue() == null)
                getManager().confirmOrder(50,Double.parseDouble(totalPrice.getText()),"NO");
            else
                getManager().confirmOrder(50,Double.parseDouble(totalPrice.getText()),useDiscountTokens.getValue());
            CustomerMenuController customerMenuController = (CustomerMenuController) getFatherStageController();
            Customer customer = (Customer) getManager().getLoggedInUser();
            customerMenuController.totalSpending.setText(String.valueOf(customer.getSpentMoney()));
            customerMenuController.accountCharge.setText(String.valueOf(customer.getCharge()));
            back();
        }
    }
}
