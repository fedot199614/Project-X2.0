package com.project.usm.app.Module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Tools.GsonParser;
import com.project.usm.app.Tools.HttpClient;
import com.project.usm.app.Tools.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class InjectionModules {
    private String url = "http://35.184.2.70";
    private String port = "8000";

    @Provides
    public HttpClient httpClient(){
        return new HttpClient(this.url,this.port);
    }

    @Provides
    public GsonParser gsonParser(){
        return GsonParser.getNewInstance();
    }

    @Provides
    public ClientApp clientApp(){
        return new ClientApp();
    }

    @Provides
    public SessionManager sessionManager(Context context){
        return new SessionManager(context);
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

}
