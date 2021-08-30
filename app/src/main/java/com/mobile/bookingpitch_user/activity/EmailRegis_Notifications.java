package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bookingpitch_user.R;

public class EmailRegis_Notifications extends AppCompatActivity implements View.OnClickListener {
    private TextView tvHiUser, tvThankUser, tvReSendEmail;
    private EditText edtVerifiCode;
    private ImageView imgBack;
    private Button btnConfirmEmail;
    private String token;
    private int otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_regis_notifications);

        // nhan thong tin tu man register 2
        Intent getIntenRegisterTwo = getIntent();
        Bundle bundleRegisterTwo = getIntenRegisterTwo.getExtras();
        if (bundleRegisterTwo != null) {
            token = bundleRegisterTwo.getString("access_token");
            otp = bundleRegisterTwo.getInt("otp");
        }

        initView();
        tvHiUser.setText(getString(R.string.xin_chao) + ", "  + RegisterActivity.nameAcc);
        btnConfirmEmail.setOnClickListener(this);
    }

    private void initView() {
        tvHiUser = findViewById(R.id.tvTextHiUser_RecruiterNotifications);
        edtVerifiCode = findViewById(R.id.edtVerifiCode_EmailRecruitersNotifications);
        imgBack = findViewById(R.id.imgBack_EmailRecruiterNotifications);
        btnConfirmEmail = findViewById(R.id.btnConfirmEmail_RecruiterNotifications);
    }

    @Override
    public void onClick(View v) {
        if (btnConfirmEmail.getId() == v.getId()){
            if (edtVerifiCode.getText().toString().isEmpty()) {
                Toast.makeText(this, R.string.nhap_ma_otp_gui_mail, Toast.LENGTH_SHORT).show();
            }else if(!edtVerifiCode.getText().toString().equalsIgnoreCase(String.valueOf(otp))){
                Toast.makeText(this, R.string.ma_otp_sai, Toast.LENGTH_SHORT).show();
            }
            else {
                startActivity(new Intent(EmailRegis_Notifications.this, Successful_Register.class));
            }
        }
    }
}