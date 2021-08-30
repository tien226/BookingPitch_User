package com.mobile.bookingpitch_user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bookingpitch_user.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtPhone;
    private Button btnGetOTP, btnYesNotiApp;
    private TextView tvYesNotiApp;
    private ProgressBar progressBar;
    private String regexStr = "^[0-9]{10}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otpactivity);

        Toolbar toolbar = findViewById(R.id.toolbar_SendOTP);
        toolbar.setTitle(R.string.otp_verification);
        setSupportActionBar(toolbar);

        initView();
        btnGetOTP.setOnClickListener(this);
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        edtPhone = findViewById(R.id.edtInputMobile);
        btnGetOTP = findViewById(R.id.btnGetOtp);
    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SendOTPActivity.this);
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
        if (btnGetOTP.getId() == v.getId()){
            if (edtPhone.getText().toString().isEmpty()){
                notificationApp(getString(R.string.nhap_sdt));
                return;
            }
            if (!edtPhone.getText().toString().matches(regexStr)){
                notificationApp(getString(R.string.sai_dinh_dang_sdt));
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            btnGetOTP.setVisibility(View.INVISIBLE);

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+84" + edtPhone.getText().toString(),
                    20,
                    TimeUnit.SECONDS,
                    SendOTPActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            progressBar.setVisibility(View.GONE);
                            btnGetOTP.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            progressBar.setVisibility(View.GONE);
                            btnGetOTP.setVisibility(View.VISIBLE);
                            Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            progressBar.setVisibility(View.GONE);
                            btnGetOTP.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(SendOTPActivity.this, VerifyOTPActivity.class);
                            intent.putExtra("mobile", edtPhone.getText().toString());
                            intent.putExtra("verificationId", verificationId);
                            startActivity(intent);
                        }
                    }
            );

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}