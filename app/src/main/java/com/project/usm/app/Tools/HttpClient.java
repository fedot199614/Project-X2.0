package com.project.usm.app.Tools;


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


public HttpClient(String url,String port){
    this.url = url;
    this.port = port;
    this. absoluteUrl = url+":"+port;
    this.client = HttpClients.createDefault();
    this.params = new ArrayList<>();
    this.httpPost = new HttpPost(absoluteUrl);
    this.httpGet = new HttpGet(absoluteUrl);

}


public void postRequest(Header[] headers, List<BasicNameValuePair> valuePairsList) throws IOException {
    httpPost.setHeaders(headers);
    params.addAll(valuePairsList);
    httpPost.setEntity(new UrlEncodedFormEntity(params));
    CloseableHttpResponse httpResponse = client.execute(httpPost);
    BufferedReader bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
    response = bf.readLine();
    flushData();
}


public void getRequest(Header[] headers) throws IOException {
        httpGet.setHeaders(headers);
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        BufferedReader bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        response = bf.readLine();
        flushData();
}

public void flushData(){
    params.clear();

}

public void closeClient() throws IOException {
    client.close();
}

}
