package com.project.usm.app.Tools;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.usm.app.DTO.NewsResponseResource;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.User;
import com.project.usm.app.SplashScreen;

import java.util.ArrayList;
import java.util.List;

public class GsonParser {
    private static final GsonParser gsonParser = new GsonParser();
    private static final String INVALID_TOKEN = "invalid_token";
    private static Gson gson = new Gson();
    private GsonParser(){

    }

    public static GsonParser getNewInstance(){
        return gsonParser;
    }

    public List<News> parseNews(String jsonObj){

        List<News> newsList = new ArrayList<>();
        JsonElement jElement = new JsonParser().parse(jsonObj);
        NewsResponseResource[] newsResponseResource = gson.fromJson(jsonObj, NewsResponseResource[].class);

        if(jElement.isJsonArray()) {
            JsonArray news = jElement.getAsJsonArray();
            for(int i =0;i<newsResponseResource.length;i++){
                        newsList.add(new News(
                                newsResponseResource[i].getTitle(),
                                newsResponseResource[i].getPublishDate(),
                                newsResponseResource[i].getDescription(),
                                newsResponseResource[i].getContent().getMessage(),
                                newsResponseResource[i].getContent().getImageLinks(),
                                newsResponseResource[i].getAuthor().getFirstName() + " " +
                                        newsResponseResource[i].getAuthor().getLastName()

                ));
            }
        }else if(jElement.isJsonObject()){
            JsonObject news = jElement.getAsJsonObject();
            Log.e("ERROR",jsonObj);
            if(news.has("error") && news.get("error").getAsString().equals(INVALID_TOKEN)){
                SplashScreen.oauthClient();
                SplashScreen.getSessionManager().logoutUser();
            }
        }
        return newsList;
    }

    public void parseClientApp(String json){
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        if(!jObject.has("error")){
            SplashScreen.getClientApp().setTokenClient(jObject.get("access_token").getAsString());
        }else{
            Log.e("ERROR",json);
        }

    }

    public void parseUser(String json,User userModel){
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        if(!jObject.has("error")){
            userModel.setToken(jObject.get("access_token").getAsString());
            userModel.setToken_type(jObject.get("token_type").getAsString());
            SplashScreen.getSessionManager().createLoginSession(userModel.getIdnp(),userModel.getPassword(),userModel.getToken());
        }else{
            Log.e("ERROR",json);
            if(jObject.has("error") && jObject.get("error").getAsString().equals(INVALID_TOKEN)){
                SplashScreen.getSessionManager().logoutUser();
            }
        }

    }
}
