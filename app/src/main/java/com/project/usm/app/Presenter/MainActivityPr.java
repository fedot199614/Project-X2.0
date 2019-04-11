package com.project.usm.app.Presenter;

import com.project.usm.app.View.MainActivityView;

public class MainActivityPr {
    private MainActivityView main;

    public MainActivityPr(MainActivityView main){
        this.main = main;
    }

    public void init(){
        main.initNavigViewIcons();
        main.initHomePage();
    }
}
