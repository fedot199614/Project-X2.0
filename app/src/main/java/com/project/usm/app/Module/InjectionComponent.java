package com.project.usm.app.Module;

import com.google.gson.Gson;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Tools.GsonParser;
import com.project.usm.app.Tools.HttpClient;
import com.project.usm.app.Tools.SessionManager;

import dagger.Component;
import dagger.Module;

@Component(modules = {InjectionModules.class,ContextModule.class})
public interface InjectionComponent {
       HttpClient getHttpClient();
       SessionManager getSessionManager();
       Gson getGson();
       GsonParser getGsonParser();
       ClientApp getClientApp();
}
