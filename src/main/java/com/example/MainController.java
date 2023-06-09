package com.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.tuple.Triple;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.scene.control.Alert;

public class MainController {
    @FXML
    private GridPane gridPane;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private TableColumn<Car, String> carColumn;

    @FXML
    private TableColumn<Car, String> ownerColumn;

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

    @FXML
    private Button addDataButton;
    private Connection connection;

    // Database connection details
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/try";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Baraza2011";

    @FXML
    private void initialize() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            populateCarsTable();
            populateLoadersTable();
            populateWeightsTable();
            populateDataTable();
            carColumn.setCellValueFactory(new PropertyValueFactory<>("car"));
            ownerColumn.setCellValueFactory(new PropertyValueFactory<>("Owner"));
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

    @FXML

    private void addDataButtonClicked(ActionEvent event) {
        Dialog<DataEntry> dialog = new Dialog<>();
        dialog.setTitle("Add Data");
        dialog.setHeaderText("Enter Car, Owner, Loader, and Weight Details");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Label carLabel = new Label("Car:");
        Label ownerLabel = new Label("Owner:");
        Label loaderLabel = new Label("Loader:");
        Label weightLabel = new Label("Weight:");

        TextField carTextField = new TextField();
        TextField ownerTextField = new TextField();
        TextField loaderTextField = new TextField();
        TextField weightTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.add(carLabel, 0, 0);
        gridPane.add(carTextField, 1, 0);
        gridPane.add(ownerLabel, 0, 1);
        gridPane.add(ownerTextField, 1, 1);
        gridPane.add(loaderLabel, 0, 2);
        gridPane.add(loaderTextField, 1, 2);
        gridPane.add(weightLabel, 0, 3);
        gridPane.add(weightTextField, 1, 3);

        dialog.getDialogPane().setContent(gridPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new DataEntry(carTextField.getText(), ownerTextField.getText(), loaderTextField.getText(),
                        weightTextField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(dataEntry -> {
            String carName = dataEntry.getCarName();
            String ownerName = dataEntry.getOwnerName();
            String loaderName = dataEntry.getLoaderName();
            String weightValue = dataEntry.getWeightValue();
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String insertCarQuery = "INSERT INTO car (car_name, owner_name) VALUES (?, ?)";
                try (PreparedStatement carStatement = connection.prepareStatement(insertCarQuery)) {
                    carStatement.setString(1, carName);
                    carStatement.setString(2, ownerName);
                    carStatement.executeUpdate();
                    populateCarsTable();
                }

                String insertLoadersQuery = "INSERT INTO loaders (loader_name) VALUES (?)";
                try (PreparedStatement loadersStatement = connection.prepareStatement(insertLoadersQuery)) {
                    loadersStatement.setString(1, loaderName);
                    loadersStatement.executeUpdate();
                    populateLoadersTable();
                }
                String insertWeightQuery = "INSERT INTO weight (weight_value) VALUES (CAST(? AS double precision))";
                try (PreparedStatement weightStatement = connection.prepareStatement(insertWeightQuery)) {
                    weightStatement.setString(1, weightValue);
                    weightStatement.executeUpdate();
                    populateWeightsTable();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Data Entry");
                alert.setHeaderText("Data Inserted Successfully");
                alert.setContentText("Data has been inserted into the tables.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Data Entry Error");
                alert.setContentText("An error occurred while inserting data into the tables.");
                alert.showAndWait();
            }
        });
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
            carsTable.getItems().clear();
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
            loadersTable.getItems().clear();
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
            weightsTable.getItems().clear();
            while (resultSet.next()) {
                String weight_value = resultSet.getString("weight_value");

                weightsTable.getItems().add(new Weight(weight_value));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
