package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.bookingpitch_user.R;
import com.squareup.picasso.Picasso;

public class DetailsNewsActivity extends AppCompatActivity {
    private ImageView imgPicture;
    private TextView tvTitle, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);

        Toolbar toolbar = findViewById(R.id.toolbar_DetailsNews);
        toolbar.setTitle(R.string.tin_tuc_viet_hoa);
        setSupportActionBar(toolbar);

        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Picasso.get().load(bundle.getString("imgNews")).placeholder(R.drawable.image_news).error(R.drawable.image_news).into(imgPicture);
            tvTitle.setText(bundle.getString("titleNews"));
            tvContent.setText(bundle.getString("contentNews"));
        }
    }

    private void initView() {
        imgPicture = findViewById(R.id.imgDetailsNews);
        tvTitle = findViewById(R.id.tvTitle_DetailsNews);
        tvContent = findViewById(R.id.tvContent_DetailsNews);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}