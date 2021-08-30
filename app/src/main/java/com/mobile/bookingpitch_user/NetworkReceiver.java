package com.mobile.bookingpitch_user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mobile.bookingpitch_user.activity.MainActivity;
import com.mobile.bookingpitch_user.activity.SelectLoginActivity;
import com.mobile.bookingpitch_user.config.Common;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SelectLoginActivity.tvIsInternet != null) {
            if (Common.isConnected(context)) {
                SelectLoginActivity.tvIsInternet.setVisibility(View.GONE);
            } else {
                SelectLoginActivity.tvIsInternet.setText(R.string.khong_co_internet);
                SelectLoginActivity.tvIsInternet.setVisibility(View.VISIBLE);

            }
        }

        if (MainActivity.tvInternet != null) {
            if (Common.isConnected(context)) {
                MainActivity.tvInternet.setVisibility(View.GONE);
            } else {
                MainActivity.tvInternet.setText(R.string.khong_co_internet);
                MainActivity.tvInternet.setVisibility(View.VISIBLE);
            }
        }
    }
}
