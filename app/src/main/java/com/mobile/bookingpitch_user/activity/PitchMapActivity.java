package com.mobile.bookingpitch_user.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.mobile.bookingpitch_user.R;

public class PitchMapActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_map);

        Toolbar toolbar = findViewById(R.id.toolbar_PitchMap);
        toolbar.setTitle(R.string.so_do_san_);
        setSupportActionBar(toolbar);

        findViewById(R.id.tvPhone_PitchMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndCall();
            }
        });
    }

    private void askPermissionAndCall() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            int sendSmsPermisson = ActivityCompat.checkSelfPermission(PitchMapActivity.this,
                    Manifest.permission.CALL_PHONE);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSION_REQUEST_CODE_CALL_PHONE
                );
                return;
            }
        }
        callNow();
    }

    private void callNow() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + "0981480462"));
        try {
            startActivity(callIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}