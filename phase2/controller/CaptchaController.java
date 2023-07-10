package phase2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class CaptchaController extends MenuController{

    public void initialize(Stage stage, MenuController fatherStageController, Scene mainScene, Scene previousScene,int whichPage) {
        super.initialize(stage, fatherStageController, mainScene, previousScene);
        Random random = new Random();
        number =random.nextInt()%4;
        text.setEditable(false);
        if (number == 0) {
            Image image1 = new Image("@../../resources/Pics/captcha563811.png");
            image.setImage(image1);
            number = 563811;
        } else if (number == 1) {
            Image image1 = new Image("@../../resources/Pics/captcha754257.png");
            image.setImage(image1);
            number = 754257;
        } else if (number == 2) {
            Image image1 = new Image("@../../resources/Pics/captcha782541.png");
            image.setImage(image1);
            number = 782541 ;
        } else  {
            Image image1 = new Image("@../../resources/Pics/captcha853214.png");
            image.setImage(image1);
            number = 853214;
        }
        this.whichPage = whichPage;
    }

    private int whichPage ;

    @FXML
    public ImageView image;

    private int number;

    @FXML
    public TextField text;

    @FXML
    public void confirmHandler() {
        if (text.getText().equals("") || Integer.parseInt(text.getText()) != number)
            back();
        else {
            if (whichPage == 1) {
                FXMLLoader loginLoader = new FXMLLoader(this.getClass().getResource("../view/LoginMenu.fxml"));
                try {
                    loginLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(loginLoader.getRoot());
                ((LoginMenuController) loginLoader.getController()).initialize(getStage(), null, scene, getPreviousScene());
                super.getStage().setScene(scene);
                super.getStage().show();
            }
        }
    }

    @FXML
    public void resetHandler() {
        text.setText("");
    }

    @FXML
    public void set1(){
        text.setText(text.getText()+1);
    }

    @FXML
    public void set2(){
        text.setText(text.getText()+2);
    }

    @FXML
    public void set3(){
        text.setText(text.getText()+3);
    }

    @FXML
    public void set4(){
        text.setText(text.getText()+4);
    }

    @FXML
    public void set5(){
        text.setText(text.getText()+5);
    }

    @FXML
    public void set6(){
        text.setText(text.getText()+6);
    }

    @FXML
    public void set7(){
        text.setText(text.getText()+7);
    }

    @FXML
    public void set8(){
        text.setText(text.getText()+8);
    }

    @FXML
    public void set9(){
        text.setText(text.getText()+9);
    }
}
