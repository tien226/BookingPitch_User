package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.config.AppData;

import java.util.Locale;

public class ChangeLanguageActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout ln_language_vi, ln_language_en;
    private Button btn_changeLanguage_confirm;
    private ImageView img_language_vi, img_language_en;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lague);

        Toolbar toolbar = findViewById(R.id.toolbar_Language);
        toolbar.setTitle(R.string.doi_ngon_ngu);
        setSupportActionBar(toolbar);

//        mainActivity = (MainActivity) getApplicationContext();

        initView();

        initData();

        ln_language_vi.setOnClickListener(this);
        ln_language_en.setOnClickListener(this);
        btn_changeLanguage_confirm.setOnClickListener(this);


    }


    private void initData() {
        if (getFlagChangeLanguage()) {
            img_language_en.setSelected(false);
            img_language_vi.setSelected(true);
        } else {
            img_language_vi.setSelected(false);
            img_language_en.setSelected(true);
        }
    }

    public static String VI_LANGUAGE = "vi-VN";
    public static String EN_LANGUAGE = "en";

    public boolean getFlagChangeLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("CommonPrefs", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("Language", AppData.language).equals(VI_LANGUAGE)) {
            return true;
        } else {
            return false;
        }

    }

    private void initView() {
        ln_language_vi = findViewById(R.id.ln_language_vi);
        btn_changeLanguage_confirm = findViewById(R.id.btn_changeLanguage_confirm);
        img_language_vi = findViewById(R.id.img_language_vi);
        img_language_en = findViewById(R.id.img_language_en);
        ln_language_en = findViewById(R.id.ln_language_en);

    }

    @Override
    public void onClick(View v) {
        if (ln_language_vi.getId() == v.getId()) {
            if (!img_language_vi.isSelected()) {
                img_language_vi.setSelected(!img_language_vi.isSelected());
                img_language_en.setSelected(false);

            } else {
                return;
            }
        } else if (ln_language_en.getId() == v.getId()) {
            if (!img_language_en.isSelected()) {
                img_language_en.setSelected(!img_language_en.isSelected());
                img_language_vi.setSelected(false);

            } else {
                return;
            }
        } else if (btn_changeLanguage_confirm.getId() == v.getId()) {
            if (getFlagChangeLanguage() && img_language_vi.isSelected()) {
                return;
            } else if (!getFlagChangeLanguage() && img_language_en.isSelected()) {
                return;
            }

            if (img_language_en.isSelected()) {
                saveFlagChangeLanguage(true);
            } else {
                saveFlagChangeLanguage(false);
            }
            Intent intent = new Intent(ChangeLanguageActivity.this, MainActivity.class);
            intent.putExtra("changeTheme", "success");
            setResult(RESULT_OK, intent);
            startActivity(intent);
            finish();


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

    private void saveFlagChangeLanguage(boolean b) {
        if (b) {
            AppData.language = EN_LANGUAGE;
            saveLocale(EN_LANGUAGE);
            setLanguage();
        } else {
            AppData.language = VI_LANGUAGE;
            saveLocale(VI_LANGUAGE);
            setLanguage();
        }
    }

    private void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}