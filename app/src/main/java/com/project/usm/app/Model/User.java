package com.project.usm.app.Model;

import android.text.TextUtils;
import android.util.Patterns;

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
        }else if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()){
            code = 1;
        }else if(getPassword().length()<6){
            code = 2;
        }

        //Data Validation
        return code;
    }

}
