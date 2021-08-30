package com.mobile.bookingpitch_user.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mobile.bookingpitch_user.NetworkReceiver;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.AppData;
import com.mobile.bookingpitch_user.config.Common;
import com.mobile.bookingpitch_user.config.SharedPref_RC;

import java.util.Locale;

public class SelectLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_ENlang, btn_VIlang;
    public static Configuration newConfig;
    private TextView tvSelectLanguage, tvSelectLogin, tvSupportMenu, tvInfoMenu, tvHDSDMenu;
    private Button btnLoginWithEmail, btnLoginWithPhone, btnLoginNoAcc;
    private ImageView btn_login_menu, btn_login_cancelMenu;
    private ConstraintLayout lay_expand_icons;
    private static final int OPENMENU_DURATION = 100;
    private LinearLayout lay_mangluoi, lay_hdsd, lay_chatVCBS;
    private BroadcastReceiver receiver;
    public static TextView tvIsInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);
        setLanguage();
        initView();

        //is connect internet
        receiver = new NetworkReceiver();
        registerBroadcast();

        btnLoginWithEmail.setOnClickListener(this);
        btnLoginWithPhone.setOnClickListener(this);
        btnLoginNoAcc.setOnClickListener(this);
        btn_ENlang.setOnClickListener(this);
        btn_VIlang.setOnClickListener(this);
        btn_login_menu.setOnClickListener(this);
        btn_login_cancelMenu.setOnClickListener(this);
        lay_mangluoi.setOnClickListener(this);
        lay_hdsd.setOnClickListener(this);
        lay_chatVCBS.setOnClickListener(this);

        setupLanguage();
    }

    private void initView() {
        tvIsInternet = findViewById(R.id.tvIsInternet);
        tvSupportMenu = findViewById(R.id.tvSupportMenu);
        tvInfoMenu = findViewById(R.id.tvInfoMenu);
        tvHDSDMenu = findViewById(R.id.tvHDSDMenu);
        btn_login_cancelMenu = findViewById(R.id.btn_login_cancelMenu);
        lay_mangluoi = findViewById(R.id.lay_login_mangluoi);
        lay_hdsd = findViewById(R.id.lay_login_hdsd);
        lay_chatVCBS = findViewById(R.id.lay_login_chatVCBS);
        lay_expand_icons = findViewById(R.id.lay_login_expand_icons);
        btn_login_menu = findViewById(R.id.btn_login_menu);
        btnLoginWithEmail = findViewById(R.id.btnLoginWithEmail);
        btnLoginWithPhone = findViewById(R.id.btnLoginWithPhone);
        btnLoginNoAcc = findViewById(R.id.btnLoginNoAcc);
//        tvSelectLogin = findViewById(R.id.tvSelectLogin);
        tvSelectLanguage = findViewById(R.id.tvSelectLanguage);
        btn_ENlang = findViewById(R.id.btn_Selectlogin_ENlang);
        btn_VIlang = findViewById(R.id.btn_Selectlogin_VIlang);

        updateText();
        if (AppData.LOCALE_EN.equals(AppData.language)) {
            btn_ENlang.setSelected(true);
            btn_VIlang.setSelected(false);
        } else {
            btn_ENlang.setSelected(false);
            btn_VIlang.setSelected(true);
        }
    }



    private void updateText() {
        tvSelectLanguage.setText(R.string.lua_chon_ngon_ngu);
//        tvSelectLogin.setText(R.string.hinh_thuc_dang_nhap);
        btnLoginWithEmail.setText(R.string.login_with_email);
        btnLoginWithPhone.setText(R.string.login_with_phone);
        btnLoginNoAcc.setText(R.string.login_no_acc);
        tvSupportMenu.setText(R.string.ho_tro);
        tvInfoMenu.setText(R.string.thong_tin_app);
        tvHDSDMenu.setText(R.string.hdsd);
    }

    @Override
    public void onClick(View v) {
        if (btn_ENlang.getId() == v.getId()) {
            if (!btn_ENlang.isSelected()) {
                btn_ENlang.setSelected(true);
                btn_VIlang.setSelected(false);
                saveLocale(AppData.LOCALE_EN);

                Intent refresh = new Intent(getApplicationContext(), SelectLoginActivity.class);
                finish();
                startActivity(refresh);
            }
        } else if (btn_VIlang.getId() == v.getId()) {
            if (btn_ENlang.isSelected()) {
                btn_ENlang.setSelected(false);
                btn_VIlang.setSelected(true);
                saveLocale(AppData.LOCALE_VI_VN);

                Intent refresh = new Intent(getApplicationContext(), SelectLoginActivity.class);
                finish();
                startActivity(refresh);
            }
        } else if (btnLoginWithEmail.getId() == v.getId()) {
            startActivity(new Intent(SelectLoginActivity.this, LoginActivity.class));
        } else if (btnLoginWithPhone.getId() == v.getId()) {
            startActivity(new Intent(SelectLoginActivity.this, SendOTPActivity.class));
        } else if (btnLoginNoAcc.getId() == v.getId()) {
            finish();
            SharedPref_RC.getInstance_RC(SelectLoginActivity.this).logout_RC();
            startActivity(new Intent(SelectLoginActivity.this, MainActivity.class));
        } else if (btn_login_menu.getId() == v.getId()) {
            showLoginMenu();
        } else if (btn_login_cancelMenu.getId() == v.getId()) {
            closeLoginMenu();
        } else if (lay_mangluoi.getId() == v.getId()) {
            startActivity(new Intent(SelectLoginActivity.this, SupportActivity.class));
        } else if (lay_hdsd.getId() == v.getId()) {
            startActivity(new Intent(SelectLoginActivity.this, AppInfo_Activity.class));
        } else if (lay_chatVCBS.getId() == v.getId()) {
            startActivity(new Intent(SelectLoginActivity.this, HDSDActivity.class));
        }
    }

    public void changeLanguage(String lang) {
        if (AppData.LOCALE_VI_VN.equals(lang)) {
            AppData.language = AppData.LOCALE_VI_VN;
        } else {
            AppData.language = AppData.LOCALE_EN;
        }
        newConfig.locale = new Locale(lang);
        onConfigurationChanged(newConfig);
        Intent refresh = new Intent(this, SelectLoginActivity.class);
        finish();
        startActivity(refresh);
    }

    private void setupLanguage() {
        if (getFlagChangeLanguage()) {
            btn_ENlang.setSelected(false);
            btn_VIlang.setSelected(true);
        } else {
            btn_VIlang.setSelected(false);
            btn_ENlang.setSelected(true);
        }
    }

    private void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public boolean getFlagChangeLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("CommonPrefs", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("Language", AppData.LOCALE_VI_VN).equals(AppData.LOCALE_VI_VN)) {
            return true;
        } else {
            return false;
        }

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

    private void showLoginMenu() {
//        tvSelectLogin.setVisibility(View.GONE);
        btnLoginWithEmail.setVisibility(View.GONE);
        btnLoginWithPhone.setVisibility(View.GONE);
        btnLoginNoAcc.setVisibility(View.GONE);
        btn_login_menu.setVisibility(View.GONE);

        lay_expand_icons.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator1 = openAnimator(lay_mangluoi, OPENMENU_DURATION);
        ValueAnimator valueAnimator2 = openAnimator(lay_hdsd, OPENMENU_DURATION);
        ValueAnimator valueAnimator4 = openAnimator(lay_chatVCBS, OPENMENU_DURATION);

        valueAnimator1.start();
        valueAnimator2.start();
        valueAnimator4.start();
    }

    private ValueAnimator openAnimator(final View view, long duration) {
        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                layoutParams.circleRadius = dpToPx(val);
                view.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }

    public int dpToPx(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    private void closeLoginMenu() {
        ValueAnimator valueAnimator1 = closeAnimator(lay_mangluoi, OPENMENU_DURATION);
        ValueAnimator valueAnimator2 = closeAnimator(lay_hdsd, OPENMENU_DURATION);
        ValueAnimator valueAnimator4 = closeAnimator(lay_chatVCBS, OPENMENU_DURATION);

        valueAnimator1.start();
        valueAnimator2.start();
        valueAnimator4.start();

        Handler handlerOpenMenu = new Handler();
        handlerOpenMenu.postDelayed(new Runnable() {
            @Override
            public void run() {
                lay_expand_icons.setVisibility(View.GONE);
            }
        }, OPENMENU_DURATION);

//        tvSelectLogin.setVisibility(View.VISIBLE);
//        btnLoginWithEmail.setVisibility(View.VISIBLE);
        btnLoginWithPhone.setVisibility(View.VISIBLE);
        btnLoginNoAcc.setVisibility(View.VISIBLE);
        btn_login_menu.setVisibility(View.VISIBLE);
    }

    private ValueAnimator closeAnimator(final View view, long duration) {
        ValueAnimator anim = ValueAnimator.ofInt(100, 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                layoutParams.circleRadius = dpToPx(val);
                view.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator());
        return anim;
    }

    protected void registerBroadcast(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unRegisterBroadcast (){
        try {
            unregisterReceiver(receiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBroadcast();
    }
}