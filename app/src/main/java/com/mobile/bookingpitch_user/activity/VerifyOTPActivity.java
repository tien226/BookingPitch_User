package com.mobile.bookingpitch_user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtCode1, edtCode2, edtCode3, edtCode4, edtCode5, edtCode6;
    private TextView tvTextMobile, tvResetOTP, tvYesNotiApp;
    private ProgressBar progressBar;
    private Button btnVerify, btnYesNotiApp;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        Toolbar toolbar = findViewById(R.id.toolbar_VerifyOTP);
        toolbar.setTitle(R.string.otp_verification);
        setSupportActionBar(toolbar);

        initView();

        tvTextMobile.setText(String.format("+84-%s", getIntent().getStringExtra("mobile")));

        btnVerify.setOnClickListener(this);
        tvResetOTP.setOnClickListener(this);
        setupOTPInputs();
        
        verificationId = getIntent().getStringExtra("verificationId");
    }

    private void initView() {
        tvResetOTP = findViewById(R.id.tvResetOTP);
        btnVerify = findViewById(R.id.btnVerify);
        progressBar = findViewById(R.id.progressBar_Verify);
        tvTextMobile = findViewById(R.id.tvTextMobile);
        edtCode1 = findViewById(R.id.edtInputCode1);
        edtCode2 = findViewById(R.id.edtInputCode2);
        edtCode3 = findViewById(R.id.edtInputCode3);
        edtCode4 = findViewById(R.id.edtInputCode4);
        edtCode5 = findViewById(R.id.edtInputCode5);
        edtCode6 = findViewById(R.id.edtInputCode6);
    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOTPActivity.this);
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

    private void setupOTPInputs() {
        edtCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    edtCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (tvResetOTP.getId() == v.getId()){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+84" + getIntent().getStringExtra("mobile"),
                    20,
                    TimeUnit.SECONDS,
                    VerifyOTPActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            verificationId = newVerificationId;
                            Toast.makeText(VerifyOTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
        else if (btnVerify.getId() == v.getId()) {
            if (edtCode1.getText().toString().trim().isEmpty()
                    || edtCode2.getText().toString().trim().isEmpty()
                    || edtCode3.getText().toString().trim().isEmpty()
                    || edtCode4.getText().toString().trim().isEmpty()
                    || edtCode5.getText().toString().trim().isEmpty()
                    || edtCode6.getText().toString().trim().isEmpty()){
                notificationApp(getString(R.string.nhap_ma_otp));
                return;
            }
            String code = edtCode1.getText().toString() +
                    edtCode2.getText().toString() +
                    edtCode3.getText().toString() +
                    edtCode4.getText().toString() +
                    edtCode5.getText().toString() +
                    edtCode6.getText().toString();

            if (verificationId != null){
                progressBar.setVisibility(View.VISIBLE);
                btnVerify.setVisibility(View.INVISIBLE);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                btnVerify.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()){
                                    SharedPref_RC.getInstance_RC(VerifyOTPActivity.this).storeUserToKen_RC(String.valueOf(task.isSuccessful()));

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    notificationApp(getString(R.string.ma_otp_sai));
                                }
                            }
                        });


            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}