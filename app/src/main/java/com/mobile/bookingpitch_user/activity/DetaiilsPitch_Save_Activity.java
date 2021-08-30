package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.Dao;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.model.CancelPitch;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetaiilsPitch_Save_Activity extends AppCompatActivity {
    private ImageView imgPitch;
    private TextView tvName, tvPrime, tvTime, tvInfoPitch, tvUserId, tvTotalPrice, tvPriceWater, tvNuoc, tvDate;
    private Button btnCancel, btnYes, btnCancelDialog;
    private String str_Id;
    private ImageView cbTrongTai, cbAoDau, cbNuoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaiils_pitch_save);

        Toolbar toolbar = findViewById(R.id.toolbar_DetailsPitchSave);
        toolbar.setTitle(R.string.chi_tiet_san_bong);
        setSupportActionBar(toolbar);

        initView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            Picasso.get().load(bundle.getString("img")).placeholder(R.drawable.img_details_pitch).error(R.drawable.img_details_pitch).into(imgPitch);
            tvName.setText(bundle.getString("name"));

            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getInstance(locale);
            tvPrime.setText(format.format(Integer.valueOf(bundle.getString("price"))) + " đ");

            tvTime.setText(new Dao().getSpanPitchID(DetaiilsPitch_Save_Activity.this, bundle.getString("span")));

            tvInfoPitch.setText(bundle.getString("note"));

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat outputSdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = sdf.parse(bundle.getString("date"));
                tvDate.setText(getString(R.string.ngay_dat_san) + ": " + outputSdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            str_Id = bundle.getString("_Id");
            tvUserId.setText(getString(R.string.so_dien_thoai) + ": " + bundle.getString("userID"));
            tvTotalPrice.setText(getString(R.string.tong_tien) + ": " + format.format(Integer.valueOf(bundle.getString("totalPrice"))) + " đ");
            if (bundle.getBoolean("umpire") == true){
                cbTrongTai.setImageResource(R.drawable.check);
            }else {
                cbTrongTai.setImageResource(R.drawable.uncheck);
            }

            if (bundle.getBoolean("tshirt") == true){
                cbAoDau.setImageResource(R.drawable.check);
            }else {
                cbAoDau.setImageResource(R.drawable.uncheck);
            }
            if (Integer.valueOf(bundle.getString("quantityWater")) == 0){
                cbNuoc.setImageResource(R.drawable.uncheck);
            }else {
                cbNuoc.setImageResource(R.drawable.check);
                tvNuoc.setText(getString(R.string.dat_nuoc) + " (" + bundle.getString("quantityWater") + getString(R.string.binh) + ")");
            }
            tvPriceWater.setText("(" + format.format(Integer.valueOf(bundle.getString("priceWater"))) + " đ)");

        }

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialogCancel();
//            }
//        });
    }

    private void showDialogCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetaiilsPitch_Save_Activity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_cancel_bookpitch, null);
        btnYes = mview.findViewById(R.id.btnYes_DialogCancel);
        btnCancelDialog = mview.findViewById(R.id.btnCancel_DialogCancel);
        builder.setView(mview);

        AlertDialog dialog = builder.show();
        // click btn yes
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callAPICancelPitch();

                dialog.dismiss();
            }
        });
        // click btn cancel
        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void callAPICancelPitch() {
        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .cancelBookPitch(str_Id, "-1")
                .enqueue(new Callback<CancelPitch>() {
                    @Override
                    public void onResponse(Call<CancelPitch> call, Response<CancelPitch> response) {
                        CancelPitch cancelPitch = response.body();
                        if (cancelPitch.getSuccess() == true){
                            Toast.makeText(DetaiilsPitch_Save_Activity.this, cancelPitch.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DetaiilsPitch_Save_Activity.this, "lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CancelPitch> call, Throwable t) {
                        Toast.makeText(DetaiilsPitch_Save_Activity.this, "Lỗi mạng hoặc lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        tvDate = findViewById(R.id.tvDateDetailsPitchSave);
        tvNuoc = findViewById(R.id.tvNuoc_DetailsPitchSave);
        tvPriceWater = findViewById(R.id.tvPriceWater_DetailsPitchSave);
        cbTrongTai = findViewById(R.id.cbTrongTai_DetailsPitchSave);
        cbAoDau = findViewById(R.id.cbAoDau_DetailsPitchSave);
        cbNuoc = findViewById(R.id.cbNuoc_DetailsPitchSave);
        tvUserId = findViewById(R.id.tvPhoneDetailsPitchSave);
        tvTotalPrice = findViewById(R.id.tvPriceDetailsPitchSave);
        imgPitch = findViewById(R.id.imgDetailsPitchSave);
        tvName = findViewById(R.id.tvName_DetailsPitchSave);
        tvPrime = findViewById(R.id.tvPrime_DetailsPitchSave);
        tvTime = findViewById(R.id.tvTime_DetailsPitchSave);
        tvInfoPitch = findViewById(R.id.tvInfoDetailsPitchSave);
//        btnCancel = findViewById(R.id.btnCancel_DetailsPitchSave);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}