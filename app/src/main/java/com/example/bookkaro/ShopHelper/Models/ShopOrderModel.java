package com.example.bookkaro.ShopHelper.Models;

import com.example.bookkaro.AddressHelper.AddressModel;

import java.util.ArrayList;

public class ShopOrderModel {
    String userPhoneNumber;
    int orderTotal;
    String vendorPhoneNumber;
    String vendorName;
    AddressModel userAddress;
    int orderStatus;
    int orderType;
    ArrayList<ShopOrderItemModel> items;

    public ShopOrderModel() {
    }

    public ShopOrderModel(String userPhoneNumber, int orderTotal, String vendorPhoneNumber, String vendorName, AddressModel userAddress, int orderStatus, int orderType, ArrayList<ShopOrderItemModel> items) {
        this.userPhoneNumber = userPhoneNumber;
        this.orderTotal = orderTotal;
        this.vendorPhoneNumber = vendorPhoneNumber;
        this.vendorName = vendorName;
        this.userAddress = userAddress;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.items = items;
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

    public AddressModel getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(AddressModel userAddress) {
        this.userAddress = userAddress;
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

    public ArrayList<ShopOrderItemModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShopOrderItemModel> items) {
        this.items = items;
    }
}
