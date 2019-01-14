package com.project.usm.app.View;

import android.app.ProgressDialog;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;

public interface MapView {
    void showLoadingDialog();
    void permissionCheck();
    void destroyLoadingDialog();
    void getMyLocation();
    void showMyLocation();
    void initTabBar();
}
