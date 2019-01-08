package com.project.usm.app.View;

import android.app.ProgressDialog;

import com.google.android.gms.maps.GoogleMap;

public interface MapView {
    void showLoadingDialog();
    void permissionCheck();
    void destroyLoadingDialog();
}
