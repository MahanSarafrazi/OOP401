package phase2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import phase2.model.Customer;

public class CartController extends MenuController{
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
