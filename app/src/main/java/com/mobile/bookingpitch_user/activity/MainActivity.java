package com.mobile.bookingpitch_user.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.bookingpitch_user.NetworkReceiver;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.AppData;
import com.mobile.bookingpitch_user.fragment.History.History_Fragment;
import com.mobile.bookingpitch_user.fragment.Home_Fragment;
import com.mobile.bookingpitch_user.fragment.MyInfor_Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;
    private Fragment fragment;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    public static TextView tvInternet;
    private BroadcastReceiver receiver;
    private int REQUEST_CODE = 11;
    private String strDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        setContentView(R.layout.activity_main);

        initView();

        receiver = new NetworkReceiver();
        registerBroadcast();

        fragment = new Home_Fragment();
        loadFragment(fragment);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void initView() {
        navigation = (BottomNavigationView) findViewById(R.id.nav_container);
        tvInternet = findViewById(R.id.tvisInternet_Main);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_IconHome:
                    fragment = new Home_Fragment();
                    loadFragment(fragment);
                    return true;
//                case R.id.nav_IconBook:
//                    getDateNow();
//                    // load fragment khi call api xong
//                    fragment = new BookPitch_Fragment();
//                    loadFragment(fragment);
//                    return true;
                case R.id.nav_IconHistory:
                    fragment = new History_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_IconMaps:
                    fragment = new MyInfor_Fragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("changeTheme");
                if (result.equals("success")) {
                    finish();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }

    protected void registerBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unRegisterBroadcast() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBroadcast();
    }

    private void getDateNow() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                strDate = simpleDateFormat.format(calendar.getTime());
//                            edtDate.setText(strStartDate);
                Toast.makeText(MainActivity.this, strDate, Toast.LENGTH_SHORT).show();

            }
        }, year, month, day);
        datePickerDialog.show();
    }

}