package com.project.usm.app.Tools;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Model.News;
import com.project.usm.app.SplashScreen;

import java.util.ArrayList;
import java.util.List;

public class GsonParser {
    private static final GsonParser gsonParser = new GsonParser();

    private GsonParser(){

    }

    public static GsonParser getNewInstance(){
        return gsonParser;
    }

    public List<News> parseNews(String jsonObj){
        List<News> newsList = new ArrayList<>();
        return newsList;
    }

    public void parseClientApp(String json){
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        if(!jObject.has("error")){
            SplashScreen.getClientApp().setTokenClient(jObject.get("access_token").getAsString());
        }else{
            Log.e("ERROR","ERROR PARSING APP CLIENT");
        }

    }
}
