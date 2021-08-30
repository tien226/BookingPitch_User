package com.mobile.bookingpitch_user.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDCity;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataCity;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataDistrict;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDistrict;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    private List<HDDataCity> lsCity = new ArrayList<>();
    private List<HDDataDistrict> lsDistrict = new ArrayList<>();
    LottieAnimationView animationView;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        initCityData();

//        animationView = findViewById(R.id.animationView);
//        animationView.animate().translationX(1500).setDuration(1500).setStartDelay(1900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(getApplicationContext(), SelectLoginActivity.class));
//                finish();
                if (currentUser == null) {
                    startActivity(new Intent(getApplicationContext(), SelectLoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }, 4000);
    }

    private void initCityData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("DistrictAndCity", MODE_PRIVATE);
        Gson cityGson = new Gson();
        Gson districtGson = new Gson();

        Retrofit cityRetrofit = ConfigRetrofitApi.getInstance();
        cityRetrofit.create(InterfaceAPI.class)
                .getCity()
                .enqueue(new Callback<HDCity>() {
                    @Override
                    public void onResponse(Call<HDCity> call, Response<HDCity> response) {
                        if (response.body() != null && response.body().getData().size() != 0) {
                            lsCity.addAll(response.body().getData());
                            String json = cityGson.toJson(lsCity);
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("listCity", json);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onFailure(Call<HDCity> call, Throwable t) {

                    }
                });
        Retrofit districtRetrofit = ConfigRetrofitApi.getInstance();
        districtRetrofit.create(InterfaceAPI.class)
                .getCityAndDistrict()
                .enqueue(new Callback<HDDistrict>() {
                    @Override
                    public void onResponse(Call<HDDistrict> call, Response<HDDistrict> response) {
                        if (response.body() != null && response.body().getData().size() != 0) {
                            lsDistrict.addAll(response.body().getData());
                            String json = districtGson.toJson(lsDistrict);
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("listDistrict", json);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onFailure(Call<HDDistrict> call, Throwable t) {

                    }
                });
    }
}