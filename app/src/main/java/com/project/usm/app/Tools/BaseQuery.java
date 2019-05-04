package com.project.usm.app.Tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
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

    public static void updateData(Bitmap bitmap, Activity activity,ProfileInfo profileInfo){
        String jsonResponseProfile = " ";
        SplashScreen.getHttpClient().buildTaskPostImg(bitmap,activity,profileInfo).imgPostIntoService()
                .postRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Client-ID 4d70425633d094e")})
                .getTaskPostImg().execute();
    }


    public static void updateData(Activity activity,String queryName, String query){
        String jsonResponseProfile = " ";
        SplashScreen.getHttpClient().buildTaskPut(activity).update(queryName,query)
                .putRequestBuild(new Header[]{new BasicHeader("Authorization",
                "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskPut().execute();
    }

    public static void scheduleQuery(RecyclerView rv,Activity activity,String query){
        String jsonResponseProfile = " ";
        SplashScreen.getHttpClient().buildTaskGetSchedule(rv,activity).getSchedule(query)
                .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskSchedule().execute();
    }


    public static void membersGroupQuery(RecyclerView rv,Activity activity,GsonParser parser, String grID){
        String json = "";
        List<ProfileInfo> profInfoList = new LinkedList<>();
        SplashScreen.getHttpClient().buildTaskGetGroupMember(rv,activity).membersService(grID)
                .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskGetGroupMember().execute();


    }


    public static List<ProfileInfo> membersGroupQueryAsynck(Activity activity,GsonParser parser, String grID){
        String json = "";
        List<ProfileInfo> profInfoList = new LinkedList<>();
        SplashScreen.getHttpClient().buildTaskGet().membersService(grID)
                .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                        "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                .getTaskGet().execute();
        try {

            json = SplashScreen.getHttpClient().getTaskGet().get();

            profInfoList = parser.parseMembers(activity,json);

        } catch (ExecutionException  | InterruptedException e) {
            e.printStackTrace();
        }finally {
            SplashScreen.getHttpClient().getTaskGet().cancel(true);
        }

        return profInfoList;

    }

    public static ProfileInfo profileQuery(Activity activity){

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

        ProfileInfo profInfo =  SplashScreen.getGsonParser().parseProfile(activity,false,jsonResponseProfile);

        return profInfo;
    }
}
