package com.project.usm.app.Presenter;

import com.project.usm.app.Model.User;
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
    public void forgotPass(String email) {

    }

    @Override
    public void checkedWebServicesConnection() {

    }

    @Override
    public void connectToWS() {

    }


}
