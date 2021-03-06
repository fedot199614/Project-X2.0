package com.project.usm.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Model.User;
import com.project.usm.app.Module.ContextModule;
import com.project.usm.app.Module.DaggerInjectionComponent;
import com.project.usm.app.Module.InjectionComponent;
import com.project.usm.app.Tools.BaseQuery;
import com.project.usm.app.Tools.GsonParser;
import com.project.usm.app.Tools.HttpClient;
import com.project.usm.app.Tools.SessionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class SplashScreen extends AppCompatActivity {
    private static User user;
    private static ProfileInfo profileInfo;
    private static GsonParser gsonParser;
    private static SessionManager session;
    private static HttpClient httpClient;
    private static ClientApp clientApp;
    private static List<News> newsList;
    private static String jsonResponseClient,jsonResponseNews;
    private static InjectionComponent component;
    private static List<ProfileInfo> profInfoList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = DaggerInjectionComponent.builder().contextModule(new ContextModule(this)).build();
        if(isOnline()) {
            session = component.getSessionManager();
            httpClient = component.getHttpClient();
            clientApp = component.getClientApp();
            gsonParser = component.getGsonParser();
            BaseQuery.oauthClient(gsonParser);
            newsList = new LinkedList<>();
            if(session.isLoggedIn()) {
                //profileInfo  = BaseQuery.profileQuery(this);
                user = new User(SplashScreen.getSessionManager().getUserDetails().get("idnp"),SplashScreen.getSessionManager().getUserDetails().get("password"));
                List<BasicNameValuePair> clientParamPlusUserParam = new ArrayList<>();
                clientParamPlusUserParam.addAll(user.getParamsClient());
                clientParamPlusUserParam.addAll(user.getParams());
                SplashScreen.getHttpClient().buildTaskPost().oauth().postRequestBuild(user.getHeaders(), clientParamPlusUserParam).getTaskPost().execute();
                try {
                    String response = SplashScreen.getHttpClient().getTaskPost().get();

                    JsonElement jElement = new JsonParser().parse(response);
                    JsonObject jObject = jElement.getAsJsonObject();
                    SplashScreen.getSessionManager().updateUserToken(jObject.get("access_token").getAsString());
                }catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                //BaseQuery.membersGroupQuery(SplashScreen.getGsonParser(),profileInfo.getProfileResponseResource().getGroupId());

            }
            if(SplashScreen.getSessionManager().isSessionContainLocalizationValue()){
                Locale myLocale = new Locale(SplashScreen.getSessionManager().getLocalizationApp());
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(this, MainActivity.class);
                startActivity(refresh);
                finish();
            }else {

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            Toast.makeText(this, getString(R.string.inetConnection), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }

    public static ProfileInfo getProfileInfo(){
        return profileInfo;
    }
    public static InjectionComponent getComponent(){
        return component;
    }
    public static HttpClient getHttpClient(){
        return httpClient;
    }
    public static List<News> getNewsList(){
        return newsList;
    }
    public static GsonParser getGsonParser(){
        return gsonParser;
    }
    public static ClientApp getClientApp(){
        return clientApp;
    }
    public static SessionManager getSessionManager(){
        return session;
    }
    public static void setProfileInfo(ProfileInfo profileInfo){
        SplashScreen.profileInfo = profileInfo;
    }

    public static void setProfileInfoList(List<ProfileInfo> profileInfo){
        SplashScreen.profInfoList = profileInfo;
    }

    public static void setNewsList(List<News> list){
        SplashScreen.newsList = list;
    }

    public static List<ProfileInfo> getProfileInfoList(){
        return profInfoList;
    }
}
