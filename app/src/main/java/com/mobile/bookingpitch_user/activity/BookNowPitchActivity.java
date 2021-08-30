package com.mobile.bookingpitch_user.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.adapter.YardList_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.Dao;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.dialog.successFull_BookPitch_dialog;
import com.mobile.bookingpitch_user.model.PutPitch;
import com.mobile.bookingpitch_user.model.SpanPitch;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookNowPitchActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgPitch, imgAdd_slNuoc, imgGiam_slNuoc;
    private TextView tvPriceWater, tvYesNotiApp, tvName, tvPrime, tvTime, tv_slNuoc, tvTotalPrice;
    private EditText edtDate, edtPhone, edtName;
    private String strStartDate, strDateAPI;
    private Button btnBookNow, btnYesNotiApp;
    private successFull_BookPitch_dialog dialog;
    private int slAoDau = 0, slNuoc = 0, priceTrongTai = 0, priceNuoc, priceAoDau, totalPrice;
    private CheckBox cbAoDau, cbNuoc, cbTrongTai;
    private String regexStr = "^[0-9]{10}$";
    public static String strPhoneBookPitch, strDate, strTimeStart, strTimeEnd, strSpanSpinner;
    private Spinner spSpan;
    private SpanPitch spanPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now_pitch);

        Toolbar toolbar = findViewById(R.id.toolbar_BookNowPitch);
        toolbar.setTitle(R.string.dat_lich_san_bong);
        setSupportActionBar(toolbar);

        TextView textView = findViewById(R.id.abcv);
        TextView tvCaBan = findViewById(R.id.caban);
        TextView tvPitchBusy = findViewById(R.id.tvPitchBusy);
        CardView cardView = findViewById(R.id.cardView_PitchBusy);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            tvPitchBusy.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat outputSdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = sdf.parse(bundle.getString("date"));
                textView.setText(getString(R.string.ngay) + ": " + outputSdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (bundle.getStringArrayList("spanBusy").isEmpty()) {
                tvCaBan.setText(R.string.chua_co_ca_dat);
            } else {
                tvCaBan.setText(new Dao().getSpanFromListID(getApplicationContext(), bundle.getStringArrayList("spanBusy")));
            }
        }

        initView();

        Picasso.get().load(YardList_Adapter.strImg).placeholder(R.drawable.img_details_pitch).error(R.drawable.img_details_pitch).into(imgPitch);
        tvName.setText(YardList_Adapter.strName);
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        tvPrime.setText(format.format(Integer.valueOf(YardList_Adapter.strPrice)) + " đ");

        if (DetailsPitchActivity.strSpanDetails != null) {
            tvTime.setText(new Dao().getSpanPitchID(BookNowPitchActivity.this, DetailsPitchActivity.strSpanDetails));
        }

        Locale localePrice = new Locale("vi", "VN");
        NumberFormat formatPrice = NumberFormat.getInstance(localePrice);
        tvPriceWater.setText("(" + formatPrice.format(Integer.valueOf(YardList_Adapter.strPriceWater))  + " đ)");

        edtDate.setOnClickListener(this);
        btnBookNow.setOnClickListener(this);
        imgAdd_slNuoc.setOnClickListener(this);
        imgGiam_slNuoc.setOnClickListener(this);
        cbTrongTai.setOnClickListener(this);
        cbAoDau.setOnClickListener(this);

        dataSpan();
        content();
    }

    private void content() {
        if (cbTrongTai.isChecked() == false) {
            priceTrongTai = 0;
        } else {
            priceTrongTai = 150000;
        }

        if (cbAoDau.isChecked() == false) {
            priceAoDau = 0;
        } else {
            priceAoDau = 280000;
        }

        if (cbNuoc.isChecked() == false) {
            priceNuoc = 0;
        } else {
            priceNuoc = slNuoc * Integer.valueOf(YardList_Adapter.strPriceWater);
        }

        totalPrice = Integer.valueOf(YardList_Adapter.strPrice) + priceNuoc + priceTrongTai + priceAoDau;

        Locale localeTotalPrice = new Locale("vi", "VN");
        NumberFormat formatTotalPrice = NumberFormat.getInstance(localeTotalPrice);
        tvTotalPrice.setText(getString(R.string.tong) + ": " + formatTotalPrice.format(totalPrice) + " đ");
        refresh();
    }

    private void refresh() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void getDateNow() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(BookNowPitchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                strStartDate = simpleDateFormat.format(calendar.getTime());
                edtDate.setText(strStartDate);

                // dinh dang ngay truyen len server
                SimpleDateFormat formatAPI = new SimpleDateFormat("ddMMyyyy");
                strDateAPI = formatAPI.format(calendar.getTime());
                strDate = strStartDate;
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void initView() {
        spSpan = findViewById(R.id.spSpan_BookNowPitch);
        tvPriceWater = findViewById(R.id.tvPriceWater);
        cbNuoc = findViewById(R.id.cbNuoc);
        edtName = findViewById(R.id.edtNameUser_BookNowPitch);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        cbAoDau = findViewById(R.id.cbAoDau);
        cbTrongTai = findViewById(R.id.cbTrongTai);
        imgAdd_slNuoc = findViewById(R.id.Add_slNuoc);
        imgGiam_slNuoc = findViewById(R.id.giam_slNuoc);
        tv_slNuoc = findViewById(R.id.tv_slNuoc);
        edtPhone = findViewById(R.id.edtPhoneUser_BookNowPitch);
        btnBookNow = findViewById(R.id.btnBookNowPitch);
        edtDate = findViewById(R.id.edtDate_BookNowPitch);
        imgPitch = findViewById(R.id.imgBookNowPitch);
        tvName = findViewById(R.id.tvName_BookNowPitch);
        tvPrime = findViewById(R.id.tvPrime_BookNowPitch);
        tvTime = findViewById(R.id.tvTime_BookNowPitch);
    }

    private void openDialog() {
        dialog = new successFull_BookPitch_dialog();
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onClick(View v) {
        if (edtDate.getId() == v.getId()) {
            getDateNow();
        } else if (imgGiam_slNuoc.getId() == v.getId()) {
            if (slNuoc > 0) {
                slNuoc--;
            }
            tv_slNuoc.setText(String.valueOf(slNuoc));
        } else if (imgAdd_slNuoc.getId() == v.getId()) {
            slNuoc++;
            tv_slNuoc.setText(String.valueOf(slNuoc));
        } else if (btnBookNow.getId() == v.getId()) {
            callAPIBookPitch();
        }
    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookNowPitchActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_notifi_app, null);
        tvYesNotiApp = mview.findViewById(R.id.tvContent_DialogNotifiApp);
        btnYesNotiApp = mview.findViewById(R.id.btnYes_DialogNotifiApp);
        builder.setView(mview);
        Dialog dialogNotiApp = builder.create();
        dialogNotiApp.setCancelable(false);
        dialogNotiApp.show();
        // set text tvyes
        tvYesNotiApp.setText(text);
        // click btn yes
        btnYesNotiApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotiApp.dismiss();
            }
        });
    }

    private void callAPIBookPitch() {
        strPhoneBookPitch = edtPhone.getText().toString();
        Calendar calendar = Calendar.getInstance();

        if (edtDate.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.chon_ngay_dat));
        } else if (edtName.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_ho_ten));
        } else if (edtPhone.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_sdt));
        } else if (!edtPhone.getText().toString().matches(regexStr)) {
            notificationApp(getString(R.string.sai_dinh_dang_sdt));
        } else {
            Map<String, String> maps = new HashMap<>();
            maps.put("pitchID", YardList_Adapter.strPitchId);
            maps.put("pitchName", YardList_Adapter.strName);
            maps.put("span", spanPitch.getId());
            maps.put("userID", strPhoneBookPitch);
            maps.put("date", strDateAPI);
            maps.put("state", "0");
            maps.put("totalPrice", String.valueOf(totalPrice));
            maps.put("price", YardList_Adapter.strPrice);
            maps.put("umpire", cbTrongTai.isChecked() == true ? "true" : "false"); // thuê trọng  tài
            maps.put("quantityWater", String.valueOf(slNuoc)); // số lượng nước
            maps.put("priceWater", YardList_Adapter.strPriceWater);
            maps.put("image", YardList_Adapter.strImg);
            maps.put("tshirt", cbAoDau.isChecked() == true ? "true" : "false"); // thuê áo đấu
            maps.put("detail", YardList_Adapter.strDetail);
            maps.put("createBy", "user");

            Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
            retrofit.create(InterfaceAPI.class)
                    .putPitch(maps)
                    .enqueue(new Callback<PutPitch>() {
                        @Override
                        public void onResponse(Call<PutPitch> call, Response<PutPitch> response) {
                            PutPitch putPitch = response.body();

                            if (putPitch != null) {
                                if (putPitch.getSuccess() == true) {
                                    openDialog();
                                } else {
                                    notificationApp(getString(R.string.ca_da_co_nguoi_dat));
                                }
                            } else {
                                notificationApp(getString(R.string.ca_dat_san_het_time));
                            }
                        }

                        @Override
                        public void onFailure(Call<PutPitch> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                            Log.e("err put pitch", t.getMessage());
                        }
                    });
        }

    }

    private void dataSpan() {
        ArrayList<SpanPitch> list = new ArrayList<>();
        list.add(new SpanPitch("1", getString(R.string.ca) + "1: 7h00 - 9h00"));
        list.add(new SpanPitch("2", getString(R.string.ca) + "2: 9h30 - 11h30"));
        list.add(new SpanPitch("3", getString(R.string.ca) + "3: 13h00 - 15h30"));
        list.add(new SpanPitch("4", getString(R.string.ca) + "4: 16h00 - 18h00"));
        list.add(new SpanPitch("5", getString(R.string.ca) + "5: 19h00 - 21h00"));


        ArrayAdapter<SpanPitch> arrayAdapter = new ArrayAdapter<SpanPitch>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpan.setAdapter(arrayAdapter);
        spSpan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#6C6B69"));
                spanPitch = (SpanPitch) parent.getItemAtPosition(position);
                strSpanSpinner = spanPitch.getTime();
                Log.e("id span pitch", spanPitch.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}