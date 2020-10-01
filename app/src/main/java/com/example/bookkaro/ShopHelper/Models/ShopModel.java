package com.example.bookkaro.ShopHelper.Models;

import android.location.Location;

import com.example.bookkaro.HomeHelper.Models.LocationModel;

import java.util.ArrayList;

public class ShopModel {

    String shopName;
    String shopIcon;
    String shopNumber;
    int shopType;
    ArrayList<LocationModel> locationModelArrayList;
    String shopAddress;

    public ShopModel() {
    }

    public ShopModel(String shopName, String shopIcon, String shopNumber, int shopType, ArrayList<LocationModel> locationModelArrayList, String shopAddress) {
        this.shopName = shopName;
        this.shopIcon = shopIcon;
        this.shopNumber = shopNumber;
        this.shopType = shopType;
        this.locationModelArrayList = locationModelArrayList;
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public ArrayList<LocationModel> getLocationModelArrayList() {
        return locationModelArrayList;
    }

    public void setLocationModelArrayList(ArrayList<LocationModel> locationModelArrayList) {
        this.locationModelArrayList = locationModelArrayList;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
