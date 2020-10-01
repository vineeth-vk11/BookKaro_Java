package com.example.bookkaro.AddressHelper;

public class AddressModel {
    Double latitude, longitude;
    String address, pincode, apartmentNumber, landMark, addressType;

    public AddressModel() {
    }

    public AddressModel(Double latitude, Double longitude, String address, String pincode, String apartmentNumber, String landMark, String addressType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.pincode = pincode;
        this.apartmentNumber = apartmentNumber;
        this.landMark = landMark;
        this.addressType = addressType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
