package com.project.usm.app.Presenter;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.project.usm.app.R;
import com.project.usm.app.Tools.NavItems;
import com.project.usm.app.View.MapView;

public class MapPresenter implements IMapPresenter {
   private MapView mapView;

   public MapPresenter(MapView mapView){
       this.mapView = mapView;
   }


    @Override
    public void init(Activity activity) {
        NavItems.getNavMenu(activity).getItem(3).setChecked(true);
        mapView.showLoadingDialog();

    }

    @Override
    public void destroyLoadingD() {
        mapView.destroyLoadingDialog();
    }

    @Override
    public void permissionCheck() {
        mapView.permissionCheck();

    }
}
