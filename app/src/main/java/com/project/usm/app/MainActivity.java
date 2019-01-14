package com.project.usm.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.project.usm.app.Fragments.Map;
import com.project.usm.app.Fragments.Schedule;
import com.project.usm.app.Presenter.MainActivityPr;
import com.project.usm.app.R;
import com.project.usm.app.AOP.Loggable;
import com.project.usm.app.Fragments.Auth;
import com.project.usm.app.Fragments.RV_Main;
import com.project.usm.app.View.MainActivityView;


import java.util.Objects;

@Loggable
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainActivityView {

    private static final int TAG_CODE_PERMISSION_LOCATION = 0;
    private TabLayout tabBar;

    @Override
    public void initHomePage(){
        RV_Main mainNewsList = new RV_Main();
        FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
        fr.addToBackStack(getString(R.string.root));
        fr.replace(R.id.mainFrame,mainNewsList).commit();
    }

    @Override
    public void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    },
                    TAG_CODE_PERMISSION_LOCATION);
        }
    }

    @Override
    public void initNavigViewIcons() {
        Iconify.with(new FontAwesomeModule());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setIcon(new IconDrawable(this, FontAwesomeIcons.fa_home).colorRes(R.color.secondText));
        navigationView.getMenu().findItem(R.id.map).setIcon(new IconDrawable(this, FontAwesomeIcons.fa_map_marker).colorRes(R.color.secondText));
        navigationView.getMenu().findItem(R.id.nav_logIn).setIcon(new IconDrawable(this, FontAwesomeIcons.fa_sign_in).colorRes(R.color.secondText));
        navigationView.getMenu().findItem(R.id.nav_schedule).setIcon(new IconDrawable(this, FontAwesomeIcons.fa_clipboard).colorRes(R.color.secondText));

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        MainActivityPr presenter = new MainActivityPr(this);
        presenter.init();

    }


    @Override
    public void onBackPressed() {

         if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.SharedNews))) {
            getSupportFragmentManager().popBackStack(getString(R.string.sharedNews), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.RV_Main))){
            exitDialogShow();
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Registration))){
            getSupportFragmentManager().popBackStack(getString(R.string.reg), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Auth))){
            getSupportFragmentManager().popBackStack(getString(R.string.auth), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Map))){
            getSupportFragmentManager().popBackStack(getString(R.string.map), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Schedule))){
            getSupportFragmentManager().popBackStack(getString(R.string.schedule_back), FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            initHomePage();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_schedule) {
            Schedule schedule = new Schedule();
            FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
            fr.addToBackStack(getString(R.string.schedule_back));
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
