package com.example.bookkaro.DeliveryHelper.Models;

public class DeliveryItemModel {
    String itemName;
    String itemQuantity;
    String itemKey;

    public DeliveryItemModel() {
    }

    public DeliveryItemModel(String itemName, String itemQuantity, String itemKey) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemKey = itemKey;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }
}
