package com.project.usm.app.Model;



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
    Map<String,String> data = new HashMap<>();
    private List<String> title = new ArrayList<>();
    private List<String> info = new ArrayList<>();


    public ProfileInfo(UserProfileResponseResource profileResponseResource) {
        this.profileResponseResource = profileResponseResource;
        data.put("Номер группы",profileResponseResource.getGroupId());
        data.put("Форма обучения",String.valueOf(profileResponseResource.getEducationFormType()));
        data.put("Номер студ. книжки",profileResponseResource.getCarnetId());
        data.put("Пол",profileResponseResource.getSex());
        data.put("Адрес",profileResponseResource.getStreetAddress());
        data.put("Email",profileResponseResource.getEmail());
        data.put("Телефон",profileResponseResource.getPhoneNumber());

        Set keySet = data.keySet();
        Iterator<String> iterator = keySet.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();

            title.add(key);
            info.add(data.get(key));
        }
    }

}
