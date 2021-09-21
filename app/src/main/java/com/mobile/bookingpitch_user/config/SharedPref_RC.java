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

    public boolean isLoggedIn_RC() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UserToken_RC, null) != null;
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

