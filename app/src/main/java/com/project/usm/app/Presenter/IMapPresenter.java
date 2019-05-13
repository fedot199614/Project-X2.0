package com.project.usm.app.Presenter;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;

public interface IMapPresenter {
    void init(Activity activity);
    void destroyLoadingD();
}
