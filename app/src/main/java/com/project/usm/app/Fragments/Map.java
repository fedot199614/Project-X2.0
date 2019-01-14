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
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.usm.app.Presenter.MapPresenter;
import com.project.usm.app.R;
import com.project.usm.app.View.MapView;

import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;

public class Map extends Fragment implements OnMapReadyCallback, LocationListener, MapView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 0;
    private GoogleMap mMap;
    private Location myLocation;
    private Thread dialogThread;
    private ProgressDialog myProgress;
    private SupportMapFragment mapFragment;
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

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        myProgress = new ProgressDialog(getContext());
        mapPresenter =  new MapPresenter(this);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapPresenter.init(getActivity());
        mapPresenter.destroyLoadingD();
        mapPresenter.permissionCheck();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void showLoadingDialog() {
               myProgress.setTitle(getString(R.string.mapL));
               myProgress.setMessage(getString(R.string.mapW));
               myProgress.setCancelable(true);
               myProgress.show();
    }

    @Override
    public void permissionCheck() {
        boolean exp = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        if (exp) {

                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        };
                ActivityCompat.requestPermissions(getActivity(), permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
            getActivity().onBackPressed();
            return;
        }else{
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);
            getMyLocation();
            showMyLocation();
        }
    }

    @Override
    public void initTabBar(){
        TabLayout tabBar = (TabLayout) getActivity().findViewById(R.id.tabLayout);
        tabBar.setVisibility(View.GONE);
    }

    @Override
    public void destroyLoadingDialog() {
        myProgress.dismiss();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getMyLocation() {

            // Get location from GPS if it's available
            LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

            myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Location wasn't found, check the next most accurate place for the current location
            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                // Finds a provider that matches the criteria
                String provider = lm.getBestProvider(criteria, true);
                // Use the provider to get the last known location
                myLocation = lm.getLastKnownLocation(provider);
            }



    }

    @Override
    public void showMyLocation() {
        if (myLocation != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 13));
            MarkerOptions marker = new MarkerOptions().position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).title("My Location");
            mMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
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
