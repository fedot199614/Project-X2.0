package com.project.usm.app.Model;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User implements IUser {

    private String grant_type;
    private String token_type;
    private String idnp;
    private String password;
    private String token;
    private String name;
    private String lastName;
    private String phone;
    private int id;
    private List<BasicNameValuePair> params;
    private Header[] headers;

    public User(String email,String pass){
        this.idnp = email;
        this.password = pass;
        this.grant_type = "password";
        this.token_type = "";
        this.params = new ArrayList<>();
        this.headers = new Header[1];
        paramsBuild();
        headersBuild();
    }

    @Override
    public boolean validationPhone() {

        return false;
    }


    @Override
    public int isValidIdnp() {
        int code = -1;
        if(TextUtils.isEmpty(getIdnp())){
            code = 0;
        }else if(!Pattern.compile("^[0-9]+$").matcher(getIdnp()).matches()){
            code = 1;
        }else if(getIdnp().length()!=13){
            code = 2;
        }

        return code;
    }



    @Override
    public int isValidPassword() {

        int code = -1;
        if(TextUtils.isEmpty(getPassword())){
            code = 0;
        }else if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$").matcher(getPassword()).matches()){
            code = 1;
        }else if(getPassword().length()>30){
            code = 2;
        }else if(getPassword().length()<8){
            code = 3;
        }

        return code;
    }


    public  List<BasicNameValuePair> paramsBuild(){
        params.add(new BasicNameValuePair("grant_type",this.grant_type));
        params.add(new BasicNameValuePair("username",this.idnp));
        params.add(new BasicNameValuePair("password",this.password));
        return params;
    }

    public Header[] headersBuild(){
        headers[0] = new BasicHeader("Content-Type","application/x-www-form-urlencoded");
        return headers;
    }


    public void addParams(String key,String value){

        this.params.add(new BasicNameValuePair(key,value));
    }
}
