package com.project.usm.app.Model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientApp {
    private String appID;
    private String appPassword;
    private String tokenClient;
    private String tokenType;
    private String grant_type;
    private List<BasicNameValuePair> params;
    private Header[] headers;


public ClientApp(){
    this.appID = "MOBILE_APP";
    this.appPassword ="password";
    this.grant_type = "client_credentials";
    this.tokenType = "Bearer";
    this.params = new ArrayList<>();
    this.headers = new Header[1];
    paramsBuild();
    headersBuild();
}

public List<BasicNameValuePair> paramsBuild(){
    params.add(new BasicNameValuePair("grant_type",this.grant_type));
    params.add(new BasicNameValuePair("client_id",this.appID));
    params.add(new BasicNameValuePair("client_secret",this.appPassword));
    return params;
}

public Header[] headersBuild(){
   headers[0] = new BasicHeader("Content-Type","application/x-www-form-urlencoded");
   //String basicToken = Base64.getEncoder().encodeToString(String.format("%s:%s",appID,appPassword).getBytes());
   return headers;
}


public void addParams(String key,String value){

    this.params.add(new BasicNameValuePair(key,value));
}

public void paramsClear(){
    this.params.clear();
}

}
