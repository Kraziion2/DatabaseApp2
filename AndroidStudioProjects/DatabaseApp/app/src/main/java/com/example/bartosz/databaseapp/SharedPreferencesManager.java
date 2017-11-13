package com.example.bartosz.databaseapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bartosz on 08/11/2017.
 */

public class SharedPreferencesManager {
    private static SharedPreferencesManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREFERENCE_NAME = "mysharedpref12";
    private static  final  String KEY_USER_NAME = "username";
    private static  final  String KEY_USER_EMAIL = "useremail";
    private static  final  String KEY_USER_ID = "userid";

    private SharedPreferencesManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_NAME,username);

        editor.apply();
        return true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_NAME, null) !=null){
            return true;
        }
        return false;
    }

    public  boolean logOut(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

public String getUsername()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_USER_NAME, null);
}

public String getUserEmail()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_USER_EMAIL,null);
}

}//end of class
