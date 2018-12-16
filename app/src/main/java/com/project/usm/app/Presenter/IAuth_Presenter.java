package com.project.usm.app.Presenter;

public interface IAuth_Presenter {
    void onLogin(String email, String pass);
    void registration(String name, String lastName, String email, String pass, String mobile);
    void forgotPass(String email);
    void checkedWebServicesConnection();
    void connectToWS();


}
