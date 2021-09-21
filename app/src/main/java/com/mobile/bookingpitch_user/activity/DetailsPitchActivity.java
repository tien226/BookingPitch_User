package com.mobile.bookingpitch_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.adapter.PitchBusy_Adapter;
import com.mobile.bookingpitch_user.adapter.YardList_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.Dao;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.DetailsPitch;
import com.mobile.bookingpitch_user.model.PitchBusy_Model.DataMonth;
import com.mobile.bookingpitch_user.model.PitchBusy_Model.PitchBusy;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsPitchActivity extends AppCompatActivity {
    private ImageView imgPitch;
    private TextView tvName, tvPrime, tvTime, tvInfoPitch, tvYes;
    private String strName, strPrice, strTime, strInfo;
    private Button btnBookNow;
    private RecyclerView recyPitchBusy;
    private ArrayList<DataMonth> dataMonthArrayList;
    private PitchBusy_Adapter adapter;
    public static String strSpanDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_pitch);

        Toolbar toolbar = findViewById(R.id.toolbar_DetailsPitch);
        toolbar.setTitle(R.string.chi_tiet_san_bong);
        setSupportActionBar(toolbar);

        initView();

        Picasso.get().load(YardList_Adapter.strImg).placeholder(R.drawable.img_details_pitch).error(R.drawable.img_details_pitch).into(imgPitch);
        tvName.setText(YardList_Adapter.strName);

        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        tvPrime.setText(format.format(Integer.valueOf(YardList_Adapter.strPrice)) + " đ");

        tvInfoPitch.setText(YardList_Adapter.strDetail);

//        getPitchDetails();

        getDataPitchBusy();

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPref_RC.getInstance_RC(DetailsPitchActivity.this).isLoggedIn_RC()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailsPitchActivity.this);
                    View mview = getLayoutInflater().inflate(R.layout.dialod_notification_login, null);
                    tvYes = mview.findViewById(R.id.tvYes_Dialog_NotificationLogin);
                    builder.setView(mview);
                    // click btn yes
                    tvYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DetailsPitchActivity.this, SelectLoginActivity.class));
                        }
                    });
                    builder.show();
                } else {
                    startActivity(new Intent(DetailsPitchActivity.this, BookNowPitchActivity.class));
                }
            }
        });

    }

    private void getDataPitchBusy() {
        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllPitchBusy(YardList_Adapter.strPitchId)
                .enqueue(new Callback<PitchBusy>() {
                    @Override
                    public void onResponse(Call<PitchBusy> call, Response<PitchBusy> response) {
                        PitchBusy pitchBusy = response.body();
                        if (pitchBusy.getDataMonth() != null) {
                            dataMonthArrayList = new ArrayList<>();
                            dataMonthArrayList.addAll(pitchBusy.getDataMonth());

                            adapter = new PitchBusy_Adapter(DetailsPitchActivity.this, dataMonthArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsPitchActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyPitchBusy.setLayoutManager(linearLayoutManager);
                            recyPitchBusy.setAdapter(adapter);
                        }else {

                        }

                    }

                    @Override
                    public void onFailure(Call<PitchBusy> call, Throwable t) {
                        Toast.makeText(DetailsPitchActivity.this, "Lỗi mạng hệ thống", Toast.LENGTH_SHORT).show();
                        Log.e("loi pitch busy", t.getMessage());
                    }
                });
    }

    private void initView() {
        recyPitchBusy = findViewById(R.id.recyPitchBusy);
        btnBookNow = findViewById(R.id.btnBookNow_DetailsPitch);
        imgPitch = findViewById(R.id.imgDetailsPitch);
        tvName = findViewById(R.id.tvName_DetailsPitch);
        tvPrime = findViewById(R.id.tvPrime_DetailsPitch);
        tvTime = findViewById(R.id.tvTime_DetailsPitch);
        tvInfoPitch = findViewById(R.id.tvInfoDetailsPitch);
    }

//    private void getPitchDetails(){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
//        String strDateNow = dateFormat.format(Calendar.getInstance().getTime());
//
//        Map<String, String> maps = new HashMap<>();
//        maps.put("pitchID", YardList_Adapter.strPitchId);
//        maps.put("date", strDateNow);
//
//        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
//        retrofit.create(InterfaceAPI.class)
//                .detailsPitch(maps)
//                .enqueue(new Callback<DetailsPitch>() {
//                    @Override
//                    public void onResponse(Call<DetailsPitch> call, Response<DetailsPitch> response) {
//                        DetailsPitch detailsPitch = response.body();
//                        if (detailsPitch != null) {
//                            tvTime.setText(new Dao().getSpanPitchID(DetailsPitchActivity.this, detailsPitch.getSpan()));
//                            strSpanDetails = detailsPitch.getSpan();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<DetailsPitch> call, Throwable t) {
//                        Toast.makeText(DetailsPitchActivity.this, "Lỗi mạng hoặc lỗi hệ thống", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}