package com.example.bookkaro.Interface;

import com.example.bookkaro.helper.ServicesGroup;

import java.util.List;

public interface iFirebaseLoadListener {
    void onFirebaseLoadSuccess(List<ServicesGroup> servicesGroupList);
    void onFirebaseLoadFailed(String message);
}
