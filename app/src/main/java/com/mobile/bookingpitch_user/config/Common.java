package com.mobile.bookingpitch_user.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {
    public static boolean isConnected(Context context){
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();

            return (info != null && info.isConnected());
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}
