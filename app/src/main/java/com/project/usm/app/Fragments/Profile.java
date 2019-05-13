package com.project.usm.app.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.Marker;
import com.project.usm.app.AOP.Annotations.CheckGalleryPermissions;
import com.project.usm.app.AOP.Annotations.InitTabBar;
import com.project.usm.app.AOP.Annotations.ListItemSelected;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Presenter.ProfilePresenter;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.Tools.FontAwesome;
import com.project.usm.app.Tools.NavigationViewManager;
import com.project.usm.app.Tools.RVAdapterProfileInfo;
import com.project.usm.app.View.ProfileView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private ProfileInfo profInfo;
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

public void setData(ProfileInfo profileInfo){
    CircleImageView profImg = (CircleImageView) getActivity().findViewById(R.id.profile_image);
    CircleImageView navProfImg = NavigationViewManager.getNewInstance().getNavProfImg();
    TextView name = (TextView) getActivity().findViewById(R.id.profile_name_lastname);
    TextView profile_group = (TextView) getActivity().findViewById(R.id.profile_group);
    TextView faculty = (TextView) getActivity().findViewById(R.id.profile_faculty);
    TextView navName = NavigationViewManager.getNewInstance().getName();

    name.setText(profileInfo.getProfileResponseResource().getFirstName()+" "+profileInfo.getProfileResponseResource().getLastName());
    navName.setText(profileInfo.getProfileResponseResource().getFirstName()+" "+profileInfo.getProfileResponseResource().getLastName());
    profile_group.setText(profileInfo.getProfileResponseResource().getGroupId());
    faculty.setText(profileInfo.getProfileResponseResource().getFaculty());


    Picasso.get().load(profileInfo.getProfileResponseResource().getProfileImageUrl()).into(profImg);
    Picasso.get().load(profileInfo.getProfileResponseResource().getProfileImageUrl()).into(navProfImg);
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
        SplashScreen.getProfileInfo().updateData();
        profInfo  = SplashScreen.getProfileInfo();
        setData(profInfo);
        RVAdapterProfileInfo adapter = new RVAdapterProfileInfo(profInfo,getActivity());
        rv.setAdapter(adapter);

        TextView textGroup = (TextView) getActivity().findViewById(R.id.profile_group);
        textGroup.setOnClickListener(click->{
            Toast.makeText(getActivity(),getString(R.string.loading),Toast.LENGTH_SHORT).show();
            GroupMembers sn = new GroupMembers();
            beginTransaction(getFragmentManager(),sn,"membersGroup",textGroup.getText().toString());
        });

        TextView textMarks = (TextView) getActivity().findViewById(R.id.my_marks);
        textMarks.setOnClickListener(click->{
            Toast.makeText(getActivity(),getString(R.string.loading),Toast.LENGTH_SHORT).show();
            Marks sn = new Marks();
            beginTransaction(getFragmentManager(),sn,"marks",textGroup.getText().toString());
        });


    }

    public void setAnimFade(Fragment fragment, Activity activity) {

        Transition changeTransform = TransitionInflater.from(activity).inflateTransition(R.transition.change_img_transform);
        fragment.setSharedElementReturnTransition(changeTransform);
        fragment.setSharedElementEnterTransition(changeTransform);

        fragment.setExitTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.explode));
        fragment.setEnterTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.explode));
    }

    public void beginTransaction(FragmentManager frManager, Fragment nextFragment, String backStackTag,String idGroup) {
        FragmentTransaction transaction = frManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("grId", idGroup);
        nextFragment.setArguments(bundle);
        transaction.addToBackStack(backStackTag);
        transaction.replace(R.id.mainFrame,nextFragment).commit();
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
        navProfImg = (CircleImageView) getActivity().findViewById(R.id.member_avatar);
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
            BaseQuery.updateData(imageBitmap,getActivity(),profInfo);

        }else if(requestCode == GALLERY_REQUEST_CODE){

            if(resultCode == RESULT_OK){

                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profImg.setImageBitmap(bitmap);
                navProfImg.setImageBitmap(bitmap);

                BaseQuery.updateData(bitmap,getActivity(),profInfo);
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
