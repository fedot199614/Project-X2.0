package com.project.usm.app.Presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;

public interface IAuth_Presenter {
    void onLogin(String email, String pass);
    void setAnimFade(Fragment fragment,Activity activity);
    void beginTransaction(Fragment firstFragment,Fragment nextFragment);
    void initTabLayout();


}
