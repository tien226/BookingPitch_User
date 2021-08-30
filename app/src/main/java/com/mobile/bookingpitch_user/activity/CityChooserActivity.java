package com.mobile.bookingpitch_user.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.adapter.CityChooserAdapter;
import com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataCity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CityChooserActivity extends AppCompatActivity {

    private com.mobile.bookingpitch_user.model.CityAndDistrict.HDDataCity HDDataCity;

    private EditText edtSearch;

    private List<HDDataCity> list = new ArrayList<>();

    private CityChooserAdapter cityChooserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_chooser);

        edtSearch = this.findViewById(R.id.edt_search);

        if (getIntent().getSerializableExtra("listCity") != null) {
            list.addAll((Collection<HDDataCity>) getIntent().getSerializableExtra("listCity"));
        }

        RecyclerView recyclerView = findViewById(R.id.container);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cityChooserAdapter = new CityChooserAdapter(this, list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cityChooserAdapter);

        cityChooserAdapter.setOnclick(city -> {
            HDDataCity = city;
            Intent data = new Intent();
            data.putExtra("city", city);
            setResult(1, data);
            CityChooserActivity.this.finish();
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<HDDataCity> filteredList = new ArrayList<>();
        for (HDDataCity item : list) {
            if (item.getNameCity().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        cityChooserAdapter.filterList(filteredList);
    }

    private void initView(){

    }

    private void initData(){

    }

    private void initAdapter(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        HDDataCity = null;
        Intent data = new Intent();
        data.putExtra("city", data);
        setResult(1, data);
    }
}