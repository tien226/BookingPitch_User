package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.AppData;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.model.EmailForgotPass_Model.EmailForgotPass;
import com.mobile.bookingpitch_user.model.ForgotPass_Model.ForgotPass;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail;
    private Button btnEmailVerifi, btnForgotPass, btnYesNotiApp;
    private TextView tvYesNotiApp;
    private TextInputEditText edtPass, edtRePass;
    private boolean isClick = false;
    private String tokenEmailForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_forgot_pass);

        initView();
        btnEmailVerifi.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail_ForgotPass);
        edtPass = findViewById(R.id.edtPass_ForgotPass);
        edtRePass = findViewById(R.id.edtRePass_ForgotPass);
        btnEmailVerifi = findViewById(R.id.btnEmailVerification);
        btnForgotPass = findViewById(R.id.btnForgot_ForgotPass);
    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_notifi_app, null);
        tvYesNotiApp = mview.findViewById(R.id.tvContent_DialogNotifiApp);
        btnYesNotiApp = mview.findViewById(R.id.btnYes_DialogNotifiApp);
        builder.setView(mview);
        Dialog dialogNotiApp = builder.create();
        dialogNotiApp.setCancelable(false);
        dialogNotiApp.show();
        // set text tvyes
        tvYesNotiApp.setText(text);
        // click btn yes
        btnYesNotiApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotiApp.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (btnEmailVerifi.getId() == v.getId()) {
            callEmalForgotPass();
        } else if (btnForgotPass.getId() == v.getId()) {
            if (edtEmail.getText().toString().isEmpty()) {
                notificationApp(getString(R.string.xac_thuc_email_truoc));
            } else {
                forgotPass();
            }
        }
    }

    private void callEmalForgotPass() {
        String strEmail = edtEmail.getText().toString();
        if (strEmail.isEmpty()) {
            notificationApp(getString(R.string.nhap_email));

        } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            notificationApp(getString(R.string.sai_ding_dang_email));
        } else {
            Retrofit retrofit = ConfigRetrofitApi.getInstance();
            retrofit.create(InterfaceAPI.class)
                    .emailForgotPass(strEmail, 1)
                    .enqueue(new Callback<EmailForgotPass>() {
                        @Override
                        public void onResponse(Call<EmailForgotPass> call, Response<EmailForgotPass> response) {
                            EmailForgotPass emailForgotPass = response.body();
                            if (emailForgotPass.getData() != null && emailForgotPass.getData().getResult() == true) {
                                Toast.makeText(ForgotPassActivity.this, emailForgotPass.getData().getMessage(), Toast.LENGTH_SHORT).show();
                                tokenEmailForgotPass = emailForgotPass.getData().getToken();
                            } else {
                                notificationApp(emailForgotPass.getError().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<EmailForgotPass> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });

        }
    }

    private void forgotPass() {
        String strpass = edtPass.getText().toString();
        String strRePass = edtRePass.getText().toString();
        if (strpass.isEmpty()) {
            notificationApp(getString(R.string.nhap_pass));
        } else if (strpass.length() <= 5) {
            notificationApp(getString(R.string.toi_thieu_6_kt));
        } else if (!strRePass.equalsIgnoreCase(strpass)) {
            notificationApp(getString(R.string.mk_nhap_lai_ko_dung));
        } else {
            Retrofit retrofit = ConfigRetrofitApi.getInstance();
            retrofit.create(InterfaceAPI.class)
                    .forgotPass(tokenEmailForgotPass, strpass, strRePass, 1)
                    .enqueue(new Callback<ForgotPass>() {
                        @Override
                        public void onResponse(Call<ForgotPass> call, Response<ForgotPass> response) {
                            ForgotPass forgotPass = response.body();
                            if (forgotPass.getData() != null && forgotPass.getData().getResult() == true) {
                                Toast.makeText(ForgotPassActivity.this, forgotPass.getData().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
                            } else {
                                notificationApp(forgotPass.getError().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ForgotPass> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });
        }

    }

    private void setLanguage() {
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        AppData.language = prefs.getString("Language", AppData.LOCALE_VI_VN);
        Locale locale = new Locale(AppData.language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}