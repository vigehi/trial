package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Weight {
    private StringProperty weight_value;

    public Weight(String weight_value) {
        this.weight_value = new SimpleStringProperty(weight_value);
    }

    public StringProperty weightProperty() {
        return weight_value;
    }

    public String getWeight() {
        return weight_value.get();
    }

    public void setWeight(String weight_value) {
        this.weight_value.set(weight_value);
    }
}
