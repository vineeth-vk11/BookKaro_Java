package com.example.bookkaro.helper;

import java.util.ArrayList;
import java.util.List;

public class ServicesGroup {

    public static final long DELIVERY_SERVICE = 100L;
    public static final long HOUSEHOLD_SERVICE = 101L;
    public static final long SHOP_SERVICE = 102L;

    private String headerTitle;
    private List<ServicesData> listItem;

    public ServicesGroup(String headerTitle, List<ServicesData> listItem) {
        this.headerTitle = headerTitle;
        this.listItem = listItem;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public List<ServicesData> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<ServicesData> listItem) {
        this.listItem = listItem;
    }
}
