package com.project.usm.app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;


import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.project.usm.app.AOP.Annotations.CheckBasePermissions;
import com.project.usm.app.AOP.Annotations.OnExit;
import com.project.usm.app.Fragments.Map;
import com.project.usm.app.Fragments.Profile;
import com.project.usm.app.Fragments.Schedule;
import com.project.usm.app.Presenter.Auth_Presenter;
import com.project.usm.app.Presenter.MainActivityPr;
import com.project.usm.app.AOP.Annotations.Loggable;
import com.project.usm.app.Fragments.Auth;
import com.project.usm.app.Fragments.RV_Main;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.Tools.NavigationViewManager;
import com.project.usm.app.View.MainActivityView;


import java.util.HashMap;

import lombok.Getter;

@Getter
@Loggable
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainActivityView {

    private static final int TAG_CODE_PERMISSION_LOCATION = 0;
    private TabLayout tabBar;
    private FlowingDrawer drawer;
    private static NavigationViewManager navViewManager;
    private Auth_Presenter auth_presenter;
    public static NavigationViewManager  getNavManager(){
        return navViewManager;
    }

    @Override
    public  void initHomePage(){
        RV_Main mainNewsList = new RV_Main();
        FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
        fr.addToBackStack(null);
        fr.replace(R.id.mainFrame,mainNewsList).commit();
    }

    @Override
    public void initNavigViewIcons() {

        navViewManager = NavigationViewManager.getNewInstance();
        navViewManager.setContext(this);
        navViewManager.setMainActivity(this);
        navViewManager.setNavigationView(findViewById(R.id.nav_view));
        navViewManager.basicInit();
        navViewManager.initIcons();
        navViewManager.getNavigationView().setNavigationItemSelectedListener(this);
        boolean isLogin = SplashScreen.getSessionManager().isLoggedIn();
        if(isLogin){
            navViewManager.sign_outViewVisibility();
            auth_presenter = new Auth_Presenter();
            HashMap<String,String> userDetails = SplashScreen.getSessionManager().getUserDetails();
            auth_presenter.createAuthUser(userDetails.get("idnp"),userDetails.get("password"),userDetails.get("token"));
            navViewManager.getNavProfImg().setImageBitmap(SplashScreen.getProfileInfo().getAvatar());
            navViewManager.getName().setText(SplashScreen.getProfileInfo().getProfileResponseResource().getFirstName()+" "+SplashScreen.getProfileInfo().getProfileResponseResource().getLastName());
            navViewManager.getSomeInfo().setText(SplashScreen.getProfileInfo().getProfileResponseResource().getSpeciality());
        }else{
            navViewManager.sign_inViewVisibility();
        }


    }


    @Override
    public void exitDialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.leave)).setTitle(getString(R.string.exit));
        builder.setPositiveButton(getString(R.string.OK),(dialog,which) -> {
                    finish();
                }
        );
        builder.setNegativeButton(getString(R.string.Cancel),(dialog,which) -> {
                    dialog.cancel();
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @CheckBasePermissions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(new IconDrawable(this, FontAwesomeIcons.fa_bars).sizeDp(21).colorRes(R.color.white));

        setSupportActionBar(toolbar);
        //custom drawer
        drawer = (FlowingDrawer) findViewById(R.id.drawer_layout);
        drawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        toolbar.setNavigationOnClickListener(e->{drawer.toggleMenu(true);});



        MainActivityPr presenter = new MainActivityPr(this);
        presenter.init();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getHeaderView(0).findViewById(R.id.imgUserNav).setOnClickListener(e->{
            drawer.closeMenu(true);
            initProfile();
        });


    }

    @OnExit
    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void initProfile(){
        Profile profile = new Profile();
        FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
        fr.addToBackStack(getString(R.string.profile));
        profile.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));
        profile.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));
        fr.replace(R.id.mainFrame,profile).commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
           initHomePage();
        } else if (id == R.id.profile) {
            initProfile();
        } else if (id == R.id.nav_schedule) {
            Schedule schedule = new Schedule();
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.addToBackStack(getString(R.string.scheduleBack));
            fr.replace(R.id.mainFrame,schedule).commit();
        } else if (id == R.id.map) {
            Map map = new Map();
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.addToBackStack(getString(R.string.map));
            fr.replace(R.id.mainFrame,map).commit();

        } else if (id == R.id.nav_logIn) {
            Auth auth = new Auth();
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.addToBackStack(getString(R.string.auth));
            fr.replace(R.id.mainFrame,auth).commit();

        }else if (id == R.id.nav_Out) {
            SplashScreen.getSessionManager().logoutUser();
            navViewManager.sign_inViewVisibility();
            initHomePage();
        }


        drawer.closeMenu(true);

        return true;
    }

}
