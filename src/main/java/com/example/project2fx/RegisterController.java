package com.example.project2fx;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private RadioButton female;

    @FXML
    private Button login;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton others;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup;

    @FXML
    private TextField username;




    @FXML
    public void createFolder() {

        File folder = new File("user_information");


        if (!folder.exists()) {
            folder.mkdir();
            System.out.println("Folder created successfully");
        }

        readFile();

    }

    @FXML
    public void readFile() {

        String filename = "user_information/users.txt";

        try {
            File file = new File(filename);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("File created");
                } else {
                    System.out.println("Error: Could not create file");
                }
            }
        }
        catch (IOException e) {
            System.out.println("File exists");
        }

    }

    @FXML
    public void countLines(){
        String filePath = "user_information/users.txt";
        int count = 0;
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                if (!line.isBlank()) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        System.out.println("Number of lines: " + count);

    }

    @FXML
    public void addData(String username, String password, String gender, String email) throws Exception {

        File file = new File("user_information/users.txt");

        try {
            RandomAccessFile writer = new RandomAccessFile(file, "rw");
            writer.seek(writer.length());

            String newline = System.getProperty("line.separator");

            writer.writeBytes(newline);
            writer.writeBytes("username: " + username + newline);
            writer.writeBytes("password: " + password + newline);
            writer.writeBytes("gender: " + gender + newline);
            writer.writeBytes("email: " + email + newline);

            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("There is no such file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean checkData() throws Exception{


        File file = new File("user_information/users.txt");
        String toCheck = username.getText().replaceAll("\\s","");
        boolean exists = false;
        boolean gmailExists = false;


        try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = reader.readLine()) != null) {

                if(line.startsWith("username")){
                    String[] words = line.split("username: ");
                    for(String t : words){
                        if(t.equals(toCheck))
                            exists = true;
                    }
                }else if(line.startsWith("email")){
                    String[] words = line.split("email: ");
                    for(String t : words){
                        if(t.equals(email.getText()))
                            gmailExists = true;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no such file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(exists){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There is already an account with this username");
            alert.showAndWait();
            return false;
        }

        if(gmailExists){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gmail already exists");
            alert.showAndWait();
            return false;
        }


        if(username.getText().isEmpty() || username.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill the Name");
            alert.showAndWait();
            return false;
        }else if(username.getText().length() > 9){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username can not contain more than 9 characters!");
            alert.showAndWait();
            return false;
        }

        if(password.getText().isEmpty() || password.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill the password");
            alert.showAndWait();
            return false;
        }else if (password.getText().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password cannot contain spaces");
            alert.showAndWait();
            return false;
        }else if(password.getText().length() > 9){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password cannot contain more than 9 characters");
            alert.showAndWait();
            return false;
        }

        if(username.getText().equals(password.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login and Password can not be the same");
            alert.showAndWait();
            return false;
        }

        if (!male.isSelected() && !female.isSelected() && !others.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must choose gender");
            alert.showAndWait();
            return false;
        }

        if(email.getText().isEmpty() || email.getText().isBlank() || !email.getText().endsWith("@gmail.com")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must fill the email and it must ends with @gmail.com");
            alert.showAndWait();
            return false;
        }else if(email.getText().length() > 19){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Email can not be more than 9 characters!");
            alert.showAndWait();
            return false;
        }

        return true;

    }


    @FXML
    public void signUpAction(ActionEvent event) throws Exception {

        createFolder();

        if(!checkData())
            return;


        try {

            String name = username.getText().replaceAll("\\s","");
            String userPassword = password.getText();
            String gen = "";

            if(male.isSelected()){
                gen = "male";
            }else if(female.isSelected()){
                gen = "female";
            }else if(others.isSelected()){
                gen = "others";
            }

            String userEmail = email.getText().replaceAll("\\s","");


            addData(name,userPassword,gen,userEmail);

            countLines();
            System.out.println("SignUp is successful");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            signup.getScene().getWindow().hide();

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


        }catch (IOException e){
            e.printStackTrace();
        }


    }


    @FXML
    public void loginAction(ActionEvent event) throws IOException {
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
    }


}

