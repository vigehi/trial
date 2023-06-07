package com.example;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MainController {
    @FXML
    private GridPane gridPane;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> carColumn;

    @FXML
    private TableView<Loader> loadersTable;

    @FXML
    private TableColumn<Loader, String> loaderColumn;

    @FXML
    private TableView<Weight> weightsTable;

    @FXML
    private TableColumn<Weight, String> weightColumn;

    @FXML
    private TableView<Data> dataTable;

    @FXML
    private TableColumn<Data, Timestamp> timestampColumn;

    @FXML
    private TableColumn<Data, String> carRegNumberColumn;

    @FXML
    private TableColumn<Data, String> loaderNameColumn;

    @FXML
    private TableColumn<Data, String> weight1Column;

    private Connection connection;

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/try";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Baraza2011";

    @FXML
    private void initialize() {
        // Establish the database connection
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            populateCarsTable();
            populateLoadersTable();
            populateWeightsTable();
            populateDataTable();

            // Set cell value factories for the table columns
            carColumn.setCellValueFactory(new PropertyValueFactory<>("car"));
            loaderColumn.setCellValueFactory(new PropertyValueFactory<>("loader"));
            weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
            timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            carRegNumberColumn.setCellValueFactory(new PropertyValueFactory<>("carRegNumber"));
            loaderNameColumn.setCellValueFactory(new PropertyValueFactory<>("loaderName"));
            weight1Column.setCellValueFactory(new PropertyValueFactory<>("weight1"));

            carsTable.setOnMouseClicked(event -> handleCarTableClick());

            loadersTable.setOnMouseClicked(event -> handleLoadersTableClick());

            weightsTable.setOnMouseClicked(event -> handleWeightsTableClick());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Car selectedCar;
private Loader selectedLoader;
private Weight selectedWeight;

private void handleCarTableClick() {
    selectedCar = carsTable.getSelectionModel().getSelectedItem();
    insertDataIfSelectionComplete();
}

private void handleLoadersTableClick() {
    selectedLoader = loadersTable.getSelectionModel().getSelectedItem();
    insertDataIfSelectionComplete();
}

private void handleWeightsTableClick() {
    selectedWeight = weightsTable.getSelectionModel().getSelectedItem();
    insertDataIfSelectionComplete();
}

private void insertDataIfSelectionComplete() {
    if (selectedCar != null && selectedLoader != null && selectedWeight != null) {
        String carName = selectedCar.getCar();
        String loaderName = selectedLoader.getLoader();
        String weightValue = selectedWeight.getWeight();

        insertDataIntoDatabase(carName, loaderName, weightValue);

        // Reset the selections
        carsTable.getSelectionModel().clearSelection();
        loadersTable.getSelectionModel().clearSelection();
        weightsTable.getSelectionModel().clearSelection();

        selectedCar = null;
        selectedLoader = null;
        selectedWeight = null;
    }
}

    private void insertDataIntoDatabase(String carName, String loaderName, String weightValue) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        String query = "INSERT INTO data1 (timestamp, car_reg_number, loader_name, weight) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, timestamp);
            statement.setString(2, carName);
            statement.setString(3, loaderName);
            statement.setString(4, weightValue);
            statement.executeUpdate();
    
            // Update the dataTable in the UI
            populateDataTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void populateDataTable() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM data1");
            dataTable.getItems().clear();
    
            while (resultSet.next()) {
                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
                String car_reg_number = resultSet.getString("car_reg_number");
                String loader_name = resultSet.getString("loader_name");
                String weight = resultSet.getString("weight");
    
                dataTable.getItems().add(new Data(timestamp, car_reg_number, loader_name, weight));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
        

    private void populateCarsTable() {
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT car_name, owner_name FROM car")) {
            while (resultSet.next()) {
                String car_name = resultSet.getString("car_name");
                String owner_name = resultSet.getString("owner_name");

                carsTable.getItems().add(new Car(car_name, owner_name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateLoadersTable() {
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT loader_name FROM loaders")) {
            while (resultSet.next()) {
                String loader_name = resultSet.getString("loader_name");

                loadersTable.getItems().add(new Loader(loader_name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateWeightsTable() {
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT weight_value FROM weight")) {
            while (resultSet.next()) {
                String weight_value = resultSet.getString("weight_value");

                weightsTable.getItems().add(new Weight(weight_value));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
