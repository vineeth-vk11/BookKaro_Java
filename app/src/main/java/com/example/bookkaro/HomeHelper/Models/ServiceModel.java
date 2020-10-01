package com.example.bookkaro.HomeHelper.Models;

import java.util.ArrayList;

public class ServiceModel {
    String serviceName;
    int serviceCategoryId;
    String serviceIcon;
    int serviceId;
    ArrayList<LocationModel> locations;

    public ServiceModel() {
    }

    public ServiceModel(String serviceName, int serviceCategoryId, String serviceIcon, int serviceId, ArrayList<LocationModel> locations) {
        this.serviceName = serviceName;
        this.serviceCategoryId = serviceCategoryId;
        this.serviceIcon = serviceIcon;
        this.serviceId = serviceId;
        this.locations = locations;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<LocationModel> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<LocationModel> locations) {
        this.locations = locations;
    }
}
