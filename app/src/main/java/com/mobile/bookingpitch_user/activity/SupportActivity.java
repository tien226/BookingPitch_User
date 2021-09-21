package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mobile.bookingpitch_user.R;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout tvEmail, tvPhone, tvMaps, tvStk;
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        Toolbar toolbar = findViewById(R.id.toolbar_Support);
        toolbar.setTitle(R.string.thong_tin_ho_tro);
        setSupportActionBar(toolbar);

        initView();
        tvEmail.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvMaps.setOnClickListener(this);
        tvStk.setOnClickListener(this);
    }

    private void initView() {
        tvStk = findViewById(R.id.tvStk_Support);
        tvEmail = findViewById(R.id.tvEmail_Support);
        tvPhone = findViewById(R.id.tvPhone_Support);
        tvMaps = findViewById(R.id.tvMaps_Support);
    }

    @Override
    public void onClick(View v) {
        if (tvEmail.getId() == v.getId()){
            askPermissionAndEmail();
        }else if (tvPhone.getId() == v.getId()){
            askPermissionAndCall();
        }else if (tvMaps.getId() == v.getId()){
            openAppMaps();
        }else if (tvStk.getId() == v.getId()){
            startActivity(new Intent(SupportActivity.this, CreateNewOrderActivity.class));
        }
    }

    private void openAppMaps() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:21.03824794998889,105.74670126690945"));
//        Intent chooser = Intent.createChooser(intent, "Lauch Maps");
//        startActivity(chooser);

        Intent intent = new Intent(Intent.ACTION_VIEW,
        Uri.parse("google.navigation:q=21.038267977312046, 105.74674418220671&mode=l"));
        startActivity(intent);
    }

    private void askPermissionAndEmail() {
        Intent callIntent = new Intent(Intent.ACTION_SENDTO);
        callIntent.setData(Uri.parse("mailto:" + "tientmph09690@fpt.edu.vn"));
        try {
            startActivity(callIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void askPermissionAndCall() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            int sendSmsPermisson = ActivityCompat.checkSelfPermission(SupportActivity.this,
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