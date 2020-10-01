package com.example.bookkaro.HouseholdHelper.Models;

public class HouseholdCartModel {
    String serviceName;
    int servicePrice;
    int serviceId;
    int serviceQuantity;

    public HouseholdCartModel() {
    }

    public HouseholdCartModel(String serviceName, int servicePrice, int serviceId, int serviceQuantity) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceId = serviceId;
        this.serviceQuantity = serviceQuantity;
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

    public int getServiceQuantity() {
        return serviceQuantity;
    }

    public void setServiceQuantity(int serviceQuantity) {
        this.serviceQuantity = serviceQuantity;
    }
}
