package com.project.usm.app.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;

import com.project.usm.app.AOP.Annotations.InitTabBar;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Presenter.Auth_Presenter;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.Tools.KeyBoardManager;
import com.project.usm.app.Tools.RVAdapterGroupMembers;
import com.project.usm.app.Tools.RVAdapterProfileInfo;
import com.project.usm.app.Tools.RecyclerItemClickListener;

import java.util.List;

public class GroupMembers extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GroupMembers() {
        // Required empty public constructor
    }

    public static GroupMembers newInstance(String param1, String param2) {
        GroupMembers fragment = new GroupMembers();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.group_members, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @InitTabBar(check = InitTabBar.Check.GONE)
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.rv_group_members);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        BaseQuery.membersGroupQuery(rv,getActivity(),SplashScreen.getGsonParser(),SplashScreen.getProfileInfo().getProfileResponseResource().getGroupId());





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
