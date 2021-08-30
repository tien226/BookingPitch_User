package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.bookingpitch_user.R;

public class Successful_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_register);

        findViewById(R.id.btnLogin_SuccessfullRecruiter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Successful_Register.this, LoginActivity.class));
            }
        });
    }
}