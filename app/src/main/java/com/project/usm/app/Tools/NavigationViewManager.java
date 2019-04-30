package com.project.usm.app.Tools;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.project.usm.app.R;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NavigationViewManager {
    private static final NavigationViewManager navigationViewManager = new NavigationViewManager();

    private CircleImageView navProfImg;
    private TextView usmText;
    private TextView someInfo;
    private TextView name;
    private ConstraintLayout imgUserNav;
    private ImageView usmLogo;
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
        this.usmText = navigationView.getHeaderView(0).findViewById(R.id.usmText);
        this.someInfo =  navigationView.getHeaderView(0).findViewById(R.id.someInfo);
        this.imgUserNav =  navigationView.getHeaderView(0).findViewById(R.id.imgUserNav);
        this.usmLogo =  navigationView.getHeaderView(0).findViewById(R.id.usmLogo);
        this.name =  navigationView.getHeaderView(0).findViewById(R.id.name);
        this.navProfImg = navigationView.getHeaderView(0).findViewById(R.id.nav_prof_img);

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
        usmText.setVisibility(View.VISIBLE);
        someInfo.setVisibility(View.INVISIBLE);
        imgUserNav.setVisibility(View.INVISIBLE);
        usmLogo.setVisibility(View.VISIBLE);
        name.setVisibility(View.INVISIBLE);
        logIn.setVisible(true);

    }

    public void sign_outViewVisibility(){
        home.setVisible(true);
        map.setVisible(true);
        nav_schedule.setVisible(true);
        profile.setVisible(true);
        logOut.setVisible(true);
        usmText.setVisibility(View.INVISIBLE);
        someInfo.setVisibility(View.VISIBLE);
        imgUserNav.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        usmLogo.setVisibility(View.INVISIBLE);
        logIn.setVisible(false);
    }

}
