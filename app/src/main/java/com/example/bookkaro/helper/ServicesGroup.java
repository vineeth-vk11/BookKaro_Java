package com.example.bookkaro.helper;

import java.util.ArrayList;

public class ServicesGroup {
    private String headerTitle;
    private ArrayList<ServicesData> listItem;

    public ServicesGroup() {
    }

    public ServicesGroup(String headerTitle, ArrayList<ServicesData> listItem) {
        this.headerTitle = headerTitle;
        this.listItem = listItem;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ServicesData> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<ServicesData> listItem) {
        this.listItem = listItem;
    }
}
