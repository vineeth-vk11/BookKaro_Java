package com.example.bookkaro.HomeHelper.Models;

public class CategoryModel {
    String categoryName;
    String categoryIcon;
    int categoryId;

    public CategoryModel() {
    }

    public CategoryModel(String categoryName, String categoryIcon, int categoryId) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
