package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
    private StringProperty car_name;
    private StringProperty owner_name;

    public Car(String car_name, String owner_name) {
        this.car_name = new SimpleStringProperty(car_name);
        this.owner_name = new SimpleStringProperty(owner_name);
    }

    public StringProperty carProperty() {
        return car_name;
    }

    public String getCar() {
        return car_name.get();
    }

    public void setCar(String car_name) {
        this.car_name.set(car_name);
    }

    public StringProperty ownerProperty() {
        return owner_name;
    }

    public String getOwner() {
        return owner_name.get();
    }

    public void setOwner(String owner_name) {
        this.owner_name.set(owner_name);
    }
}
