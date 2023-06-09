package com.example;

public class DataEntry {
    private String carName;
    private String ownerName;
    private String loaderName;
    private String weightValue;

    public DataEntry(String carName, String ownerName, String loaderName, String weightValue) {
        this.carName = carName;
        this.ownerName = ownerName;
        this.loaderName = loaderName;
        this.weightValue = weightValue;
    }

    public String getCarName() {
        return carName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getLoaderName() {
        return loaderName;
    }

    public String getWeightValue() {
        return weightValue;
    }
}
