// package com.example;
// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// public class MainApp extends Application {

//     private Connection connection;

//     // Database connection details
//     private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
//     private static final String DB_USERNAME = "username";
//     private static final String DB_PASSWORD = "password";

//     @Override
//     public void start(Stage primaryStage) throws Exception {
//         // Establish the database connection
//         try {
//             connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//         } catch (SQLException e) {
//             e.printStackTrace();
//             // Handle connection error
//         }

//         TableView<Car> carsTable = createCarsTable();
//         TableView<Loader> loadersTable = createLoadersTable();
//         TableView<Weight> weightsTable = createWeightsTable();

//         VBox root = new VBox();
//         root.getChildren().addAll(carsTable, loadersTable, weightsTable);

//         Scene scene = new Scene(root, 800, 600);

//         primaryStage.setScene(scene);
//         primaryStage.setTitle("Car Database Viewer");
//         primaryStage.show();
//     }

//     private TableView<Car> createCarsTable() {
//         TableView<Car> carsTable = new TableView<>();

//         TableColumn<Car, String> carColumn = new TableColumn<>("Car");
//         carColumn.setCellValueFactory(cellData -> cellData.getValue().carProperty());

//         TableColumn<Car, String> ownerColumn = new TableColumn<>("Owner");
//         ownerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());

//         carsTable.getColumns().addAll(carColumn, ownerColumn);

//         // Populate the cars table with data
//         try {
//             ResultSet resultSet = connection.createStatement().executeQuery("SELECT car, owner FROM cars");

//             while (resultSet.next()) {
//                 String car = resultSet.getString("car");
//                 String owner = resultSet.getString("owner");

//                 carsTable.getItems().add(new Car(car, owner));
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             // Handle query execution error
//         }

//         return carsTable;
//     }

//     private TableView<Loader> createLoadersTable() {
//         TableView<Loader> loadersTable = new TableView<>();

//         TableColumn<Loader, String> loaderColumn = new TableColumn<>("Loader");
//         loaderColumn.setCellValueFactory(cellData -> cellData.getValue().loaderProperty());

//         loadersTable.getColumns().add(loaderColumn);

//         // Populate the loaders table with data
//         try {
//             ResultSet resultSet = connection.createStatement().executeQuery("SELECT loader FROM loaders");

//             while (resultSet.next()) {
//                 String loader = resultSet.getString("loader");

//                 loadersTable.getItems().add(new Loader(loader));
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             // Handle query execution error
//         }

//         return loadersTable;
//     }

//     private TableView<Weight> createWeightsTable() {
//         TableView<Weight> weightsTable = new TableView<>();

//         TableColumn<Weight, String> weightColumn = new TableColumn<>("Weight");
//         weightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty());

//         weightsTable.getColumns().add(weightColumn);

//         // Populate the weights table with data
//         try {
//             ResultSet resultSet = connection.createStatement().executeQuery("SELECT weight FROM weights");

//             while (resultSet.next()) {
//                 String weight = resultSet.getString("weight");

//                 weightsTable.getItems().add(new Weight(weight));
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             // Handle query execution error
//         }

//         return weightsTable;
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }
