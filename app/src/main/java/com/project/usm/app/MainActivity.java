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
import android.widget.Toast;

import com.project.usm.app.Fragments.Map;
import com.project.usm.app.R;
import com.project.usm.app.AOP.Loggable;
import com.project.usm.app.Fragments.Auth;
import com.project.usm.app.Fragments.RV_Main;


import java.util.Objects;

@Loggable
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TAG_CODE_PERMISSION_LOCATION = 0;
    public void initHomePage(){
        RV_Main mainNewsList = new RV_Main();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(getString(R.string.root));
        ft.replace(R.id.mainFrame,mainNewsList).commit();

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //permission check
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            },
                    TAG_CODE_PERMISSION_LOCATION);
        }
        //Init Start Fragment
        initHomePage();




    }


    @Override
    public void onBackPressed() {



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


if(getSupportFragmentManager().getBackStackEntryCount()!= 1  && !getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.RV_Main))){
    getSupportFragmentManager().popBackStack();
}else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.RV_Main))){
    dialog.show();
}




        if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.SharedNews))) {
            getSupportFragmentManager().popBackStack(getString(R.string.sharedNews), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.RV_Main))){
            dialog.show();
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Registration))){
            getSupportFragmentManager().popBackStack(getString(R.string.reg), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Auth))){
            getSupportFragmentManager().popBackStack(getString(R.string.auth), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(getString(R.string.Map))){
            getSupportFragmentManager().popBackStack(getString(R.string.map), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            initHomePage();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.map) {
            Map map = new Map();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(getString(R.string.map));
            ft.replace(R.id.mainFrame,map).commit();

        } else if (id == R.id.nav_logIn) {
            Auth auth = new Auth();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(getString(R.string.auth));
            ft.replace(R.id.mainFrame,auth).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
