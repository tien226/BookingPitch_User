package com.mobile.bookingpitch_user.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mobile.bookingpitch_user.activity.SelectLoginActivity;

public class SharedPref_RC {
    //Storage File
    public static final String SHARED_PREF_NAME = "homangtrang";

    //Username
    public static final String UserToken_RC = "user_token";
    public static final String UserName_RC = "user_name";
    public static final String UserEmail_RC = "user_email";
    public static final String UserAvatar_RC = "user_avatar";

    @SuppressLint("StaticFieldLeak")
    public static SharedPref_RC mInstance;

    @SuppressLint("StaticFieldLeak")
    public static Context mCtx;

    public SharedPref_RC(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref_RC getInstance_RC(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref_RC(context);
        }
        return mInstance;
    }


    //method to store user data
    public void storeUserToKen_RC(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserToken_RC, token);
        editor.apply();
    }

    public void storeUserName_RC(String name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserName_RC, name);
        editor.apply();
    }

    public void storeUserEmail_RC(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserEmail_RC, email);
        editor.apply();
    }

    public void storeUserAvatar_RC(String avatar) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserAvatar_RC, avatar);
        editor.apply();
    }

    public boolean isLoggedIn_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UserToken_RC, null) != null;
    }

    public String LoggedInUserAvatar_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UserAvatar_RC, null);
    }

    //find logged in user
    public String LoggedInUserName_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UserName_RC, null);

    }

    public String LoggedInEmail_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UserEmail_RC, null);

    }

    public String LoggedInUserToken_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UserToken_RC, null);

    }

    //Logout user
    public void logout_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, SelectLoginActivity.class));
    }
}

