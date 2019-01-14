package com.project.usm.app.Presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;

import com.project.usm.app.Model.User;
import com.project.usm.app.R;
import com.project.usm.app.View.Auth_View;

public class Auth_Presenter implements IAuth_Presenter {

    Auth_View auth_view;
    public Auth_Presenter(Auth_View auth) {

        this.auth_view = auth;
    }



    @Override
    public void onLogin(String email, String pass) {
        User user = new User(email,pass);
        auth_view.showLoading();
        int codeValidation = user.isValidData();
        if(codeValidation != -1){
            auth_view.showErrorValidation(codeValidation);
        }
        auth_view.hideLoading();
    }

    @Override
    public void registration(String name, String lastName, String email, String pass, String mobile) {

    }



    @Override
    public void checkedWebServicesConnection() {

    }

    @Override
    public void connectToWS() {

    }

    @Override
    public void setAnimFade(Fragment fragment,Activity activity) {
            fragment.setExitTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.fade));
            fragment.setEnterTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.fade));
    }

    @Override
    public void beginTransaction(Fragment firstFragment,Fragment nextFragment) {
        FragmentTransaction transaction = firstFragment.getFragmentManager().beginTransaction();
        transaction.addToBackStack(firstFragment.getString(R.string.reg));
        transaction.addSharedElement(firstFragment.getView().findViewById(R.id.cv_auth), firstFragment.getView().findViewById(R.id.cv_auth).getTransitionName());
        transaction.replace(R.id.mainFrame, nextFragment).commit();
    }

    @Override
    public void initTabLayout() {
        auth_view.initTabBar();
    }


}
