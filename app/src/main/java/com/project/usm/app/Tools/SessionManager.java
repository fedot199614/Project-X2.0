package com.project.usm.app.Tools;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.project.usm.app.MainActivity;

public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private Editor editor;
    // Context
    private Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_IDNP = "idnp";
    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_GROUP = "grID";
    public static final String KEY_LOCALIZATION = "LOCAL";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name, String email,String token) {
        // Storing login value as TRUE

        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_IDNP, name);
        // Storing email in pref
        editor.putString(KEY_PASSWORD, email);
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }
    public void updateUserToken(String token) {

        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }
    public void addUserGroup(String group) {

        editor.putString(KEY_GROUP, group);

        // commit changes
        editor.commit();
    }
    public void setLocalizationApp(String localization) {

        editor.putString(KEY_LOCALIZATION, localization);

        // commit changes
        editor.commit();
    }

    public Boolean isSessionContainLocalizationValue(){
        if(pref.contains(KEY_LOCALIZATION)){
            return true;
        }else{
            return false;
        }
    }

    public String getLocalizationApp() {

       return pref.getString(KEY_LOCALIZATION,null);
        // commit changes

    }
    /**
     * Check login method wil check user login status If false it will redirect
     * user to login page Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_IDNP, pref.getString(KEY_IDNP, null));

        // user email id
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_GROUP, pref.getString(KEY_GROUP, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}