package com.example.project2fx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Button back;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;

    public final String adminEmail = "admin@gmail.com";
    public final String adminPassword = "123456";





    @FXML
    void backAction() {
        back.setOnAction(event -> {
            login.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            try {
                fxmlLoader.load();
            }catch (IOException e){
                e.printStackTrace();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        });
    }


    @FXML
    void loginAction() {


        if(email.getText().isBlank()){
            email.setStyle("-fx-background-color: #ff1493;");
        }else{
            email.setStyle("-fx-background-color: white;");
        }


        if(!email.getText().equals(adminEmail)){
            label1.setText("Wrong email for admin");
            email.setStyle("-fx-background-color: #ff1493;");
        }else{
            email.setStyle("-fx-background-color: white;");
            label1.setText("");
        }

        if(!password.getText().equals(adminPassword)){
            label2.setText("Wrong password for admin");
            password.setStyle("-fx-background-color: #ff1493;");
        }else {
            label2.setText("");
            password.setStyle("-fx-background-color: white;");
        }



        if(email.getText().equals(adminEmail) && password.getText().equals(adminPassword)){
            login.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newsPage.fxml"));

            try {
                fxmlLoader.load();
            }catch (IOException e){
                e.printStackTrace();
            }

            System.out.println("Successful login of Admin");

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    @FXML
    void initialize() {
        backAction();
    }


}