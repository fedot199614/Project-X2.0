package com.project.usm.app.Model;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User implements IUser {


    private String email;
    private String password;
    private String token;
    private String name;
    private String lastName;
    private String phone;
    private int id;

    public User(String email,String pass){
        this.email =email;
        this.password = pass;
    }

    @Override
    public boolean validationPhone() {

        return false;
    }


    @Override
    public int isValidData() {
        int code = -1;
        if(TextUtils.isEmpty(getEmail())){
            code = 0;
        }else if(!Pattern.compile("^[0-9]+$").matcher(getEmail()).matches()){
            code = 1;
        }else if(getEmail().length()!=13){
            code = 2;
        }

        //Data Validation
        return code;
    }

}
