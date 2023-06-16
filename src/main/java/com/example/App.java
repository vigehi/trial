package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class App extends Application {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    @Override
    public void start(Stage primaryStage) throws Exception {
        showLoginScreen(primaryStage);
    }

    private void showLoginScreen(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Login");
    Label usernameLabel = new Label("Username:");
    TextField usernameField = new TextField();
    Label passwordLabel = new Label("Password:");
    PasswordField passwordField = new PasswordField();
    Button loginButton = new Button("Login");
    loginButton.setOnAction(event -> {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            try {
                showMainScreen(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    });
    VBox loginLayout = new VBox(10);
    loginLayout.getStyleClass().add("login-layout"); 
    usernameLabel.getStyleClass().add("login-label");
    passwordLabel.getStyleClass().add("login-label");
    loginButton.getStyleClass().add("login-button");

    loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
    loginLayout.setSpacing(10);
    loginLayout.setPadding(new javafx.geometry.Insets(10));

    Scene loginScene = new Scene(loginLayout, 300, 200);

    loginScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Load external CSS file
    primaryStage.setScene(loginScene);
    primaryStage.setResizable(false);
    primaryStage.show();
}


    private void showMainScreen(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Database Viewer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


// java --module-path /home/oga/trial/javafx-sdk-20.0.1/lib --add-modules
// javafx.controls,javafx.fxml -cp target/classes com.example.App
// sudo update-alternatives --config java
// java --module-path /home/oga/trial/javafx-sdk-20.0.1/lib --add-modules javafx.controls,javafx.fxml -cp  target/classes:/home/oga/trial/postgresql-42.6.0.jar com.example.App
