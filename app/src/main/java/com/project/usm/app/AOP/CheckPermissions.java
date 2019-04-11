package com.project.usm.app.AOP;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.project.usm.app.Fragments.Map;
import com.project.usm.app.Fragments.Profile;
import com.project.usm.app.R;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CheckPermissions {
    private static final int TAG_CODE_PERMISSION_LOCATION = 0;
    private static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 0;
    private final int PERMISSION_REQUEST_CODE = 3;
    @After("@annotation(com.project.usm.app.AOP.Annotations.CheckBasePermissions)")
    public void checkBase(JoinPoint joinPoint) {
            if (joinPoint.getTarget() != null) {
                final Context context = (Context) joinPoint.getTarget();
                final Activity activity = (Activity) joinPoint.getTarget();
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            },
                            TAG_CODE_PERMISSION_LOCATION);
                }

            }
    }


    @After("@annotation(com.project.usm.app.AOP.Annotations.CheckMapPermissions)")
    public void checkMap(JoinPoint joinPoint) {

        if (joinPoint.getTarget() != null) {

            final Map fragment = (Map) joinPoint.getTarget();

            boolean exp = ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
            if (exp) {

                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                };
                ActivityCompat.requestPermissions(fragment.getActivity(), permissions,
                         REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
                fragment.getMapPresenter().destroyLoadingD();
                fragment.getActivity().onBackPressed();

            } else {
                fragment.getMMap().getUiSettings().setZoomControlsEnabled(true);
                fragment.getMMap().getUiSettings().setMyLocationButtonEnabled(true);
                fragment.getMMap().setMyLocationEnabled(true);
                fragment.getMFusedLocationClient().getLastLocation().addOnSuccessListener(fragment);


            }

        }
    }


    @Around("@annotation(com.project.usm.app.AOP.Annotations.CheckGalleryPermissions)")
    public void checkGallery(JoinPoint joinPoint) {

        final Fragment fragment = (Fragment) joinPoint.getTarget();
        final Profile profile = (Profile) joinPoint.getTarget();
        final Context context = (Context) joinPoint.getTarget();
        final Activity activity = (Activity) joinPoint.getTarget();
        int permissionCheckCam = ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.CAMERA);
        int permissionCheckWrite = ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckRead = ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheckCam != PackageManager.PERMISSION_GRANTED || permissionCheckWrite != PackageManager.PERMISSION_GRANTED || permissionCheckRead != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(fragment.getActivity(),new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);

        }else {


            String[] list = {fragment.getString(R.string.newPhoto), fragment.getString(R.string.onGallery)};
            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
            builder.setTitle(fragment.getString(R.string.edit))
                    .setItems(list, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which) {
                                case 0:
                                    profile.cameraOpen();
                                    break;
                                case 1:
                                    profile.galleryOpen();
                                    break;
                            }
                        }
                    });

            builder.create().show();

        }

    }
}
