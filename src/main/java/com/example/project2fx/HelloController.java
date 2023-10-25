package com.example.project2fx;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adminButton;

    @FXML
    private Hyperlink forgotpassword;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private CheckBox remember;

    @FXML
    private Button signup;

    @FXML
    private TextField username;


    @FXML
    public void logic(){



        if(username.getText().isBlank() && password.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fields are blank ");
            alert.showAndWait();
            return;
        }

        File selFile = new File("remSelected.txt");
        if (remember.isSelected()) {
            try {
                selFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        File filePath = new File("user_information/users.txt");
        boolean userNameMatched = false;
        boolean passwordMatched = false;

        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            String line;
            while ((line = file.readLine()) != null) {

                if(line.startsWith("username")){
                    String[] words = line.split(": ");
                    for(String t : words){
                        if(t.equals(username.getText().replaceAll("\\s",""))){
                            userNameMatched = true;
                            String pas;
                            if((pas = file.readLine()).equals("password: "+password.getText().replaceAll("\\s",""))){
                                passwordMatched = true;
                            }
                        }

                    }
                }


            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if(!passwordMatched || !userNameMatched){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong username/password");
            alert.showAndWait();
            return;
        }

        if(userNameMatched && passwordMatched){
            login.getScene().getWindow().hide();



            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newsPage.fxml"));
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
        }
    }

    @FXML
    void adminAction(){
        adminButton.setOnAction(actionEvent -> {
            login.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adminPage.fxml"));
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
    void loginAction(){
        login.setOnAction(actionEvent -> {

            logic();

        });
    }

    @FXML
    void signUpAction() {
        signup.setOnAction(actionEvent -> {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
        });
    }

    @FXML
    public void forgotPasswordAction(){
        forgotpassword.setOnAction(event -> {
            forgotpassword.getScene().getWindow().hide();


            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotPage.fxml"));
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
        signUpAction();
        loginAction();
        adminAction();
        forgotPasswordAction();
    }

}
