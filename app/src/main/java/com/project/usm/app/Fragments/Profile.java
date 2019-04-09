package com.project.usm.app.Fragments;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.mxn.soul.flowingdrawer_core.BuildConfig;
import com.project.usm.app.Presenter.ProfilePresenter;
import com.project.usm.app.R;
import com.project.usm.app.Tools.FontAwesome;
import com.project.usm.app.Tools.NavItems;
import com.project.usm.app.View.ProfileView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment implements ProfileView {
    private ProfilePresenter profilePresenter;
    private FontAwesome editProfileImg;
    private File file;
    private Uri uri;
    private final int PERMISSION_REQUEST_CODE = 3;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CircleImageView profImg,navProfImg;
    private final int CAMERA_REQUEST_CODE = 0;
    private final int GALLERY_REQUEST_CODE = 1;

    private String mParam1;
    private String mParam2;

    private SharedNews.OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profilePresenter = new ProfilePresenter(this);
        profilePresenter.initStartState();
    }

    @Override
    public void cameraOpen() {
       Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE");
       startActivityForResult(camIntent,CAMERA_REQUEST_CODE);
    }

    @Override
    public void galleryOpen() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , GALLERY_REQUEST_CODE);
    }


    @Override
    public void initTabBar(){
        TabLayout tabBar = (TabLayout) getActivity().findViewById(R.id.tabLayout);
        tabBar.setVisibility(View.GONE);
    }
    @Override
    public void initBaseOption() {
        NavItems.getNavMenu(getActivity()).getItem(1).setChecked(true);
        profImg = (CircleImageView) getActivity().findViewById(R.id.profile_image);
        navProfImg = (CircleImageView) getActivity().findViewById(R.id.nav_prof_img);
        editProfileImg = (FontAwesome) getActivity().findViewById(R.id.addProfImg);
    }

    @Override
    public void initSettingsListener(){
        editProfileImg.setOnClickListener(click->{
            int permissionCheckCam = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            int permissionCheckWrite = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionCheckRead = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if(permissionCheckCam != PackageManager.PERMISSION_GRANTED || permissionCheckWrite != PackageManager.PERMISSION_GRANTED || permissionCheckRead != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(getActivity(),new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);

            }else {


                String[] list = {getString(R.string.newPhoto), getString(R.string.onGallery)};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.edit))
                        .setItems(list, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which) {
                                    case 0:
                                        cameraOpen();
                                        break;
                                    case 1:
                                        galleryOpen();
                                        break;
                                }
                            }
                        });

                builder.create().show();

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            profImg.setImageBitmap(imageBitmap);
            navProfImg.setImageBitmap(imageBitmap);
        }else if(requestCode == GALLERY_REQUEST_CODE){

            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                profImg.setImageURI(selectedImage);
                navProfImg.setImageURI(selectedImage);
            }

//            if (data != null && data.getExtras() != null) {
//                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//                profImg.setImageBitmap(imageBitmap);
//            }

        }
    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
