package com.project.usm.app.View;

import android.content.Context;

public interface Auth_View {
    void showLoading();
    void hideLoading();
    void onLoginMessageError();
    void onLoginSuccessfully();
    void showErrorValidationLogin(int errorCode);
    void showErrorValidationPassword(int errorCode);
    void initAuthState();
    void initHomePage();
}
