package com.example.bookkaro.DeliveryHelper.Models;

import java.util.ArrayList;

public class SendPackageModel {
    String deliveryAddress;
    String pickedupAddress;
    ArrayList<DeliveryItemModel> orderItems;
    String userPhoneNumber;
    String instructions;
    int orderStatus;

    public SendPackageModel() {
    }

    public SendPackageModel(String deliveryAddress, String pickedupAddress, ArrayList<DeliveryItemModel> orderItems, String userPhoneNumber, String instructions, int orderStatus) {
        this.deliveryAddress = deliveryAddress;
        this.pickedupAddress = pickedupAddress;
        this.orderItems = orderItems;
        this.userPhoneNumber = userPhoneNumber;
        this.instructions = instructions;
        this.orderStatus = orderStatus;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPickedupAddress() {
        return pickedupAddress;
    }

    public void setPickedupAddress(String pickedupAddress) {
        this.pickedupAddress = pickedupAddress;
    }

    public ArrayList<DeliveryItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<DeliveryItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
