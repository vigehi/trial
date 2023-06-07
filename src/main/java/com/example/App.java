package com.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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

// java --module-path /home/oga/pro1/desk/javafx-sdk-20.0.1/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.example.App
// udo update-alternatives --config java