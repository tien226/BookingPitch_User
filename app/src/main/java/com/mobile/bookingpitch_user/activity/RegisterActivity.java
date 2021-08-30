package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.model.Register_Model.Register;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText edtname, edtEmail, edtPass, edtRePass;
    private Button btnNext, btnCancel, btnYesNotiApp;
    private TextView tvYesNotiApp;
    public static String nameAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        btnNext.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

    private void initView() {
        edtname = findViewById(R.id.edtName_RecruitersScreen);
        edtEmail = findViewById(R.id.edtEmail_RecruitersScreen);
        edtPass = findViewById(R.id.edtPass_RecruitersScreen);
        edtRePass = findViewById(R.id.edtRepass_Recruiters_Screen);
        btnNext = findViewById(R.id.btnNext_RecruitersScreen);
        btnCancel = findViewById(R.id.btnCancel_Recruiters_Screen);
    }

    @Override
    public void onClick(View v) {
        if (btnNext.getId() == v.getId()) {
            regisAcc();
        }else if (btnCancel.getId() == v.getId()){
            edtname.setText("");
            edtEmail.setText("");
            edtPass.setText("");
            edtRePass.setText("");
        }
    }

    private void regisAcc() {
        if (edtname.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_ho_ten));
        } else if (edtEmail.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_email));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            notificationApp(getString(R.string.sai_ding_dang_email));
        } else if (edtPass.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_mk));
        } else if (edtPass.getText().toString().length() <= 5) {
            notificationApp(getString(R.string.toi_thieu_6_kt));
        } else if (edtRePass.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_lai_mk));
        } else if (!edtRePass.getText().toString().equalsIgnoreCase(edtPass.getText().toString())) {
            notificationApp(getString(R.string.nhap_lai_mk_ko_dung));
        } else {
            String name = edtname.getText().toString();
            String email = edtEmail.getText().toString();
            String pass = edtPass.getText().toString();
            String repass = edtRePass.getText().toString();


            Retrofit retrofit = ConfigRetrofitApi.getInstance();
            retrofit.create(InterfaceAPI.class)
                    .registerAcc(name, email, pass, repass)
                    .enqueue(new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            Register register = response.body();
                            if (register.getData() != null && register.getData().getResult()) {
                                Toast.makeText(RegisterActivity.this, register.getData().getMessage(), Toast.LENGTH_SHORT).show();

                                //truyen thong tin sang register acc ntd2
                                Intent intent = new Intent(RegisterActivity.this, RegisterActivity_Two.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tokenRegister", register.getData().getAccessToken());
                                intent.putExtras(bundle);
                                startActivity(intent);

                                //truyen thong tin ten
                                nameAcc = register.getData().getUserInfo().getEpName();
                            } else {
                                notificationApp(register.getError().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });

        }
    }
}