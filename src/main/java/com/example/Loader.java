package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Loader {
    private StringProperty loader_name;

    public Loader(String loader_name) {
        this.loader_name = new SimpleStringProperty(loader_name);
    }

    public StringProperty loaderProperty() {
        return loader_name;
    }

    public String getLoader() {
        return loader_name.get();
    }

    public void setLoader(String loader_name) {
        this.loader_name.set(loader_name);
    }
}
