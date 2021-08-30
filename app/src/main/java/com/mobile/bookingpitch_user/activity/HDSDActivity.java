package com.mobile.bookingpitch_user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.mobile.bookingpitch_user.R;
import com.github.barteksc.pdfviewer.PDFView;

public class HDSDActivity extends AppCompatActivity {
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdsdactivity);

        Toolbar toolbar = findViewById(R.id.toolbar_HDSD);
        toolbar.setTitle(R.string.hdsd_my_info);
        setSupportActionBar(toolbar);

        pdfView = findViewById(R.id.pdfViewer);
        pdfView.fromAsset("HDSD.pdf").load();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}