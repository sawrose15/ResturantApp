package com.sawrose.resturant.SharedPrefrences;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by sawrose on 10/4/16.
 */

public class GlobalState extends Application {
    SharedPreferences Login;
    SharedPreferences.Editor LoginEditor;

    SharedPreferences userDetail;
    SharedPreferences.Editor userDetailEditor;


    public static GlobalState singleton;

    public static final String PREFS_IS_LOGGED_IN = "is logged in"; // to check if user is logged in

    public  static final  String PREFS_USER_DETAIL = "is saved in"; // used to save user information for login

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();

        Login = this.getSharedPreferences(PREFS_IS_LOGGED_IN,0);
        LoginEditor= Login.edit();

        userDetail =this.getSharedPreferences(PREFS_USER_DETAIL,0);
        userDetailEditor= userDetail.edit();

        singleton =this;
    }

    /**
     * @return MySIngleton instance
     */
    public GlobalState getInstance() {
        return singleton;
    }

    public String getPrefsCheckLogin(){
        return Login.getString(PREFS_IS_LOGGED_IN,"");
    }

    public void setPrefsCheckLogin(String value, int resultCode){
        if(resultCode==1){
            LoginEditor.putString(PREFS_IS_LOGGED_IN,value).commit();
        }else {
            LoginEditor.remove(PREFS_IS_LOGGED_IN).commit();
        }
    }

    public String getPrefsSaveDetail(){
        return userDetail.getString(PREFS_USER_DETAIL,"");
    }

    public void setPrefsUserDetail(String value, int resultCode){
        if (resultCode==1){
            userDetailEditor.putString(PREFS_USER_DETAIL,value).commit();
        }
        else {
            userDetailEditor.remove(PREFS_USER_DETAIL).commit();
        }
    }

}
