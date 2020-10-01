package com.example.bookkaro.ShopHelper.Models;

import java.util.ArrayList;

public class ShopCategoryModel {
    String categoryName;
    ArrayList<ShopItemModel> shopItemModelArrayList;

    public ShopCategoryModel() {
    }

    public ShopCategoryModel(String categoryName, ArrayList<ShopItemModel> shopItemModelArrayList) {
        this.categoryName = categoryName;
        this.shopItemModelArrayList = shopItemModelArrayList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<ShopItemModel> getShopItemModelArrayList() {
        return shopItemModelArrayList;
    }

    public void setShopItemModelArrayList(ArrayList<ShopItemModel> shopItemModelArrayList) {
        this.shopItemModelArrayList = shopItemModelArrayList;
    }


}
