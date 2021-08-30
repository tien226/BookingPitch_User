package com.mobile.bookingpitch_user.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.model.CityAndDistrict.CityHunter;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataCity;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataDistrict;
import com.mobile.bookingpitch_user.model.RegisterTwo_Model.RegisterTwo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity_Two extends AppCompatActivity implements View.OnClickListener {
    private EditText edtPhone, edtAddress, edtCity, edtDistrict;
    private CheckBox checkBox;
    private Button btnRegister, btnCancel, btnYesNotiApp;
    private TextView tvLogin, tvYesNotiApp;
    private CircleImageView imgSelect;
    private CityHunter cityHunter;
    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    private static final int REQUEST_CHOOSE_PHOTO = 123;
    private Intent intent;
    private Bundle bundle;
    private String tocken, strIdCity, encodedImage, strPhone, strAddress, strDistrict;
    private Bitmap bitmap;
    private String regexStr = "^[0-9]{10}$";
    private List<HDDataCity> lsCity = new ArrayList<>();
    private List<HDDataDistrict> lsDistrict = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        cityHunter = new CityHunter(this);

        initView();

//nhan thong tin tu man register ntd1
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            tocken = bundle.getString("tokenRegister");
        }

        imgSelect.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        edtCity.setOnClickListener(this);
        edtDistrict.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }

    private void notificationApp(String text) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity_Two.this);
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
        imgSelect = findViewById(R.id.imgSelect_RegisterRecruiter_Two);
        edtPhone = findViewById(R.id.edtphone_RegisterRecruiter_Two);
        edtAddress = findViewById(R.id.edtaddress_RegisterRecruiter_Two);
        edtCity = findViewById(R.id.edtCity_RegisterRecruiter_Two);
        edtDistrict = findViewById(R.id.edtDistrict_RegisterRecruiter_Two);
        btnRegister = findViewById(R.id.btnRegister_RegisterRecruiter_Two);
        btnCancel = findViewById(R.id.btnCancel_RegisterRecruiter_Two);
        tvLogin = findViewById(R.id.tvLogin_RegisterRecruiter_Two);
        checkBox = findViewById(R.id.ckb_RegisterRecruiterTwo);
    }

    @Override
    public void onClick(View v) {
        if (imgSelect.getId() == v.getId()) {
            verifyPermissions();
        } else if (btnRegister.getId() == v.getId()) {
            registerRecruitersTwo();
        } else if (edtCity.getId() == v.getId()) {
            Map<String, String> map = new HashMap<>(cityHunter.getListCity());
            List<HDDataCity> hdDataCityList = new ArrayList<>(cityHunter.getListDataCity());
            intent = new Intent(this, CityChooserActivity.class);
            intent.putExtra("listCity", (Serializable) hdDataCityList);
            startActivityForResult(intent, 1);
        } else if (edtDistrict.getId() == v.getId()) {
            if (edtCity.getText().toString().equals("")) {
                notificationApp(getString(R.string.nhap_tinh_thanhpho) + " trước");
            } else {
                List<HDDataDistrict> dataDistrictList = new ArrayList<>(cityHunter.getListDistrictFromParentID(strIdCity));
                intent = new Intent(this, DistrictChooserActivity.class);
                intent.putExtra("listDistrict", (Serializable) dataDistrictList);
                startActivityForResult(intent, 2);
            }
        } else if (tvLogin.getId() == v.getId()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (btnCancel.getId() == v.getId()) {
            edtPhone.setText("");
            edtAddress.setText("");
            edtCity.setText("");
            edtDistrict.setText("");
        }
    }

    private void registerRecruitersTwo() {
        if (edtPhone.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_sdt));
        } else if (!edtPhone.getText().toString().matches(regexStr)) {
            notificationApp(getString(R.string.sai_dinh_dang_sdt));
        } else if (edtAddress.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_dia_chi));
        } else if (edtCity.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_tinh_thanhpho));
        } else if (edtDistrict.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_quan_huyen));
        } else if (checkBox.isChecked() == false) {
            notificationApp(getString(R.string.dong_y_dk_dv));
        } else {
            bitmap = ((BitmapDrawable) imgSelect.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 88, byteArrayOutputStream);
            byte[] imageInByte = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);

            strPhone = edtPhone.getText().toString();
            strAddress = edtAddress.getText().toString();

            Retrofit retrofit = ConfigRetrofitApi.getInstance();
            retrofit.create(InterfaceAPI.class)
                    .registerAccTwo(tocken, encodedImage, strPhone, strAddress, strIdCity, strDistrict)
                    .enqueue(new Callback<RegisterTwo>() {
                        @Override
                        public void onResponse(Call<RegisterTwo> call, Response<RegisterTwo> response) {
                            RegisterTwo registerTwo = response.body();
                            if (registerTwo.getData() != null && registerTwo.getData().getResult()) {
                                Toast.makeText(RegisterActivity_Two.this, registerTwo.getData().getMessage(), Toast.LENGTH_SHORT).show();

                                // truyen thong tin sang man email xac thuc
                                intent = new Intent(RegisterActivity_Two.this, EmailRegis_Notifications.class);
                                bundle = new Bundle();
                                bundle.putString("access_token", registerTwo.getData().getAccessToken());
                                bundle.putInt("otp", registerTwo.getData().getUserInfo().getOtp());
                                intent.putExtras(bundle);
                                startActivity(intent);

                            } else {
                                notificationApp(registerTwo.getError().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterTwo> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });
        }
    }

    private boolean verifyPermissions() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            choosePhotoOrCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_ID_READ_WRITE_PERMISSION);
        }
        return false;
    }

    private void choosePhotoOrCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_choose_pictureorcamera, null);
        builder.setView(dialogView);
        ImageView imgCamera = dialogView.findViewById(R.id.imgCamera_SelectCameraOrGallery);
        ImageView imgGallery = dialogView.findViewById(R.id.imgGallery_SelectCameraOrGallery);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraNow();
                alertDialog.cancel();
            }
        });
        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
                alertDialog.cancel();
            }
        });
    }

    private void choosePhoto() {
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    private void cameraNow() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imgUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    bitmap = BitmapFactory.decodeStream(is);
                    imgSelect.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imgSelect.setImageBitmap(bitmap);
            }
        }

        if (requestCode == 1) {
            if (data == null) {

            } else {
                HDDataCity hdDataCity = (HDDataCity) data.getSerializableExtra("city");
                strIdCity = hdDataCity.getIdCity();
                hdDataCity.getNameCity();
                edtCity.setText(hdDataCity.getNameCity());
                edtDistrict.setText("");
            }
        }
        if (requestCode == 2) {
            if (data == null) {

            } else {
                HDDataDistrict hdDataDistrict = (HDDataDistrict) data.getSerializableExtra("district");
                strDistrict = hdDataDistrict.getIdCity();
                hdDataDistrict.getIdCity();
                edtDistrict.setText(hdDataDistrict.getNameCity());
            }
        }
    }
}