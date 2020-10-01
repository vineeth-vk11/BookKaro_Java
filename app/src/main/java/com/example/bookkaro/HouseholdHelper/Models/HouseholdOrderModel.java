package com.example.bookkaro.HouseholdHelper.Models;

import com.example.bookkaro.AddressHelper.AddressModel;

import java.util.ArrayList;

public class HouseholdOrderModel {
    String userPhoneNumber;
    int orderTotal;
    String vendorPhoneNumber;
    String vendorName;
    String dateOfService;
    String timeOfService;
    int orderStatus;
    int orderType;
    AddressModel userAddress;
    String deliveryAddress;
    String pincode;
    ArrayList<HouseholdCartModel> orderItems;

    public HouseholdOrderModel() {

    }

    public HouseholdOrderModel(String userPhoneNumber, int orderTotal, String vendorPhoneNumber, String vendorName, String dateOfService, String timeOfService, int orderStatus, int orderType, AddressModel userAddress, String deliveryAddress, ArrayList<HouseholdCartModel> orderItems) {
        this.userPhoneNumber = userPhoneNumber;
        this.orderTotal = orderTotal;
        this.vendorPhoneNumber = vendorPhoneNumber;
        this.vendorName = vendorName;
        this.dateOfService = dateOfService;
        this.timeOfService = timeOfService;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.userAddress = userAddress;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = orderItems;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getVendorPhoneNumber() {
        return vendorPhoneNumber;
    }

    public void setVendorPhoneNumber(String vendorPhoneNumber) {
        this.vendorPhoneNumber = vendorPhoneNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(String dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getTimeOfService() {
        return timeOfService;
    }

    public void setTimeOfService(String timeOfService) {
        this.timeOfService = timeOfService;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public AddressModel getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(AddressModel userAddress) {
        this.userAddress = userAddress;
    }

    public ArrayList<HouseholdCartModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<HouseholdCartModel> orderItems) {
        this.orderItems = orderItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
