package com.project.usm.app.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;

import com.project.usm.app.Model.News;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.Tools.NavItems;
import com.project.usm.app.View.Home_View;

import java.util.ArrayList;
import java.util.List;

public class HomeNews implements IHomeNews {
    Home_View home_view;
    public HomeNews(Home_View home) {

        this.home_view = home;
    }


    @Override
    public void Init() {
        List<News> newsList = new ArrayList<>();
        newsList.addAll(SplashScreen.getNewsList());
        RecyclerView rv =  home_view.InitRV();
        rv.setAdapter(home_view.createRVAdapter(newsList));
        home_view.itemClickListener(rv,newsList);
        home_view.onRefresh();


    }

    @Override
    public void setAnimFade(Fragment fragment, Activity activity) {

        Transition changeTransform = TransitionInflater.from(activity).
                inflateTransition(R.transition.change_img_transform);
        fragment.setSharedElementReturnTransition(changeTransform);
        fragment.setSharedElementEnterTransition(changeTransform);

        //fragment.setExitTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.explode));
        fragment.setEnterTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.explode));
    }


    @Override
    public void beginTransaction(FragmentManager frManager, Fragment nextFragment, String backStackTag, int position, View view, List<News> model) {
        FragmentTransaction transaction = frManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(view.getResources().getString(R.string.title), model.get(position).getTitle());
        bundle.putString(view.getResources().getString(R.string.news), model.get(position).getFull_news());
        bundle.putStringArrayList(view.getResources().getString(R.string.imgUrl),(ArrayList<String>)model.get(position).getImgURL());
        nextFragment.setArguments(bundle);
        transaction.addToBackStack(backStackTag);
        //transaction.addSharedElement(view.findViewById(R.id.cv), view.findViewById(R.id.cv).getTransitionName());
        //transaction.addSharedElement(view.findViewById(R.id.title_model), view.findViewById(R.id.title_model).getTransitionName());
        //transaction.addSharedElement(view.findViewById(R.id.news_model), view.findViewById(R.id.news_model).getTransitionName());
        transaction.addSharedElement(view.findViewById(R.id.imagePrev),view.findViewById(R.id.imagePrev).getTransitionName());
        transaction.replace(R.id.mainFrame,nextFragment).commit();



    }

    @Override
    public void refreshAnim() {
        home_view.repeatQuery();
        home_view.runLayoutAnimation();
       // home_view.repeatQuery();
    }
}
