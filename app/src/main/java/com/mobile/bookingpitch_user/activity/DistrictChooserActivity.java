package com.mobile.bookingpitch_user.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.adapter.DistrictChooserAdapter;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataDistrict;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DistrictChooserActivity extends AppCompatActivity {

    private HDDataDistrict HDDataDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_chooser);

        List<HDDataDistrict> list = new ArrayList<>();
        if (getIntent().getSerializableExtra("listDistrict") != null) {
            list.addAll((Collection<HDDataDistrict>) getIntent().getSerializableExtra("listDistrict"));
        }

        RecyclerView recyclerView = findViewById(R.id.container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DistrictChooserAdapter districtChooserAdapter = new DistrictChooserAdapter(this, list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(districtChooserAdapter);

        districtChooserAdapter.setOnclick(district -> {
            HDDataDistrict = district;
            Intent data = new Intent();
            data.putExtra("district", HDDataDistrict);
            setResult(2, data);
            DistrictChooserActivity.this.finish();
        });

    }
}