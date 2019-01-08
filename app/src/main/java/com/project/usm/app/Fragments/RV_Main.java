package com.project.usm.app.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import  com.project.usm.app.Model.News;
import com.project.usm.app.Presenter.HomeNews;
import  com.project.usm.app.R;
import com.project.usm.app.Tools.NavItems;
import  com.project.usm.app.Tools.RVAdapter;
import  com.project.usm.app.Tools.RecyclerItemClickListener;
import com.project.usm.app.View.Home_View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RV_Main extends Fragment implements Home_View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  HomeNews homePresenter = new HomeNews(this);

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RV_Main() {

    }



    public static RV_Main newInstance(String param1, String param2) {
        RV_Main fragment = new RV_Main();
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

        return inflater.inflate(R.layout.fragment_rv__main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        homePresenter.Init(getActivity());
        homePresenter.setAnimFade(this,getActivity());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public RecyclerView InitRV() {
        RecyclerView rv = (RecyclerView)getView().findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        return rv;
    }

    @Override
    public RVAdapter createRVAdapter(List<News> newsList) {
        RVAdapter adapter = new RVAdapter(newsList);
        return adapter;
    }

    @Override
    public void itemClickListener(RecyclerView rv, List<News> model) {
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SharedNews sn = new SharedNews();
                homePresenter.setAnimFade(sn,getActivity());
                homePresenter.beginTransaction(getFragmentManager(),sn,getString(R.string.sharedNews),position,view,model);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
