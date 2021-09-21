package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.mobile.bookingpitch_user.BuildConfig;
import com.mobile.bookingpitch_user.R;

public class AppInfo_Activity extends AppCompatActivity {
    private TextView tvInfoApp, tvVersion;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        Toolbar toolbar = findViewById(R.id.toolbar_AppInfo);
        toolbar.setTitle(R.string.thong_tin_app_viet_hoa);
        setSupportActionBar(toolbar);

        tvVersion = findViewById(R.id.tvVersionApp);
        tvInfoApp = findViewById(R.id.tvInfoApp);
        tvInfoApp.setText(getString(R.string.app_info_dong1)+ "\n" +
                getString(R.string.app_info_dong2) + "\n" +
                getString(R.string.app_info_dong3) + "\n" +
                getString(R.string.app_info_dong4) + "\n" +
                getString(R.string.app_info_dong5));

        tvVersion.setText("Version " + BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}