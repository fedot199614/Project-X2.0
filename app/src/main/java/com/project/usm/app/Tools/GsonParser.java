package com.project.usm.app.Tools;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.project.usm.app.DTO.NewsResponseResource;
import com.project.usm.app.DTO.UserProfileResponseResource;
import com.project.usm.app.Fragments.Profile;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Model.User;
import com.project.usm.app.SplashScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class GsonParser {
    private static User user;
    private static final GsonParser gsonParser = new GsonParser();
    private static final String INVALID_TOKEN = "invalid_token";
    private static Gson gson = SplashScreen.getComponent().getGson();
    private GsonParser(){

    }

    public static GsonParser getNewInstance(){
        return gsonParser;
    }

    public List<News> parseNews(String jsonObj){

        List<News> newsList = new ArrayList<>();
        JsonElement jElement = new JsonParser().parse(jsonObj);
        List<NewsResponseResource> newsResponseResourceList = gson.fromJson(jsonObj, new TypeToken<ArrayList<NewsResponseResource>>(){}.getType());
        if(jElement.isJsonArray()) {
            for(NewsResponseResource element : newsResponseResourceList){
                newsList.add(new News(
                        element.getId(),
                        element.getTitle(),
                        element.getPublishDate(),
                        element.getDescription(),
                        element.getContent().getMessage(),
                        element.getContent().getImageLinks(),
                        element.getAuthor().getFirstName() + " " +
                                element.getAuthor().getLastName()

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
        this.user = userModel;
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




    public ProfileInfo parseProfile(String json){
        JsonElement element = new JsonParser().parse(json);
        JsonObject obj = element.getAsJsonObject();
        ProfileInfo profileInfo;
        if(!obj.has("error")) {
            UserProfileResponseResource profile = gson.fromJson(json, UserProfileResponseResource.class);
             profileInfo = new ProfileInfo(profile);
        }else{
            if(SplashScreen.getSessionManager().isLoggedIn()){
                this.user = new User(SplashScreen.getSessionManager().getUserDetails().get("idnp"),SplashScreen.getSessionManager().getUserDetails().get("password"));
            }
            List<BasicNameValuePair> clientParamPlusUserParam = new ArrayList<>();
            clientParamPlusUserParam.addAll(user.getParamsClient());
            clientParamPlusUserParam.addAll(user.getParams());
            SplashScreen.getHttpClient().buildTaskPost().oauth().postRequestBuild(user.getHeaders(), clientParamPlusUserParam).getTaskPost().execute();
            try {
                String response = SplashScreen.getHttpClient().getTaskPost().get();
                JsonElement jElement = new JsonParser().parse(response);
                JsonObject jObject = jElement.getAsJsonObject();
                SplashScreen.getSessionManager().updateUserToken(jObject.get("access_token").getAsString());
            }catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
            }

            String jsonResponseProfile = "";
            SplashScreen.getHttpClient().buildTaskGet().profile()
                    .getRequestBuild(new Header[]{new BasicHeader("Authorization",
                            "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token"))})
                    .getTaskGet().execute();
            try {
                jsonResponseProfile = SplashScreen.getHttpClient().getTaskGet().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            UserProfileResponseResource profile = gson.fromJson(jsonResponseProfile, UserProfileResponseResource.class);
            profileInfo = new ProfileInfo(profile);
        }
        return profileInfo;
    }
}
