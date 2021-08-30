package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.mobile.bookingpitch_user.R;

public class AppInfo_Activity extends AppCompatActivity {
    private TextView tvInfoApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        Toolbar toolbar = findViewById(R.id.toolbar_AppInfo);
        toolbar.setTitle(R.string.thong_tin_app_viet_hoa);
        setSupportActionBar(toolbar);

        tvInfoApp = findViewById(R.id.tvInfoApp);
        tvInfoApp.setText(getString(R.string.app_info_dong1)+ "\n" +
                getString(R.string.app_info_dong2) + "\n" +
                getString(R.string.app_info_dong3) + "\n" +
                getString(R.string.app_info_dong4) + "\n" +
                getString(R.string.app_info_dong5));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}