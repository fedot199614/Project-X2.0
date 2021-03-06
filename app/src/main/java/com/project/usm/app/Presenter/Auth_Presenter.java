package com.project.usm.app.Presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.util.Log;
import android.widget.Toast;

import com.project.usm.app.MainActivity;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Model.User;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.View.Auth_View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.message.BasicNameValuePair;
import lombok.Getter;


public class Auth_Presenter implements IAuth_Presenter {
    User user;
    Auth_View auth_view;
    Activity activity;
    public Auth_Presenter(Auth_View auth,Activity activity) {
        this.activity = activity;
        this.auth_view = auth;
    }

    public Auth_Presenter() {

    }

    @Override
    public void createAuthUser(String idnp, String password, String token){
        user = new User(idnp,password);
        user.setToken(token);
    }

    @Override
    public void onLogin(String email, String pass) {
        user = new User(email,pass);

        int codeValidationIdnp = user.isValidIdnp();
        int codeValidationPassword = user.isValidPassword();
        if(codeValidationIdnp != -1){
            auth_view.showErrorValidationLogin(codeValidationIdnp);
        }
        if(codeValidationPassword != -1){
            auth_view.showErrorValidationPassword(codeValidationPassword);
        }
        if(codeValidationIdnp == -1 && codeValidationPassword == -1) {
            List<BasicNameValuePair> clientParamPlusUserParam = new ArrayList<>();
            clientParamPlusUserParam.addAll(user.getParamsClient());
            clientParamPlusUserParam.addAll(user.getParams());
            SplashScreen.getHttpClient().buildTaskPostAuth(activity,auth_view,user).oauth().postRequestBuild(user.getHeaders(), clientParamPlusUserParam).getTaskPostAuth().execute();

        }

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


}
