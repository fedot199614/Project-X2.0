package com.project.usm.app.Tools;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.usm.app.DTO.MapPointResponseResource;
import com.project.usm.app.DTO.StudentMarkDataResponseResource;
import com.project.usm.app.DTO.SubjectResponseResource;
import com.project.usm.app.DTO.UserProfileOneElement;
import com.project.usm.app.Fragments.GroupMembers;
import com.project.usm.app.Fragments.Marks;
import com.project.usm.app.Fragments.Profile;
import com.project.usm.app.MainActivity;
import com.project.usm.app.Model.News;
import com.project.usm.app.Model.ProfileInfo;
import com.project.usm.app.Model.User;
import com.project.usm.app.Presenter.HomeNews;
import com.project.usm.app.R;
import com.project.usm.app.SplashScreen;
import com.project.usm.app.View.Auth_View;
import com.project.usm.app.View.Home_View;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpClient {
    private CloseableHttpClient client;
    private List<NameValuePair> params;
    private String groupMembersService;
    private String url;
    private String port;
    private String absoluteUrl;
    private HttpPut httpPut;
    private HttpPost httpPost;
    private HttpGet httpGet;
    private String updateService;
    private String imgService;
    private String response;
    private String oauthService;
    private String newsService;
    private String profileService;
    private String scheduleService;
    private String markService;
    private String mapService;
    private MyTaskPost taskPost;
    private MyTaskPostAuth taskPostAuth;
    private MyTaskGet taskGet;
    private MyTaskGetImg taskGetImg;
    private MyTaskPut taskPut;
    private MyTaskPostImg taskPostImg;
    private MyTaskGetGroupMember taskGetGroupMember;
    private MyTaskGetSchedule taskSchedule;
    private MyTaskGetMarks taskMarks;
    private MyTaskGetMap taskMap;
    private MyTaskGetNews taskGetNews;
    private MyTaskGetProfile taskGetProfile;

    @Inject
public HttpClient(String url,String port){
    this.url = url;
    this.port = port;
    this.client = HttpClients.createDefault();
    this.params = new ArrayList<>();
    this.oauthService = "oauth/token";
    this.newsService = "news";
    this.profileService = "users/profile/";
    this.groupMembersService = "users/query";
    this.updateService = "users/update";
    this.imgService = "https://api.imgur.com/3/upload";
    this.scheduleService = "schedule/week";
    this.markService = "marks";
    this.mapService = "map/points";
}

public HttpClient buildTaskGetImg(){
        this.taskGetImg = new MyTaskGetImg();
        return this;
    }
    public HttpClient buildTaskGetSchedule(RecyclerView rv,Activity activity){
        this.taskSchedule = new MyTaskGetSchedule(rv,activity);
        return this;
    }
    public HttpClient buildTaskGetMarks(RecyclerView rv,Activity activity){
        this.taskMarks = new MyTaskGetMarks(rv,activity);
        return this;
    }

    public HttpClient buildTaskGetMap(GoogleMap map,Activity activity,Marker marker){
        this.taskMap = new MyTaskGetMap(activity,map,marker);
        return this;
    }

 public HttpClient buildTaskPostImg(Bitmap bitmap,Activity activity,ProfileInfo profileInfo){
        this.taskPostImg = new MyTaskPostImg(bitmap,activity,profileInfo);
        return this;
    }

    public HttpClient buildTaskPostAuth(Activity activity, Auth_View auth_view, User user){
        this.taskPostAuth = new MyTaskPostAuth(activity,auth_view,user);
        return this;
    }

public HttpClient buildTaskPost(){
    this.taskPost = new MyTaskPost();
    return this;
}

public HttpClient buildTaskGet(){
    this.taskGet = new MyTaskGet();
    return this;
}

public HttpClient buildTaskGetProfile(Activity activity,RecyclerView rv,Fragment fragment){
        this.taskGetProfile = new MyTaskGetProfile(activity,rv,fragment);
        return this;
    }
public HttpClient buildTaskGetNews(HomeNews presenter,Activity activity,Fragment fragment,Home_View view){
        this.taskGetNews = new MyTaskGetNews(presenter,activity,fragment,view);
        return this;
    }

    public HttpClient buildTaskGetGroupMember(RecyclerView rv, Activity activity) {
        this.taskGetGroupMember = new MyTaskGetGroupMember(rv,activity);
        return this;
    }

    public HttpClient buildTaskPut(Activity activity){
        this.taskPut = new MyTaskPut(activity);
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

public HttpClient profile(){
        this.absoluteUrl = this.url+":"+this.port+"/"+this.profileService;
        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
}

public HttpClient update(String queryName,String query){
    this.absoluteUrl = this.url+":"+this.port+"/"+this.updateService;
    if(!this.absoluteUrl.endsWith("?")) {
        this.absoluteUrl += "?";
    }
    List<NameValuePair> params = new LinkedList<NameValuePair>();
    params.add(new BasicNameValuePair(queryName, query));
    String paramString = URLEncodedUtils.format(params, "utf-8");
    this.absoluteUrl+=paramString;
    this.httpPut = new HttpPut(absoluteUrl);
    this.httpPost = new HttpPost(absoluteUrl);
    this.httpGet = new HttpGet(absoluteUrl);
    return this;
}
    public HttpClient getSchedule(String query){
        this.absoluteUrl = this.url+":"+this.port+"/"+this.scheduleService;
        if(!this.absoluteUrl.endsWith("?")) {
            this.absoluteUrl += "?";
        }
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("groupId", query));
        String paramString = URLEncodedUtils.format(params, "utf-8");
        this.absoluteUrl+=paramString;
        this.httpPut = new HttpPut(absoluteUrl);
        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
    }
    public HttpClient getRequestSchedule(){
        this.absoluteUrl = this.url+":"+this.port+"/"+this.markService;
        this.httpPut = new HttpPut(absoluteUrl);
        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
    }

    public HttpClient getRequestMap(){
        this.absoluteUrl = this.url+":"+this.port+"/"+this.mapService;
        this.httpPut = new HttpPut(absoluteUrl);
        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
    }

    public HttpClient imgPostIntoService(){
        this.absoluteUrl = this.imgService;
        this.httpPost = new HttpPost(absoluteUrl);
        return this;
    }

public HttpClient membersService(String query){

        this.absoluteUrl = this.url+":"+this.port+"/"+this.groupMembersService;
        if(!this.absoluteUrl.endsWith("?")) {
            this.absoluteUrl += "?";
        }
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("groupId", query));
        String paramString = URLEncodedUtils.format(params, "utf-8");
        this.absoluteUrl+=paramString;

        this.httpPost = new HttpPost(absoluteUrl);
        this.httpGet = new HttpGet(absoluteUrl);
        return this;
}

public HttpClient customEndPoint(String endpoint){
        this.absoluteUrl = endpoint;
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


public HttpClient postRequestBuild(Header[] headers){
        httpPost.setHeaders(headers);
        return this;
}
    public HttpClient putRequestBuild(Header[] headers){
        httpPut.setHeaders(headers);
        return this;
    }


public void flushData(){
    params.clear();

}

public void closeClient() throws IOException {
    client.close();
}



    public class MyTaskPostAuth extends AsyncTask<String, Void, String>{

        Activity activity;
        Auth_View auth_view;
        User user;
        private ProgressDialog dialog;
        public MyTaskPostAuth(Activity activity, Auth_View auth_view, User user){
            this.activity = activity;
            this.auth_view = auth_view;
            this.user = user;
            this.dialog = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            Log.d("LOG_TAG", "BeginPostAuth");
            dialog.setMessage(activity.getString(R.string.autorizationWait));
            dialog.show();
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
            SplashScreen.getGsonParser().parseUser(result,user);

            if(SplashScreen.getSessionManager().isLoggedIn()){
                auth_view.onLoginSuccessfully();
                auth_view.initAuthState();
                auth_view.initHomePage();
                SplashScreen.setProfileInfo(BaseQuery.profileQuery(activity));
                //BaseQuery.membersGroupQuery(SplashScreen.getGsonParser(),SplashScreen.getProfileInfo().getProfileResponseResource().getGroupId());
                Picasso.get().load(SplashScreen.getProfileInfo().getProfileResponseResource().getProfileImageUrl()).into(MainActivity.getNavManager().getNavProfImg());
                MainActivity.getNavManager().getName().setText(SplashScreen.getProfileInfo().getProfileResponseResource().getFirstName()+" "+SplashScreen.getProfileInfo().getProfileResponseResource().getLastName());
                MainActivity.getNavManager().getSomeInfo().setText(SplashScreen.getProfileInfo().getProfileResponseResource().getSpeciality());
                SplashScreen.getSessionManager().setProfileData(SplashScreen.getProfileInfo().getProfileResponseResource().getProfileImageUrl()
                                                                ,SplashScreen.getProfileInfo().getProfileResponseResource().getFirstName()+" "+SplashScreen.getProfileInfo().getProfileResponseResource().getLastName()
                                                                ,SplashScreen.getProfileInfo().getProfileResponseResource().getSpeciality());
            }else{
                auth_view.onLoginMessageError();
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

public class MyTaskPost extends AsyncTask<String, Void, String>{




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
    public class MyTaskPut extends AsyncTask<String, Void, String>{

        Activity activity;
        public MyTaskPut(Activity act){
            this.activity = act;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d("LOG_TAG", "BeginPut");
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = "";
            CloseableHttpResponse httpResponse = null;
            try {

                httpResponse = client.execute(httpPut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bf = null;
            try {

                bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
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
            SplashScreen.setProfileInfo(SplashScreen.getGsonParser().parseProfile(activity,result));
            Toast.makeText(MainActivity.getNavManager().getContext(),activity.getString(R.string.save),Toast.LENGTH_SHORT).show();

        }

    }


    public class MyTaskPostImg extends AsyncTask<String, Void, String>{
        File file;
        Activity activity;
        Bitmap bitmap;
        ProgressDialog dialog;
        String path_external = Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg";
        ProfileInfo profileInfo;
        public MyTaskPostImg(Bitmap bitmap, Activity activity, ProfileInfo profileInfo) {
            this.bitmap = bitmap;
            this.activity= activity;
            this.profileInfo = profileInfo;
            this.dialog = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.show();
            Log.d("LOG_TAG", "BeginPut");
        }

        @Override
        protected String doInBackground(String... strings) {

            //String[] array = profileInfo.getProfileResponseResource().getProfileImageUrl().replace("//","/").split("/");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            file = new File(path_external);
            try {
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.flush();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            byte[] byteArray = bytes.toByteArray();
//            String str = "";
//            try {
//                str = new String(byteArray, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            String res = "";
            CloseableHttpResponse httpResponse = null;
            try {

                HttpEntity entity = MultipartEntityBuilder.create()
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addBinaryBody("image",file)
                        .addTextBody("type", "file")
                        .addTextBody("name", "upload.jpg")
                        .build();
                httpPost.setEntity(entity);
                httpResponse = client.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bf = null;
            String urlImg = "";
            try {

                bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                res = bf.readLine();
                Log.e("ertertert",res);
                urlImg = SplashScreen.getGsonParser().parseImgUrl(res);

                Log.e("ertertert23",urlImg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            absoluteUrl = url+":"+port+"/"+updateService;
            if(!absoluteUrl.endsWith("?")) {
                absoluteUrl += "?";
            }
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("profileImageUrl", urlImg));
            String paramString = URLEncodedUtils.format(params, "utf-8");
            absoluteUrl+=paramString;
            httpPut = new HttpPut(absoluteUrl);
            httpPut.setHeader(new BasicHeader("Authorization",
                    "Bearer "+SplashScreen.getSessionManager().getUserDetails().get("token")));
            try {
                httpResponse = client.execute(httpPut);
                bf = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                res = bf.readLine();
                Log.e("ertertert12431",res);
            } catch (IOException e) {
                e.printStackTrace();
            }
            flushData();
            return res;
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
            //Log.e("ertertert",result);
            SplashScreen.setProfileInfo(SplashScreen.getGsonParser().parseProfile(activity,result));
            dialog.dismiss();
            Toast.makeText(MainActivity.getNavManager().getContext(),activity.getString(R.string.save),Toast.LENGTH_SHORT).show();


        }

    }
    public class MyTaskGetGroupMember extends AsyncTask<String, Void, String>{



        Activity activity;
        RecyclerView rv;
        ProgressDialog pr;
        public  MyTaskGetGroupMember(RecyclerView rv,Activity activity){
            this.activity = activity;
            this.rv = rv;
            this.pr = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pr.setMessage(activity.getString(R.string.updateData));
            pr.show();
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
            SplashScreen.setProfileInfoList(SplashScreen.getGsonParser().parseMembers(activity,result));

            List<ProfileInfo> profInfo  = SplashScreen.getProfileInfoList();
            RVAdapterGroupMembers adapter = new RVAdapterGroupMembers(profInfo);
            rv.setAdapter(adapter);

            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(rv.getContext(), R.anim.layout_animation_fall_down);
            rv.setLayoutAnimation(controller);
            rv.getAdapter().notifyDataSetChanged();
            rv.scheduleLayoutAnimation();
            rv.addOnItemTouchListener(new RecyclerItemClickListener(activity, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(activity.getString(R.string.info));
                    List<UserProfileOneElement> data = profInfo.get(position).getData();
                    String res = "";
                    for(int i=0;i<data.size();i++){
                        res += data.get(i).getName()+": "+data.get(i).getResponseResult()+"\n";
                    }
                    alertDialog.setMessage(res);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));
            pr.dismiss();
        }
    }
    public class MyTaskGetProfile extends AsyncTask<String, Void, String>{

        Fragment fragment;
        Activity activity;
        ProgressDialog dialog;
        RecyclerView rv;
        public MyTaskGetProfile(Activity activity,RecyclerView rv,Fragment fragment){
            this.activity = activity;
            this.dialog = new ProgressDialog(activity);
            this.rv = rv;
            this.fragment = fragment;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.show();
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
                res = bf.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

            flushData();
            return res;
        }


        public void beginTransaction(FragmentManager frManager, Fragment nextFragment, String backStackTag,String idGroup) {
            FragmentTransaction transaction = frManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("grId", idGroup);
            nextFragment.setArguments(bundle);
            transaction.addToBackStack(backStackTag);
            transaction.replace(R.id.mainFrame,nextFragment).commit();
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
            SplashScreen.setProfileInfo(SplashScreen.getGsonParser().parseProfile(activity,result));
            ProfileInfo profInfo  = SplashScreen.getProfileInfo();

            //setData(profInfo);


            CircleImageView profImg = (CircleImageView) activity.findViewById(R.id.profile_image);
            CircleImageView navProfImg = NavigationViewManager.getNewInstance().getNavProfImg();
            TextView name = (TextView) activity.findViewById(R.id.profile_name_lastname);
            TextView profile_group = (TextView) activity.findViewById(R.id.profile_group);
            TextView faculty = (TextView) activity.findViewById(R.id.profile_faculty);
            TextView navName = NavigationViewManager.getNewInstance().getName();

            name.setText(profInfo.getProfileResponseResource().getFirstName()+" "+profInfo.getProfileResponseResource().getLastName());
            navName.setText(profInfo.getProfileResponseResource().getFirstName()+" "+profInfo.getProfileResponseResource().getLastName());
            profile_group.setText(profInfo.getProfileResponseResource().getGroupId());
            faculty.setText(profInfo.getProfileResponseResource().getFaculty());


            Picasso.get().load(profInfo.getProfileResponseResource().getProfileImageUrl()).into(profImg);
            Picasso.get().load(profInfo.getProfileResponseResource().getProfileImageUrl()).into(navProfImg);

            RVAdapterProfileInfo adapter = new RVAdapterProfileInfo(profInfo,activity);
            rv.setAdapter(adapter);

            TextView textGroup = (TextView)activity.findViewById(R.id.profile_group);
            textGroup.setOnClickListener(click->{
                Toast.makeText(activity,activity.getString(R.string.loading),Toast.LENGTH_SHORT).show();
                GroupMembers sn = new GroupMembers();
                beginTransaction(fragment.getFragmentManager(),sn,"membersGroup",textGroup.getText().toString());
            });

            TextView textMarks = (TextView) activity.findViewById(R.id.my_marks);
            textMarks.setOnClickListener(click->{
                Toast.makeText(activity,activity.getString(R.string.loading),Toast.LENGTH_SHORT).show();
                Marks sn = new Marks();
                beginTransaction(fragment.getFragmentManager(),sn,"marks",textGroup.getText().toString());
            });
            dialog.dismiss();
        }
    }

    public class MyTaskGet extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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

    public class MyTaskGetNews extends AsyncTask<String, Void, String>{
        ProgressDialog dialog;
        Activity activity;
        Home_View view;
        Fragment fragment;
        HomeNews homePresenter;
        public MyTaskGetNews(HomeNews presenter,Activity activity, Fragment fragment,Home_View view) {
            this.activity = activity;
            this.dialog = new ProgressDialog(activity);
            this.view = view;
            this.fragment = fragment;
            this.homePresenter = presenter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.show();
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
            Log.e("sdsdfd234s",result);
            List<News> list = SplashScreen.getGsonParser().parseNews(result);
            SplashScreen.setNewsList(list);
            homePresenter.Init();
            homePresenter.setAnimFade(fragment,activity);
            dialog.dismiss();
        }
    }

    public class MyTaskGetImg extends AsyncTask<String, Void, Bitmap>{






        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LOG_TAG", "BeginGet");
        }



        @Override
        protected Bitmap doInBackground(String... strings) {
            HttpResponse httpResponse;
            Bitmap bmp = null;

            try{
                httpResponse = client.execute(httpGet);
                // responseCode = httpResponse.getStatusLine().getStatusCode();

                HttpEntity entity = httpResponse.getEntity();


                if (entity != null)
                {
                    InputStream in = entity.getContent();
                    bmp = BitmapFactory.decodeStream(in);
                    in.close();
                }
            } catch (ClientProtocolException e)  {
                client.getConnectionManager().shutdown();
                e.printStackTrace();
            } catch (IOException e) {
                client.getConnectionManager().shutdown();
                e.printStackTrace();
            }


            flushData();
            return bmp;
        }


        @Override
        protected  void onPostExecute(Bitmap result){
            super.onPostExecute(result);
        }
    }

    public class MyTaskGetSchedule extends AsyncTask<String, Void, String> implements TabLayout.OnTabSelectedListener{

        Activity activity;
        ProgressDialog dialog;
        RecyclerView rv;
        RVAdapterSchedule adapter;
        Map<Integer,List<SubjectResponseResource>> map;
        public MyTaskGetSchedule(RecyclerView rv,Activity activity){
            this.activity = activity;
            this.dialog = new ProgressDialog(activity);
            this.rv = rv;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.show();
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
                res = bf.readLine();
                Log.e("dsasadasd",res);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flushData();
            return res;
        }
        public void runLayoutAnimation(){
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(rv.getContext(), R.anim.layout_animation_fall_down);
            rv.setLayoutAnimation(controller);
            rv.getAdapter().notifyDataSetChanged();
            rv.scheduleLayoutAnimation();
        }

        @Override
        protected  void onPostExecute(String result){
            super.onPostExecute(result);
            TabLayout tabBar = (TabLayout) activity.findViewById(R.id.tabLayout);
            tabBar.setOnTabSelectedListener(this);

            map = SplashScreen.getGsonParser().parseSchedule(result);
            if(!map.get(tabBar.getSelectedTabPosition()).isEmpty()){
                adapter = new RVAdapterSchedule(map.get(tabBar.getSelectedTabPosition()));
                rv.setAdapter(adapter);

            }else{
                RVAdapterNoData adapter = new RVAdapterNoData(activity);
                rv.setAdapter(adapter);
            }


//            Set<Integer> keys = map.keySet();
//
//            for(Integer key : keys){
//                if(!map.get(key).isEmpty()){
//                    adapter = new RVAdapterSchedule(map.get(key));
//                    rv.setAdapter(adapter);
//                    break;
//                }
//            }



            dialog.dismiss();
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            runLayoutAnimation();
            if(map.get(tab.getPosition()).isEmpty()){
                RVAdapterNoData adapter = new RVAdapterNoData(activity);
                rv.setAdapter(adapter);
            }else {
                adapter = new RVAdapterSchedule(map.get(tab.getPosition()));
                rv.setAdapter(adapter);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }


    public class MyTaskGetMarks extends AsyncTask<String, Void, RVAdapterMarks>{

        Activity activity;
        ProgressDialog dialog;
        RecyclerView rv;

        Map<Integer,List<SubjectResponseResource>> map;
        public MyTaskGetMarks(RecyclerView rv,Activity activity){
            this.activity = activity;
            this.dialog = new ProgressDialog(activity);
            this.rv = rv;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.show();
            Log.d("LOG_TAG", "BeginGet");
        }



        @Override
        protected RVAdapterMarks doInBackground(String... strings) {
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
                res = bf.readLine();
                Log.e("marks",res);
            } catch (IOException e) {
                e.printStackTrace();
            }

            StudentMarkDataResponseResource markData = SplashScreen.getGsonParser().parseMarks(res);
            RVAdapterMarks adapter = new RVAdapterMarks(markData.getYears(),activity);


            flushData();
            return adapter;
        }
        public void runLayoutAnimation(){
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(rv.getContext(), R.anim.layout_animation_fall_down);
            rv.setLayoutAnimation(controller);
            rv.getAdapter().notifyDataSetChanged();
            rv.scheduleLayoutAnimation();
        }

        @Override
        protected  void onPostExecute(RVAdapterMarks result){
            super.onPostExecute(result);
            rv.setAdapter(result);
            runLayoutAnimation();
            dialog.dismiss();
        }
    }



    public class MyTaskGetMap extends AsyncTask<String, Void, List<MapPointResponseResource>> implements GoogleMap.OnInfoWindowClickListener{

        Activity activity;
        ProgressDialog dialog;
        GoogleMap map;
        FusedLocationProviderClient clientLocation;
        private Marker markerStart;
        List<Polyline> polylines;
        public MyTaskGetMap(Activity activity,GoogleMap map,Marker markerStart){
            this.activity = activity;
            this.dialog = new ProgressDialog(activity);
            this.map = map;
            this.markerStart = markerStart;
            this.polylines = new ArrayList<>();
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(activity.getString(R.string.loading));
            dialog.show();
            Log.d("LOG_TAG", "BeginGet");
        }



        @Override
        protected List<MapPointResponseResource> doInBackground(String... strings) {
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
                res = bf.readLine();
                Log.e("map",res);
            } catch (IOException e) {
                e.printStackTrace();
            }

           List<MapPointResponseResource>  mapData = SplashScreen.getGsonParser().parseMap(res);



            flushData();
            return mapData;
        }


        @Override
        protected  void onPostExecute(List<MapPointResponseResource> result){
            super.onPostExecute(result);
            map.setOnInfoWindowClickListener(this);
            for(MapPointResponseResource mapPoint : result){
                MarkerOptions markerOptions =  new MarkerOptions()
                        .position(new LatLng(mapPoint.getLatitude(), mapPoint.getLongitude()))
                        .snippet("Address: "+mapPoint.getAddress())
                        .title(mapPoint.getTitle());

                Marker sd = map.addMarker(markerOptions);

            }
            dialog.dismiss();
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            Log.e("dfsdfsd",String.valueOf(marker.getPosition()));
        }

    }

}
