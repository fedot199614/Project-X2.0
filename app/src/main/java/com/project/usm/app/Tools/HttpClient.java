package com.project.usm.app.Tools;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.project.usm.app.Fragments.Profile;
import com.project.usm.app.MainActivity;
import com.project.usm.app.Model.User;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.View.Auth_View;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpClient {
    private CloseableHttpClient client;
    private List<NameValuePair> params;
    private String groupMembersService;
    private String url;
    private String port;
    private String absoluteUrl;
    private HttpPut httpPut;
    private HttpPost httpPost;
    private HttpGet httpGet;
    private String updateService;
    private String response;
    private String oauthService;
    private String newsService;
    private String profileService;
    private MyTaskPost taskPost;
    private MyTaskPostAuth taskPostAuth;
    private MyTaskGet taskGet;
    private MyTaskGetImg taskGetImg;
    private MyTaskPut taskPut;


    @Inject
public HttpClient(String url,String port){
    this.url = url;
    this.port = port;
    this.client = HttpClients.createDefault();
    this.params = new ArrayList<>();
    this.oauthService = "oauth/token";
    this.newsService = "news";
    this.profileService = "users/profile/";
    this.groupMembersService = "users/query";
    this.updateService = "users/update";
}

public HttpClient buildTaskGetImg(){
        this.taskGetImg = new MyTaskGetImg();
        return this;
    }

    public HttpClient buildTaskPostAuth(Activity activity, Auth_View auth_view, User user){
        this.taskPostAuth = new MyTaskPostAuth(activity,auth_view,user);
        return this;
    }

public HttpClient buildTaskPost(){
    this.taskPost = new MyTaskPost();
    return this;
}

public HttpClient buildTaskGet(){
    this.taskGet = new MyTaskGet();
    return this;
}

    public HttpClient buildTaskPut(){
        this.taskPut = new MyTaskPut();
        return this;
    }

public HttpClient oauth(){
    this.absoluteUrl = this.url+":"+this.port+"/"+this.oauthService;
    this.httpPost = new HttpPost(absoluteUrl);
    this.httpGet = new HttpGet(absoluteUrl);
    return this;
}

public HttpClient news(){
    this.absoluteUrl = this.url+":"+this.port+"/"+this.newsService;
    this.httpPost = new HttpPost(absoluteUrl);
    this.httpGet = new HttpGet(absoluteUrl);
    return this;
}

public HttpClient profile(){
        this.absoluteUrl = this.url+":"+this.port+"/"+this.profileService;
        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
}

public HttpClient update(String queryName,String query){
    this.absoluteUrl = this.url+":"+this.port+"/"+this.updateService;
    if(!this.absoluteUrl.endsWith("?")) {
        this.absoluteUrl += "?";
    }
    List<NameValuePair> params = new LinkedList<NameValuePair>();
    params.add(new BasicNameValuePair(queryName, query));
    String paramString = URLEncodedUtils.format(params, "utf-8");
    this.absoluteUrl+=paramString;
    this.httpPut = new HttpPut(absoluteUrl);
    this.httpPost = new HttpPost(absoluteUrl);
    this.httpGet = new HttpGet(absoluteUrl);
    return this;
}

public HttpClient membersService(String query){

        this.absoluteUrl = this.url+":"+this.port+"/"+this.groupMembersService;
        if(!this.absoluteUrl.endsWith("?")) {
            this.absoluteUrl += "?";
        }
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("groupId", query));
        String paramString = URLEncodedUtils.format(params, "utf-8");
        this.absoluteUrl+=paramString;

        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
}

public HttpClient customEndPoint(String endpoint){
        this.absoluteUrl = endpoint;
        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
}

public HttpClient postRequestBuild(Header[] headers, List<BasicNameValuePair> valuePairsList){
            httpPost.setHeaders(headers);
            params.addAll(valuePairsList);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
    return this;
}


public HttpClient getRequestBuild(Header[] headers){
        httpGet.setHeaders(headers);
    return this;
}


public HttpClient putRequestBuild(Header[] headers){
        httpPut.setHeaders(headers);
        return this;
}

public void flushData(){
    params.clear();

}

public void closeClient() throws IOException {
    client.close();
}

    public class MyTaskPostAuth extends AsyncTask<String, Void, String>{

        Activity activity;
        Auth_View auth_view;
        User user;
        private ProgressDialog dialog;
        public MyTaskPostAuth(Activity activity, Auth_View auth_view, User user){
            this.activity = activity;
            this.auth_view = auth_view;
            this.user = user;
            this.dialog = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            Log.d("LOG_TAG", "BeginPostAuth");
            dialog.setMessage("Авторизация. Подождите...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = "";
            CloseableHttpResponse httpResponse = null;
            try {

                httpResponse = client.execute(httpPost);

            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bf = null;
            try {

                bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                res = bf.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            flushData();
            return res;
        }


        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
            SplashScreen.getGsonParser().parseUser(result,user);

            if(SplashScreen.getSessionManager().isLoggedIn()){
                auth_view.onLoginSuccessfully();
                auth_view.initAuthState();
                auth_view.initHomePage();
                SplashScreen.setProfileInfo(BaseQuery.profileQuery());
                SplashScreen.setProfileInfoList(BaseQuery.membersGroupQuery(SplashScreen.getGsonParser(),SplashScreen.getProfileInfo().getProfileResponseResource().getGroupId()));

                MainActivity.getNavManager().getNavProfImg().setImageBitmap(SplashScreen.getProfileInfo().getAvatar());
                MainActivity.getNavManager().getName().setText(SplashScreen.getProfileInfo().getProfileResponseResource().getFirstName()+" "+SplashScreen.getProfileInfo().getProfileResponseResource().getLastName());
                MainActivity.getNavManager().getSomeInfo().setText(SplashScreen.getProfileInfo().getProfileResponseResource().getSpeciality());
            }else{
                auth_view.onLoginMessageError();
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

public class MyTaskPost extends AsyncTask<String, Void, String>{




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LOG_TAG", "BeginPost");
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = "";
            CloseableHttpResponse httpResponse = null;
            try {

                httpResponse = client.execute(httpPost);

            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bf = null;
            try {

                    bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    res = bf.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            flushData();
            return res;
        }


        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }
    public class MyTaskPut extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d("LOG_TAG", "BeginPut");
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = "";
            CloseableHttpResponse httpResponse = null;
            try {

                httpResponse = client.execute(httpPut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bf = null;
            try {

                bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                res = bf.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            flushData();
            return res;
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
            SplashScreen.getGsonParser().parseProfile(true,result);
            Toast.makeText(MainActivity.getNavManager().getContext(),"Сохранено",Toast.LENGTH_SHORT).show();

        }

    }


    public class MyTaskGet extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d("LOG_TAG", "BeginGet");
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = "";
            CloseableHttpResponse httpResponse = null;
            try {

                httpResponse = client.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bf = null;
            try {

                    bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    res = bf.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            flushData();
            return res;
        }


        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);


        }
    }

    public class MyTaskGetImg extends AsyncTask<String, Void, Bitmap>{






        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LOG_TAG", "BeginGet");
        }



        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpResponse httpResponse;
            Bitmap bmp = null;

            try{
                httpResponse = client.execute(httpGet);
                // responseCode = httpResponse.getStatusLine().getStatusCode();

                HttpEntity entity = httpResponse.getEntity();

                if (entity != null)
                {
                    InputStream in = entity.getContent();
                    bmp = BitmapFactory.decodeStream(in);
                    in.close();
                }
            } catch (ClientProtocolException e)  {
                client.getConnectionManager().shutdown();
                e.printStackTrace();
            } catch (IOException e) {
                client.getConnectionManager().shutdown();
                e.printStackTrace();
            }


            flushData();
            return bmp;
        }


        @Override
        protected  void onPostExecute(Bitmap result){
            super.onPostExecute(result);
        }
    }



}
