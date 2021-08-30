package com.mobile.bookingpitch_user.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.ChangePass_Model.ChangePass;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText edtOldPass, edtNewsPass, edtReNewsPass;
    private Button btnChange, btnCancel, btnYesNotiApp;
    private TextView tvYesNotiApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Toolbar toolbar = findViewById(R.id.toolbar_ChangerPass);
        toolbar.setTitle(R.string.doi_MK);
        setSupportActionBar(toolbar);

        initView();
        btnChange.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void initView() {
        btnChange = findViewById(R.id.btnChagePass_ChangePass);
        btnCancel = findViewById(R.id.btnCancel_ChangePass);
        edtOldPass = findViewById(R.id.edtPassOld_ChangePass);
        edtNewsPass = findViewById(R.id.edtNewPass_ChangePass);
        edtReNewsPass = findViewById(R.id.edtReNewPass_ChangePass);
    }

    private void notificationApp(String text) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChangePassActivity.this);
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
        if (btnCancel.getId() == v.getId()) {
            edtOldPass.setText("");
            edtNewsPass.setText("");
            edtReNewsPass.setText("");
        } else if (btnChange.getId() == v.getId()) {
            callChangePass();
        }
    }

    private void callChangePass() {
        if (edtOldPass.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_mk_cu));
        } else if (edtNewsPass.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_mk_moi));
        } else if (edtNewsPass.getText().toString().length() <= 5) {
            notificationApp(getString(R.string.toi_thieu_6_kt));
        } else if (!edtReNewsPass.getText().toString().equalsIgnoreCase(edtNewsPass.getText().toString())) {
            notificationApp(getString(R.string.mk_nhap_lai_ko_dung));
        } else {
            String strPassOld = edtOldPass.getText().toString();
            String strNewPass = edtNewsPass.getText().toString();
            String strReNewPass = edtReNewsPass.getText().toString();
            String token = SharedPref_RC.getInstance_RC(ChangePassActivity.this).LoggedInUserToken_RC();

            Retrofit retrofit = ConfigRetrofitApi.getInstance();
            retrofit.create(InterfaceAPI.class)
                    .changePass(token, strPassOld, strNewPass, strReNewPass)
                    .enqueue(new Callback<ChangePass>() {
                        @Override
                        public void onResponse(Call<ChangePass> call, Response<ChangePass> response) {
                            ChangePass changePass = response.body();
                            if (changePass.getData() != null && changePass.getData().getResult() == true) {
                                Toast.makeText(ChangePassActivity.this, changePass.getData().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                notificationApp(changePass.getError().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePass> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}