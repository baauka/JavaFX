package com.example.project2fx;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ForgotPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label passwordLabel;
    @FXML
    private TextField email;

    @FXML
    private Button find;

    @FXML
    private Button login;

    @FXML
    private TextField username;


    @FXML
    void findAction() {
        find.setOnAction(event -> {

            File filePath = new File("user_information/users.txt");
            String password = "";
            boolean exists = false;
            String currentName = "";
            String currentEmail = "";

            if(username.getText().isBlank() && email.getText().isBlank()){
                username.setStyle("-fx-background-color: #ff1493;");
                email.setStyle("-fx-background-color: #ff1493;");
                return;
            }else {
                username.setStyle("-fx-background-color: #ff1493;");
                email.setStyle("-fx-background-color: white;");
            }

            if(username.getText().isBlank()){
                username.setStyle("-fx-background-color: #ff1493;");
                return;
            }else{
                username.setStyle("-fx-background-color: white;");
            }

            if(email.getText().isBlank()){
                email.setStyle("-fx-background-color: #ff1493;");
                return;
            }else{
                email.setStyle("-fx-background-color: white;");
            }


            try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")){
                String line;


                while((line = file.readLine()) != null){
                    if(line.startsWith("username")){
                        currentName = line.substring("username:".length()).trim();
                        if(currentName.equals(username.getText()))
                            password = file.readLine();
                    }else if(line.startsWith("email")){
                        currentEmail = line.substring("email:".length()).trim();
                        if(currentEmail.equals(email.getText())) {
                            if(currentName.equals(username.getText()))
                                exists = true;
                        }
                    }
                }

                if(!exists){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("This user does not exist or your inputs are wrong");
                    alert.showAndWait();
                    passwordLabel.setText("");
                    return;
                }


                passwordLabel.setText(password);


            }catch (FileNotFoundException e) {
                System.out.println("File not found: " + filePath);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }


        });
    }

    @FXML
    void loginAction() {
        login.setOnAction(event -> {
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
    void initialize() {
        loginAction();
        findAction();
    }

}
