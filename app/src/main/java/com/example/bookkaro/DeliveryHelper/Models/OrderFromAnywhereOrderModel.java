package com.example.bookkaro.DeliveryHelper.Models;

import java.util.ArrayList;

public class OrderFromAnywhereOrderModel {

    String itemsCategory;
    ArrayList<DeliveryItemModel> orderItems;
    String deliveryAddress;
    String pickupAddress;
    String estimatedTotal;
    String instructions;
    String userPhoneNumber;
    int orderStatus;

    public OrderFromAnywhereOrderModel() {
    }

    public OrderFromAnywhereOrderModel(String itemsCategory, ArrayList<DeliveryItemModel> orderItems, String deliveryAddress, String pickupAddress, String estimatedTotal, String instructions, String userPhoneNumber, int orderStatus) {
        this.itemsCategory = itemsCategory;
        this.orderItems = orderItems;
        this.deliveryAddress = deliveryAddress;
        this.pickupAddress = pickupAddress;
        this.estimatedTotal = estimatedTotal;
        this.instructions = instructions;
        this.userPhoneNumber = userPhoneNumber;
        this.orderStatus = orderStatus;
    }

    public String getItemsCategory() {
        return itemsCategory;
    }

    public void setItemsCategory(String itemsCategory) {
        this.itemsCategory = itemsCategory;
    }

    public ArrayList<DeliveryItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<DeliveryItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(String estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
