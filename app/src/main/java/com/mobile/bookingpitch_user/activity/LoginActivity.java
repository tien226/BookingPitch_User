package com.mobile.bookingpitch_user.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.AppData;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.Login.ProfileEmployee;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail;
    private TextInputLayout edtPass;
    private TextView tvForgotPass, tvRegisterAcc, tvLoginNoAcc, tvYesNotiApp;
    private Button btnLogin, btnYesNotiApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_login);

        initView();
        btnLogin.setOnClickListener(this);
        tvRegisterAcc.setOnClickListener(this);
//        tvLoginNoAcc.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
    }

    private void initView() {
        edtEmail = findViewById(R.id.edt_Email_LoginScreen);
        edtPass = findViewById(R.id.edt_pass_LoginScreen);
        btnLogin = findViewById(R.id.btn_Login_LoginScreen);
        tvForgotPass = findViewById(R.id.tvForgotPass_LoginScreen);
        tvRegisterAcc = findViewById(R.id.tvRegisterAccount_LoginScreen);
//        tvLoginNoAcc = findViewById(R.id.tvLoginNoAcc);
    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
        if (btnLogin.getId() == v.getId()) {
            if (edtEmail.getText().toString().isEmpty()) {
                notificationApp(getString(R.string.nhap_email));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
                notificationApp(getString(R.string.sai_ding_dang_email));
            } else if (edtPass.getEditText().getText().toString().isEmpty()) {
                notificationApp(getString(R.string.nhap_pass));
            } else {
                login();
            }
        } else if (tvForgotPass.getId() == v.getId()) {
            startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
        } else if (tvRegisterAcc.getId() == v.getId()) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
//        else if (tvLoginNoAcc.getId() == v.getId()) {
//            finish();
//            SharedPref_RC.getInstance_RC(LoginActivity.this).logout_RC();
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//
//        }
    }

    private void login() {
        Map<String, String> params = new HashMap<>();
        // login api
        params.put("email", edtEmail.getText().toString());
        params.put("password", edtPass.getEditText().getText().toString());
        Retrofit retrofit = ConfigRetrofitApi.getInstance();
        retrofit.create(InterfaceAPI.class)
                .loginAccount(params)
                .enqueue(new Callback<ProfileEmployee>() {
                    @Override
                    public void onResponse(Call<ProfileEmployee> call, Response<ProfileEmployee> response) {
                        ProfileEmployee profileNtd = response.body();
                        Log.e("login", profileNtd.toString());

                        if (profileNtd != null) {
                            if (profileNtd.getError() == null) {
                                Toast.makeText(LoginActivity.this, R.string.dn_thanh_cong, Toast.LENGTH_SHORT).show();

                                String nameUV = "";
                                String tokenLoginUV = "";
                                String avatarUV = "";
                                String emailUV = "";

                                nameUV = profileNtd.getData().getUserInfo().getEpName();
                                tokenLoginUV = profileNtd.getData().getAccessToken();
                                avatarUV = profileNtd.getData().getUserInfo().getEpAvatar();
                                emailUV = profileNtd.getData().getUserInfo().getEpEmail();

                                SharedPref_RC.getInstance_RC(LoginActivity.this).storeUserToKen_RC(tokenLoginUV);
                                SharedPref_RC.getInstance_RC(LoginActivity.this).storeUserName_RC(nameUV);
                                SharedPref_RC.getInstance_RC(LoginActivity.this).storeUserEmail_RC(emailUV);
                                SharedPref_RC.getInstance_RC(LoginActivity.this).storeUserAvatar_RC(avatarUV);

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                            } else {
//                                notificationApp(profileNtd.getError().getMessage());
                                notificationApp(getString(R.string.tk_mk_ko_dung));
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileEmployee> call, Throwable t) {
                        notificationApp(getString(R.string.loi_call_api));
                        Log.e("aab", t.getMessage());
                    }
                });
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