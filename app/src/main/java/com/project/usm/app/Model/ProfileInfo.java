package com.project.usm.app.Model;



import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.project.usm.app.DTO.UserProfileResponseResource;

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

    private UserProfileResponseResource profileResponseResource;
    Map<String,String> data = new LinkedHashMap<>();
    private List<String> title;
    private Map<String,Boolean> editable = new LinkedHashMap<>();
    private List<String> info;
    private Bitmap avatar;

    public void updateData(){
        data.put("Адрес",this.profileResponseResource.getStreetAddress()); editable.put("streetAddress",true);
        data.put("Email",this.profileResponseResource.getEmail()); editable.put("email",true);
        data.put("Телефон",this.profileResponseResource.getPhoneNumber()); editable.put("phoneNumber",true);
        data.put("Номер группы",this.profileResponseResource.getGroupId()); editable.put("1",false);
        data.put("Форма обучения",String.valueOf(this.profileResponseResource.getEducationFormType())); editable.put("2",false);
        data.put("Номер студ. книжки",this.profileResponseResource.getCarnetId()); editable.put("3",false);
        data.put("Пол",this.profileResponseResource.getSex()); editable.put("4",false);
        title = new LinkedList<>();
        info = new LinkedList<>();

        Set keySet = data.keySet();
        Iterator<String> iterator = keySet.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();

            title.add(key);
            info.add(data.get(key));
        }
    }
    public ProfileInfo(UserProfileResponseResource profileResponseResource) {
        this.profileResponseResource = profileResponseResource;
        data.put("Адрес",this.profileResponseResource.getStreetAddress()); editable.put("streetAddress",true);
        data.put("Email",this.profileResponseResource.getEmail()); editable.put("email",true);
        data.put("Телефон",this.profileResponseResource.getPhoneNumber()); editable.put("phoneNumber",true);
        data.put("Номер группы",this.profileResponseResource.getGroupId()); editable.put("1",false);
        data.put("Форма обучения",String.valueOf(this.profileResponseResource.getEducationFormType())); editable.put("2",false);
        data.put("Номер студ. книжки",this.profileResponseResource.getCarnetId()); editable.put("3",false);
        data.put("Пол",this.profileResponseResource.getSex()); editable.put("4",false);
        title = new LinkedList<>();
        info = new LinkedList<>();


        Set keySet = data.keySet();
        Iterator<String> iterator = keySet.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();

            title.add(key);
            info.add(data.get(key));
        }
    }

}
