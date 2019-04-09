package com.project.usm.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Model.News;
import com.project.usm.app.Tools.GsonParser;
import com.project.usm.app.Tools.HttpClient;
import com.project.usm.app.Tools.SessionManager;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import lombok.Getter;


public class SplashScreen extends AppCompatActivity {
    private static GsonParser gsonParser;
    private static SessionManager session;
    private static HttpClient httpClient;
    private static ClientApp clientApp;
    private static List<News> newsList;
    private static String jsonResponseClient,jsonResponseNews;

    public static void oauthClient(){
        SplashScreen.getHttpClient().buildTaskPost().oauth().postRequestBuild(SplashScreen.getClientApp().getHeaders(),SplashScreen.getClientApp().getParams()).getTaskPost().execute();
        try {
            jsonResponseClient = SplashScreen.getHttpClient().getTaskPost().get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            SplashScreen.getHttpClient().getTaskPost().cancel(true);
        }

        gsonParser.parseClientApp(jsonResponseClient);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isOnline()) {
            session = new SessionManager(this);
            httpClient = new HttpClient("http://35.184.2.70","8000");
            clientApp = new ClientApp();
            gsonParser = GsonParser.getNewInstance();
            oauthClient();
            try {

                SplashScreen.getHttpClient().buildTaskGet().news()
                        .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                                SplashScreen.getClientApp().getTokenType()+" "+SplashScreen.getClientApp().getTokenClient())})
                        .getTaskGet().execute();

                jsonResponseNews = SplashScreen.getHttpClient().getTaskGet().get();
                newsList = gsonParser.parseNews(jsonResponseNews);
            } catch (ExecutionException | InterruptedException e) {
               Log.e("ERROR APP OAUTH",e.getMessage());
            } finally {
                SplashScreen.getHttpClient().getTaskGet().cancel(true);
            }


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
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

}
