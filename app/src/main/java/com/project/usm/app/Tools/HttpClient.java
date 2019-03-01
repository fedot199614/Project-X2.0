package com.project.usm.app.Tools;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpClient {
    private CloseableHttpClient client;
    private List<NameValuePair> params;
    private String url;
    private String port;
    private String absoluteUrl;
    private HttpPost httpPost;
    private HttpGet httpGet;
    private String response;
    private String oauthService;
    private String newsService;
    private MyTaskPost taskPost;
    private MyTaskGet taskGet;


public HttpClient(String url,String port){
    this.url = url;
    this.port = port;
    this.client = HttpClients.createDefault();
    this.params = new ArrayList<>();
    this.oauthService = "oauth/token";
    this.newsService = "v1/news";
}

public HttpClient buildTaskPost(){
    this.taskPost = new MyTaskPost();
    return this;
}

public HttpClient buildTaskGet(){
    this.taskGet = new MyTaskGet();
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

public void flushData(){
    params.clear();

}

public void closeClient() throws IOException {
    client.close();
}

public class MyTaskPost extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //tvInfo.setText("Begin");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
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



    public class MyTaskGet extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //tvInfo.setText("Begin");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
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
}
