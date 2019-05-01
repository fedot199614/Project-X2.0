package com.project.usm.app.Tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaseQuery {
    public static List<News> getNews(GsonParser parser){
        List<News> newsList = null;
        String jsonResponseNews = " ";
        try {

            SplashScreen.getHttpClient().buildTaskGet().news()
                    .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                            SplashScreen.getClientApp().getTokenType()+" "+SplashScreen.getClientApp().getTokenClient())})
                    .getTaskGet().execute();

            jsonResponseNews = SplashScreen.getHttpClient().getTaskGet().get();

            newsList = parser.parseNews(jsonResponseNews);
        } catch (ExecutionException | InterruptedException e) {
            Log.e("ERROR APP OAUTH",e.getMessage());
        } finally {
            SplashScreen.getHttpClient().getTaskGet().cancel(true);
        }

        return newsList;
    }


    public static void oauthClient(GsonParser parser){
        String jsonResponseClient = " ";
        SplashScreen.getHttpClient().buildTaskPost().oauth().postRequestBuild(SplashScreen.getClientApp().getHeaders(),SplashScreen.getClientApp().getParams()).getTaskPost().execute();
        try {
            jsonResponseClient = SplashScreen.getHttpClient().getTaskPost().get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            SplashScreen.getHttpClient().getTaskPost().cancel(true);
        }

        parser.parseClientApp(jsonResponseClient);
    }

    public static void updateData(String queryName, String query){
        String jsonResponseProfile = " ";
        SplashScreen.getHttpClient().buildTaskPut().update(queryName,query)
                .putRequestBuild(new Header[]{new BasicHeader("Authorization",
                "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskPut().execute();
    }


    public static List<ProfileInfo> membersGroupQuery(GsonParser parser, String grID){
        String json = "";
        List<ProfileInfo> profInfoList = new LinkedList<>();
        SplashScreen.getHttpClient().buildTaskGet().membersService(grID)
                .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskGet().execute();
        try {

            json = SplashScreen.getHttpClient().getTaskGet().get();

            profInfoList = parser.parseMembers(json);

        } catch (ExecutionException  | InterruptedException e) {
            e.printStackTrace();
        }finally {
            SplashScreen.getHttpClient().getTaskGet().cancel(true);
        }


        for(ProfileInfo element : profInfoList){
            Bitmap Response = null;
            SplashScreen.getHttpClient().buildTaskGetImg().customEndPoint(element.getProfileResponseResource().getProfileImageUrl())
                    .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                            "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                    .getTaskGetImg().execute();
            try {

                Response = SplashScreen.getHttpClient().getTaskGetImg().get();
                element.setAvatar(Response);
            } catch (ExecutionException  | InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                SplashScreen.getHttpClient().getTaskGetImg().cancel(true);
            }
        }

        return profInfoList;

    }

    public static ProfileInfo profileQuery(){

        String jsonResponseProfile = "";
        SplashScreen.getHttpClient().buildTaskGet().profile()
                .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskGet().execute();
        try {
            jsonResponseProfile = SplashScreen.getHttpClient().getTaskGet().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            SplashScreen.getHttpClient().getTaskGet().cancel(true);
        }


        ProfileInfo profInfo =  SplashScreen.getGsonParser().parseProfile(false,jsonResponseProfile);
        //Log.e("werwwer",jsonResponseProfile);
        //Log.e("wersdfsdf",profInfo.getProfileResponseResource().getProfileImageUrl());
        Bitmap Response = null;
        SplashScreen.getHttpClient().buildTaskGetImg().customEndPoint(profInfo.getProfileResponseResource().getProfileImageUrl())
                .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskGetImg().execute();
        try {

            Response = SplashScreen.getHttpClient().getTaskGetImg().get();
            profInfo.setAvatar(Response);
        } catch (ExecutionException  | InterruptedException e) {
            e.printStackTrace();
        }

        return profInfo;
    }
}
