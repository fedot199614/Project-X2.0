package com.project.usm.app.Tools;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.project.usm.app.R;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NavigationViewManager {
    private static final NavigationViewManager navigationViewManager = new NavigationViewManager();

    private Context context;
    private NavigationView navigationView;
    private MenuItem home;
    private MenuItem map;
    private MenuItem logIn;
    private MenuItem logOut;
    private MenuItem nav_schedule;
    private MenuItem profile;

    private NavigationViewManager(){

    }

    public static NavigationViewManager getNewInstance(){
        return navigationViewManager;
    }

    public void basicInit(){
        this.home = navigationView.getMenu().findItem(R.id.nav_home);
        this.map = navigationView.getMenu().findItem(R.id.map);
        this.logIn = navigationView.getMenu().findItem(R.id.nav_logIn);
        this.logOut = navigationView.getMenu().findItem(R.id.nav_Out);
        this.nav_schedule = navigationView.getMenu().findItem(R.id.nav_schedule);
        this.profile = navigationView.getMenu().findItem(R.id.profile);


    }

    public void initIcons() {
        home.setIcon(new IconDrawable(context, FontAwesomeIcons.fa_home).colorRes(R.color.secondText));
        map.setIcon(new IconDrawable(context, FontAwesomeIcons.fa_map_marker).colorRes(R.color.secondText));
        logIn.setIcon(new IconDrawable(context, FontAwesomeIcons.fa_sign_in).colorRes(R.color.secondText));
        nav_schedule.setIcon(new IconDrawable(context, FontAwesomeIcons.fa_clipboard).colorRes(R.color.secondText));
        profile.setIcon(new IconDrawable(context, FontAwesomeIcons.fa_user).colorRes(R.color.secondText));
        logOut.setIcon(new IconDrawable(context, FontAwesomeIcons.fa_sign_out).colorRes(R.color.secondText));
    }

    public void sign_inViewVisibility(){
        home.setVisible(true);
        map.setVisible(true);
        nav_schedule.setVisible(false);
        profile.setVisible(false);
        logOut.setVisible(false);
        logIn.setVisible(true);

    }

    public void sign_outViewVisibility(){
        home.setVisible(true);
        map.setVisible(true);
        nav_schedule.setVisible(true);
        profile.setVisible(true);
        logOut.setVisible(true);
        logIn.setVisible(false);
    }

}
