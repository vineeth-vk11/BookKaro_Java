package com.example.bookkaro.helper.home;

public class Category {
    private String categoryName;
    private String categoryIcon;

    public Category() {
    }

    public Category(String categoryName, String categoryIcon) {
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
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
}
