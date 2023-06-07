package com.example;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Data {
    private ObjectProperty<LocalDateTime> timestamp;
    private StringProperty carRegNumber;
    private StringProperty loaderName;
    private StringProperty weight;

    public Data(LocalDateTime timestamp, String carRegNumber, String loaderName, String weight) {
        this.timestamp = new SimpleObjectProperty<>(timestamp);
        this.carRegNumber = new SimpleStringProperty(carRegNumber);
        this.loaderName = new SimpleStringProperty(loaderName);
        this.weight = new SimpleStringProperty(weight);
    }

    public Data(String timestamp, String car_reg_number, String loader_name, String weight) {
    }

    public LocalDateTime getTimestamp() {
        return timestamp.get();
    }

    public ObjectProperty<LocalDateTime> timestampProperty() {
        return timestamp;
    }

    public String getCarRegNumber() {
        return carRegNumber.get();
    }

    public StringProperty carRegNumberProperty() {
        return carRegNumber;
    }

    public String getLoaderName() {
        return loaderName.get();
    }

    public StringProperty loaderNameProperty() {
        return loaderName;
    }

    public String getWeight1() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }
}
