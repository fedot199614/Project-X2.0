package com.project.usm.app.AOP;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.project.usm.app.AOP.Annotations.InitTabBar;
import com.project.usm.app.AOP.Annotations.ListItemSelected;
import com.project.usm.app.Fragments.Schedule;
import com.project.usm.app.MainActivity;
import com.project.usm.app.R;
import com.project.usm.app.Tools.NavItems;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class Aspects {

    @Around("@annotation(com.project.usm.app.AOP.Annotations.OnExit)")
    public void onBackPressed(JoinPoint joinPoint) {

        final Context context = (Context) joinPoint.getTarget();
        final Activity activity = (Activity) joinPoint.getTarget();
        final AppCompatActivity appCompatActivity = (AppCompatActivity) joinPoint.getTarget();
        final MainActivity main = (MainActivity) joinPoint.getTarget();

        main.getDrawer().closeMenu(true);

        if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.SharedNews))) {
            appCompatActivity.getSupportFragmentManager().popBackStack(appCompatActivity.getString(R.string.sharedNews), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.RV_Main))){
            main.exitDialogShow();
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.Registration))){
            appCompatActivity.getSupportFragmentManager().popBackStack(appCompatActivity.getString(R.string.reg), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.Auth))){
            appCompatActivity.getSupportFragmentManager().popBackStack(appCompatActivity.getString(R.string.auth), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.Map))){
            appCompatActivity.getSupportFragmentManager().popBackStack(appCompatActivity.getString(R.string.map), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.Schedule))){
            appCompatActivity.getSupportFragmentManager().popBackStack(appCompatActivity.getString(R.string.scheduleBack), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals(appCompatActivity.getString(R.string.profClass))) {
            appCompatActivity.getSupportFragmentManager().popBackStack(appCompatActivity.getString(R.string.profile), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else if(appCompatActivity.getSupportFragmentManager().getFragments().get(0).getClass().getSimpleName().equals("GroupMembers")){
            appCompatActivity.getSupportFragmentManager().popBackStack("membersGroup", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }

    @After("@annotation(com.project.usm.app.AOP.Annotations.InitTabBar)")
    public void initTabBar(JoinPoint joinPoint) {
        boolean flag = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(InitTabBar.class).check().flag;

        Fragment fragment = (Fragment) joinPoint.getTarget();

        TabLayout tabBar = (TabLayout) fragment.getActivity().findViewById(R.id.tabLayout);
        if(flag) {
            tabBar.setVisibility(View.GONE);
        }else{
            Schedule schedule = (Schedule) joinPoint.getTarget();
            //tabBar.setOnTabSelectedListener(schedule);
            tabBar.setVisibility(View.VISIBLE);
        }
    }


    @After("@annotation(com.project.usm.app.AOP.Annotations.ListItemSelected)")
    public void initTabBarGone(JoinPoint joinPoint) {
        int item = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ListItemSelected.class).item().e;

        Fragment fragment = (Fragment) joinPoint.getTarget();
        NavItems.getNavMenu(fragment.getActivity()).getItem(item).setChecked(true);
    }


}
