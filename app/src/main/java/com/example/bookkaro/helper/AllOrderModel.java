package com.example.bookkaro.helper;

public class AllOrderModel {

    String serviceName;
    String serviceIcon;
    int serviceTotal;
    int orderType;
    String key;
    String dateTime;

    public AllOrderModel() {
    }

    public AllOrderModel(String serviceName, String serviceIcon, int serviceTotal, int orderType, String key, String dateTime) {
        this.serviceName = serviceName;
        this.serviceIcon = serviceIcon;
        this.serviceTotal = serviceTotal;
        this.orderType = orderType;
        this.key = key;
        this.dateTime = dateTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public int getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(int serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
