package com.project.usm.app.Model;



import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;
import android.widget.ImageView;

import com.project.usm.app.DTO.UserProfileOneElement;
import com.project.usm.app.DTO.UserProfileResponseResource;
import com.project.usm.app.R;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInfo {

    private final Activity activity;
    private UserProfileResponseResource profileResponseResource;
    private List<UserProfileOneElement> data = new LinkedList<>();

//    public void updateData(){
//        data.clear();
//        data.add(new UserProfileOneElement(activity.getString(R.string.adress),"streetAddress",true,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getStreetAddress()));
//        data.add(new UserProfileOneElement("Email","email",true,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getEmail()));
//        data.add(new UserProfileOneElement(activity.getString(R.string.phone),"phoneNumber",true,InputType.TYPE_CLASS_PHONE,this.profileResponseResource.getPhoneNumber()));
//        data.add(new UserProfileOneElement(activity.getString(R.string.group)," ",false,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getGroupId()));
//        data.add(new UserProfileOneElement(activity.getString(R.string.form)," ",false,InputType.TYPE_CLASS_TEXT,String.valueOf(this.profileResponseResource.getEducationFormType())));
//        data.add(new UserProfileOneElement(activity.getString(R.string.card)," ",false,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getCarnetId()));
//    }
    public ProfileInfo(Activity activity,UserProfileResponseResource profileResponseResource) {
        this.activity = activity;
        this.profileResponseResource = profileResponseResource;
        data.add(new UserProfileOneElement(activity.getString(R.string.adress),"streetAddress",true,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getStreetAddress()));
        data.add(new UserProfileOneElement("Email","email",true,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getEmail()));
        data.add(new UserProfileOneElement(activity.getString(R.string.phone),"phoneNumber",true,InputType.TYPE_CLASS_PHONE,this.profileResponseResource.getPhoneNumber()));
        data.add(new UserProfileOneElement(activity.getString(R.string.group)," ",false,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getGroupId()));
        data.add(new UserProfileOneElement(activity.getString(R.string.form)," ",false,InputType.TYPE_CLASS_TEXT,String.valueOf(this.profileResponseResource.getEducationFormType())));
        data.add(new UserProfileOneElement(activity.getString(R.string.card)," ",false,InputType.TYPE_CLASS_TEXT,this.profileResponseResource.getCarnetId()));
    }

}
