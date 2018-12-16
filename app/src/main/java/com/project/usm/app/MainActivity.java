package com.project.usm.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.project.usm.app.R;
import com.project.usm.app.AOP.Loggable;
import com.project.usm.app.Fragments.Auth;
import com.project.usm.app.Fragments.RV_Main;

@Loggable
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {






    public void initHomePage(){
        RV_Main mainNewsList = new RV_Main();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack("root");
        ft.replace(R.id.mainFrame,mainNewsList).commit();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Init Start Fragment
        initHomePage();




    }


    @Override
    public void onBackPressed() {
        //Log.i("gggggg",String.valueOf(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName()));
       // Log.i("gggggg1",String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        //Log.i("gggggg2",getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()).getClass().getSimpleName());
       //if(getSupportFragmentManager().getBackStackEntryCount() == 1) {
           // finish();
        //}
        if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("SharedNews")) {
            getSupportFragmentManager().popBackStack("sharedNews", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("RV_Main")){
            finish();
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("Registration")){
            getSupportFragmentManager().popBackStack("reg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("Auth")){
            finish();
        }

       // switch(getSupportFragmentManager().getFragments().size()){
         //   case :
       // }
       //else {
           // super.onBackPressed();
       //}

        //if(getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("SharedNews")){
           // getSupportFragmentManager().findFragmentByTag("root").
        //}



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

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logIn) {
            Auth auth = new Auth();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack("auth");
            ft.replace(R.id.mainFrame,auth).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
