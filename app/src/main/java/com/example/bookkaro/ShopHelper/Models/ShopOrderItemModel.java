package com.example.bookkaro.ShopHelper.Models;

public class ShopOrderItemModel {
    String itemName;
    String itemPrice;
    String itemId;
    int itemQuantity;
    String itemIcon;
    String itemDesc;

    public ShopOrderItemModel() {
    }

    public ShopOrderItemModel(String itemName, String itemPrice, String itemId, int itemQuantity, String itemIcon, String itemDesc) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemIcon = itemIcon;
        this.itemDesc = itemDesc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
