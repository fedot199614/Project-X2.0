package com.project.usm.app.Tools;

import android.app.Activity;
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
import com.project.usm.app.DTO.DayResponseResource;
import com.project.usm.app.DTO.MapPointResponseResource;
import com.project.usm.app.DTO.NewsResponseResource;
import com.project.usm.app.DTO.ScheduleResponseResource;
import com.project.usm.app.DTO.StudentMarkDataResponseResource;
import com.project.usm.app.DTO.SubjectResponseResource;
import com.project.usm.app.DTO.UserProfileResponseResource;
import com.project.usm.app.Fragments.Profile;
import com.project.usm.app.Model.ClientApp;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Model.ScheduleModel;
import com.project.usm.app.Model.User;
import com.project.usm.app.SplashScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappedTypePair;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

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
        Log.e("sdfsdf",jsonObj);
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
                BaseQuery.oauthClient(SplashScreen.getGsonParser());
                //SplashScreen.getSessionManager().logoutUser();
            }
        }
        return newsList;
    }

    public List<ProfileInfo> parseMembers(Activity activity,String jsonObj){

        List<ProfileInfo> membersList = new ArrayList<>();
        JsonElement jElement = new JsonParser().parse(jsonObj);
        Log.e("wwer",jsonObj);
        List<UserProfileResponseResource> newsResponseResourceList = gson.fromJson(jsonObj, new TypeToken<ArrayList<UserProfileResponseResource>>(){}.getType());
        if(jElement.isJsonArray()) {
            for(UserProfileResponseResource element : newsResponseResourceList){
                membersList.add(new ProfileInfo(activity,element));
            }
        }else if(jElement.isJsonObject()){
            JsonObject news = jElement.getAsJsonObject();
            Log.e("ERROR",jsonObj);
            if(news.has("error") && news.get("error").getAsString().equals(INVALID_TOKEN)){
                BaseQuery.oauthClient(SplashScreen.getGsonParser());
                SplashScreen.getSessionManager().logoutUser();
            }
        }
        return membersList;
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


    public Map<Integer,List<SubjectResponseResource>> parseSchedule(String json){
        Map<Integer,List<SubjectResponseResource>> mapSchedule = null;
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        ScheduleResponseResource schedule = null;
        if(!jObject.has("error")){
            schedule = gson.fromJson(json, ScheduleResponseResource.class);
            mapSchedule = new HashMap<>();
            for(DayResponseResource element : schedule.getDays()){
                List<SubjectResponseResource> list2 = element.getSubjects();
                switch(element.getDay()){
                    case "MONDAY":
                        mapSchedule.put(0,list2);
                        break;
                    case "TUESDAY":
                        mapSchedule.put(1,list2);
                        break;
                    case "WEDNESDAY":
                        mapSchedule.put(2,list2);
                        break;
                    case "THURSDAY":
                        mapSchedule.put(3,list2);
                        break;
                    case "FRIDAY":

                        mapSchedule.put(4,list2);
                        break;
                    case "SATURDAY":
                        mapSchedule.put(5,list2);
                        break;
                    case "SUNDAY":
                        mapSchedule.put(6,list2);
                        break;

                }
            };

//            MapperFactory factory = new DefaultMapperFactory.Builder().build();
//            factory.classMap(SubjectResponseResource.class,ScheduleModel.class)
//                    .byDefault()
//                    .customize(new CustomMapper<SubjectResponseResource, ScheduleModel>() {
//                        @Override
//                        public void mapAtoB(SubjectResponseResource subjectResponseResource, ScheduleModel scheduleModel, MappingContext context) {
//                            scheduleModel.setTimeStart(subjectResponseResource.getTime().split(" ")[0]);
//                            scheduleModel.setTimeEnd(subjectResponseResource.getTime().split(" ")[1]);
//                            scheduleModel.setBlock(subjectResponseResource.getLectureRoom().split("/")[1]);
//                            scheduleModel.setCabinetNumber(subjectResponseResource.getLectureRoom().split("/")[0]);
//                        }
//                    }).register();
//
//            factory.classMap(DayResponseResource.class,ScheduleModel.class)
//                    .field("day","day")
//                    .register();
//
//            factory.classMap(ScheduleResponseResource.class,ScheduleModel.class)
//                    .register();
//
//
//            List<ScheduleModel> list = new LinkedList<>();
//
//            MapperFacade facade = factory.getMapperFacade();
//            if(schedule != null) {
//                list = facade.mapAsList(schedule.getDays(), ScheduleModel.class);
//            }
//
//            Log.e("ewrwer",list.get(0).toString());




        }else{

            if(jObject.has("error") && jObject.get("error").getAsString().equals(INVALID_TOKEN)){
                SplashScreen.getSessionManager().logoutUser();
            }
        }


        return  mapSchedule;

    }
    public StudentMarkDataResponseResource parseMarks(String json){
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        StudentMarkDataResponseResource markData = null;
        if(!jObject.has("error")){
            markData = gson.fromJson(json, StudentMarkDataResponseResource.class);
        }else{
            if(jObject.has("error") && jObject.get("error").getAsString().equals(INVALID_TOKEN)){
                SplashScreen.getSessionManager().logoutUser();
            }
        }


        return  markData;

    }

    public List<MapPointResponseResource> parseMap(String json){
        JsonElement jElement = new JsonParser().parse(json);
        //JsonObject jObject = jElement.getAsJsonObject();


        List<MapPointResponseResource>   mapData = gson.fromJson(json, new TypeToken<LinkedList<MapPointResponseResource>>(){}.getType());

        return  mapData;

    }

    public String parseImgUrl(String json){

        JsonElement element = new JsonParser().parse(json);
        JsonObject obj = element.getAsJsonObject();
        String res = obj.get("data").getAsJsonObject().get("link").getAsString();

        return res;
    }


    public ProfileInfo parseProfile(Activity activity,Boolean update,String json){

        JsonElement element = new JsonParser().parse(json);
        JsonObject obj = element.getAsJsonObject();
        ProfileInfo profileInfo = null;
        if(!obj.has("error")) {
            if(!update) {
                UserProfileResponseResource profile = gson.fromJson(json, UserProfileResponseResource.class);
                profileInfo = new ProfileInfo(activity,profile);
            }else{

                UserProfileResponseResource profile = gson.fromJson(json, UserProfileResponseResource.class);
                SplashScreen.getProfileInfo().setProfileResponseResource(profile);
                SplashScreen.getProfileInfo().updateData();

            }
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
            if(!update) {
                UserProfileResponseResource profile = gson.fromJson(jsonResponseProfile, UserProfileResponseResource.class);
                profileInfo = new ProfileInfo(activity,profile);
            }else{
                UserProfileResponseResource profile = gson.fromJson(jsonResponseProfile, UserProfileResponseResource.class);
                SplashScreen.getProfileInfo().setProfileResponseResource(profile);
                SplashScreen.getProfileInfo().updateData();

            }

        }
        return profileInfo;
    }
}
