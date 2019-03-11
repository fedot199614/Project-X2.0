package com.project.usm.app.Fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;



import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;


import com.project.usm.app.View.Home_View;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SharedNews extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    private int mCurIndex;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SharedNews() {

    }


    public static SharedNews newInstance(String param1, String param2) {
        SharedNews fragment = new SharedNews();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.news_model_shared_main, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        TabLayout tabBar = (TabLayout) getActivity().findViewById(R.id.tabLayout);
        tabBar.setVisibility(View.GONE);
        TextView title = (TextView) getView().findViewById(R.id.title_model_sh);
        TextView newsFull = (TextView) getView().findViewById(R.id.news_model_sh);
        Bundle bundle = getArguments();
        title.setText(bundle.getString(getString(R.string.title)));
        newsFull.setText(bundle.getString(getString(R.string.news)));
        //-------------------------------------------------------------
        List<String> urls = bundle.getStringArrayList(getString(R.string.imgUrl));
        ImageView sharedImg = (ImageView) getActivity().findViewById(R.id.sharedImg);
        if(!urls.isEmpty()) {
            Picasso.get().load(urls.get(0)).into(sharedImg);
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

