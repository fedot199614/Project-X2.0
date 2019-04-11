package com.project.usm.app.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.pm.PackageManager;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;

import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.usm.app.AOP.Annotations.CheckMapPermissions;
import com.project.usm.app.AOP.Annotations.InitTabBar;

import com.project.usm.app.AOP.Annotations.ListItemSelected;
import com.project.usm.app.Presenter.MapPresenter;
import com.project.usm.app.R;
import com.project.usm.app.View.MapView;

import java.util.List;
import java.util.Objects;

import lombok.Getter;

import static android.content.Context.LOCATION_SERVICE;


@Getter
public class Map extends Fragment implements MapView, OnMapReadyCallback,OnSuccessListener<Location> {
    private FusedLocationProviderClient mFusedLocationClient;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 0;
    private GoogleMap mMap;
    private Marker marker;
    private ProgressDialog myProgress;
    private MapPresenter mapPresenter;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Map() {
        // Required empty public constructor
    }


    public static Map newInstance(String param1, String param2) {
        Map fragment = new Map();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        myProgress = new ProgressDialog(getContext());
        mapPresenter = new MapPresenter(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @ListItemSelected(item = ListItemSelected.Item.MAP)
    @InitTabBar(check = InitTabBar.Check.GONE)
    @CheckMapPermissions
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mapPresenter.init(getActivity());

    }


    @Override
    public void showLoadingDialog() {
        myProgress.setTitle(getString(R.string.mapL));
        myProgress.setMessage(getString(R.string.mapW));
        myProgress.setCancelable(true);
        myProgress.show();
    }

    @Override
    public void destroyLoadingDialog() {
        myProgress.dismiss();
    }

    @Override
    public void onSuccess(Location location) {
        mapPresenter.destroyLoadingD();
        if(marker!=null){
            marker.remove();
        }
        if (location != null) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                            MarkerOptions markerOp = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My Location");
                            marker = mMap.addMarker(markerOp);
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(17)                   // Sets the zoom
                                    .bearing(90)                // Sets the orientation of the camera to east
                                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                    .build();                   // Creates a CameraPosition from the builder
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
