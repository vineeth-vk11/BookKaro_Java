package com.example.bookkaro.HouseholdHelper.Models;

import com.google.firebase.storage.StorageReference;

public class HouseholdServiceModel {

    String serviceName;
    int servicePrice;
    int serviceId;

    public HouseholdServiceModel() {
    }

    public HouseholdServiceModel(String serviceName, int servicePrice, int serviceId) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
