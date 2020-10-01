package com.example.bookkaro.ShopHelper.Models;

public class ShopItemModel {
    String itemName;
    String itemPrice;
    String itemIcon;
    String itemDesc;
    String itemId;
    String itemStatus;

    public ShopItemModel() {
    }

    public ShopItemModel(String itemName, String itemPrice, String itemIcon, String itemDesc, String itemId) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemIcon = itemIcon;
        this.itemDesc = itemDesc;
        this.itemId = itemId;
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

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
}
