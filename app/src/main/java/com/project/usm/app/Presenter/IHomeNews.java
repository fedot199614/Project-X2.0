package com.project.usm.app.Presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.project.usm.app.Model.News;

import java.util.List;

public interface IHomeNews {
    void Init(Activity activity);
    void setAnimFade(Fragment fragment, Activity activity);
    void beginTransaction(FragmentManager frManager, Fragment nextFragment, String backStackTag, int position, View view, List<News> model);
    void refreshAnim();
}
