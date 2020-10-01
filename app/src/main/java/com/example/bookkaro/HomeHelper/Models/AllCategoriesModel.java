package com.example.bookkaro.HomeHelper.Models;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllCategoriesModel {
    String categoryName;
    int categoryId;
    ArrayList<ServiceModel> serviceModelArrayList;

    public AllCategoriesModel() {
    }

    public AllCategoriesModel(String categoryName, int categoryId, ArrayList<ServiceModel> serviceModelArrayList) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.serviceModelArrayList = serviceModelArrayList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public ArrayList<ServiceModel> getServiceModelArrayList() {
        return serviceModelArrayList;
    }

    public void setServiceModelArrayList(ArrayList<ServiceModel> serviceModelArrayList) {
        this.serviceModelArrayList = serviceModelArrayList;
    }
}
