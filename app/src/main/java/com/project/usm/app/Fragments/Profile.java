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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mxn.soul.flowingdrawer_core.BuildConfig;
import com.project.usm.app.AOP.Annotations.CheckGalleryPermissions;
import com.project.usm.app.AOP.Annotations.InitTabBar;
import com.project.usm.app.AOP.Annotations.ListItemSelected;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Presenter.ProfilePresenter;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.Tools.FontAwesome;
import com.project.usm.app.Tools.NavItems;
import com.project.usm.app.Tools.RVAdapterProfileInfo;
import com.project.usm.app.Tools.RVAdapterSchedule;
import com.project.usm.app.View.ProfileView;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
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




    @ListItemSelected(item = ListItemSelected.Item.PROFILE)
    @InitTabBar(check = InitTabBar.Check.GONE)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profilePresenter = new ProfilePresenter(this);
        profilePresenter.initStartState();

        RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.rv_profile_info);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        ProfileInfo profInfo  = BaseQuery.profileQuery(getActivity());
        RVAdapterProfileInfo adapter = new RVAdapterProfileInfo(profInfo);
        rv.setAdapter(adapter);


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
    public void initBaseOption() {
        profImg = (CircleImageView) getActivity().findViewById(R.id.profile_image);
        navProfImg = (CircleImageView) getActivity().findViewById(R.id.nav_prof_img);
        editProfileImg = (FontAwesome) getActivity().findViewById(R.id.addProfImg);
    }

    @CheckGalleryPermissions
    public void checkPermissions(){

    }

    @Override
    public void initSettingsListener(){
        editProfileImg.setOnClickListener(click->{
            checkPermissions();
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
