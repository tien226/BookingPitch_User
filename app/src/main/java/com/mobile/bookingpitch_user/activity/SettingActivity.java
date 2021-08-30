package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.bookingpitch_user.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        Toolbar toolbar = findViewById(R.id.toolbar_Settting);
        toolbar.setTitle("Cài Đặt");
        setSupportActionBar(toolbar);

        findViewById(R.id.llLayoutChangerLague).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangeLanguageActivity.class));
            }
        });
    }
}