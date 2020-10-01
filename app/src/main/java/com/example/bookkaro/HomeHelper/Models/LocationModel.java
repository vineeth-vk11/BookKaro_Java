package com.example.bookkaro.HomeHelper.Models;

public class LocationModel {
    int pincode;

    public LocationModel() {
    }

    public LocationModel(int pincode) {
        this.pincode = pincode;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
