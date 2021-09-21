package com.mobile.bookingpitch_user.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.mobile.bookingpitch_user.PushFCM.FcmNotificationsSender;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.adapter.YardList_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.Dao;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.dialog.successFull_BookPitch_dialog;
import com.mobile.bookingpitch_user.dialog.successFull_PutPitchMultiDate_dialog;
import com.mobile.bookingpitch_user.dialog.successFull_PutPitchSelectDate_dialog;
import com.mobile.bookingpitch_user.model.PutPitch;
import com.mobile.bookingpitch_user.model.PutPitchMultiDate;
import com.mobile.bookingpitch_user.model.SpanPitch;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private String strStartDate, strDateAPI, strDateAPINext, strDateAPINext3, strDateAPINext4, strformatDateAPI;
    private Button btnBookNow, btnYesNotiApp;
    private successFull_BookPitch_dialog dialog;
    private successFull_PutPitchMultiDate_dialog dialogPutPitchMulti;
    private successFull_PutPitchSelectDate_dialog dialogPutPitchSelectDate;
    private int slAoDau = 0, slNuoc = 0, priceTrongTai = 0, priceNuoc, priceAoDau, totalPrice;
    private CheckBox cbAoDau, cbNuoc, cbTrongTai, cbFixedCalendar, cbSetMultiDate;
    private String regexStr = "^[0-9]{10}$";
    public static String strPhonePutPitchSelectDate, strPhoneBookPitch, strPhonePutPitchMulti, strDate, strDateNext, strDateNext3, strDateNext4, strTimeStart, strTimeEnd, strSpanSpinner;
    private Spinner spSpan;
    private SpanPitch spanPitch;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private CalendarPickerView calendar;
    private RelativeLayout relative_BookDate;
    private LinearLayout layout_SetMultiDate;
    private List<String> dateAnySelectDate = new ArrayList<>();
//    private TextView tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;
//    private boolean Thu2, Thu3, Thu4, Thu5, Thu6, Thu7, CN;
//    private LinearLayout layoutFixedCalendar;
//    private Date date2, date3, date4, date5, date6, date7, datecn, date2Next, date3Next, date4Next, date5Next, date6Next, date7Next, datecnNext;
    //    private RecyclerView recycListDateMonth;
//    private DateMonth_Adapter dateMonthAdapter;
//    private List<DateMonth> dateMonthList;
//    private QuantityListener quantityListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now_pitch);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

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
        if (currentUser != null) {
            edtPhone.setText(0 + currentUser.getPhoneNumber().substring(3));
        }

        Picasso.get().load(YardList_Adapter.strImg).placeholder(R.drawable.img_details_pitch).error(R.drawable.img_details_pitch).into(imgPitch);
        tvName.setText(YardList_Adapter.strName);
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getInstance(locale);
        tvPrime.setText(format.format(Integer.valueOf(YardList_Adapter.strPrice)) + " đ");

//        if (DetailsPitchActivity.strSpanDetails != null) {
//            tvTime.setText(new Dao().getSpanPitchID(BookNowPitchActivity.this, DetailsPitchActivity.strSpanDetails));
//        }

        Locale localePrice = new Locale("vi", "VN");
        NumberFormat formatPrice = NumberFormat.getInstance(localePrice);
        tvPriceWater.setText("(" + formatPrice.format(Integer.valueOf(YardList_Adapter.strPriceWater)) + " đ/" + getString(R.string.binh) + " 20l)");

        edtDate.setOnClickListener(this);
        btnBookNow.setOnClickListener(this);
        imgAdd_slNuoc.setOnClickListener(this);
        imgGiam_slNuoc.setOnClickListener(this);
        cbTrongTai.setOnClickListener(this);
        cbAoDau.setOnClickListener(this);
        cbFixedCalendar.setOnClickListener(this);
        cbSetMultiDate.setOnClickListener(this);

//        tvMonday.setOnClickListener(this);
//        tvTuesday.setOnClickListener(this);
//        tvWednesday.setOnClickListener(this);
//        tvThursday.setOnClickListener(this);
//        tvFriday.setOnClickListener(this);
//        tvSaturday.setOnClickListener(this);
//        tvSunday.setOnClickListener(this);
//        edtMultiDate.setOnClickListener(this);

        dataSpan();
        content();

        //chọn nhiều ngày
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);
        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(4);
        //calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        }
        calendar.scrollToDate(new Date());
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
            priceAoDau = 80000;
        }

        if (cbNuoc.isChecked() == false) {
            priceNuoc = 0;
        } else {
            priceNuoc = slNuoc * Integer.valueOf(YardList_Adapter.strPriceWater);
        }

        if (cbSetMultiDate.isChecked() == false) {
            if (cbFixedCalendar.isChecked() == false) {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) + priceNuoc + priceTrongTai + priceAoDau;
            } else {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 4 + priceNuoc + priceTrongTai + priceAoDau;
            }

        } else {
            if (cbSetMultiDate.isChecked() == false) {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) + priceNuoc + priceTrongTai + priceAoDau;
            } else {
                if (dateAnySelectDate.size() == 1) {
                    totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 1 + priceNuoc + priceTrongTai + priceAoDau;
                } else if (dateAnySelectDate.size() == 2) {
                    totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 2 + priceNuoc + priceTrongTai + priceAoDau;
                } else if (dateAnySelectDate.size() == 3) {
                    totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 3 + priceNuoc + priceTrongTai + priceAoDau;
                } else if (dateAnySelectDate.size() == 4) {
                    totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 4 + priceNuoc + priceTrongTai + priceAoDau;
                }
            }
        }

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
                // truyền thông tin màn success full
                strDate = strStartDate;

                // thứ tuần kế tiếp 2
                calendar.add(Calendar.DATE, +7);
                SimpleDateFormat formatAPINext = new SimpleDateFormat("ddMMyyyy");
                strDateAPINext = formatAPINext.format(calendar.getTime());

                SimpleDateFormat formatNext = new SimpleDateFormat("dd-MM-yyyy");
                strDateNext = formatNext.format(calendar.getTime());

                // thứ tuần kế tiếp 3
                calendar.add(Calendar.DATE, +7);
                SimpleDateFormat formatAPINext3 = new SimpleDateFormat("ddMMyyyy");
                strDateAPINext3 = formatAPINext3.format(calendar.getTime());

                SimpleDateFormat formatNext3 = new SimpleDateFormat("dd-MM-yyyy");
                strDateNext3 = formatNext3.format(calendar.getTime());

                // thứ tuần kế tiếp 4
                calendar.add(Calendar.DATE, +7);
                SimpleDateFormat formatAPINext4 = new SimpleDateFormat("ddMMyyyy");
                strDateAPINext4 = formatAPINext4.format(calendar.getTime());

                SimpleDateFormat formatNext4 = new SimpleDateFormat("dd-MM-yyyy");
                strDateNext4 = formatNext4.format(calendar.getTime());
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void initView() {
        layout_SetMultiDate = findViewById(R.id.layout_SetMultiDate);
        relative_BookDate = findViewById(R.id.relative_BookDate);
        cbSetMultiDate = findViewById(R.id.cbSetMultiDate);
        calendar = findViewById(R.id.calendar_view);
//        recycListDateMonth = findViewById(R.id.listDateMonth);
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
        cbFixedCalendar = findViewById(R.id.cbFixedCalendar);
//        tvMonday = findViewById(R.id.tvMonday_BookNowPitch);
//        tvTuesday = findViewById(R.id.tvTuesday_BookNowPitch);
//        tvWednesday = findViewById(R.id.tvWednesday_BookNowPitch);
//        tvThursday = findViewById(R.id.tvThursday_BookNowPitch);
//        tvFriday = findViewById(R.id.tvFriday_BookNowPitch);
//        tvSaturday = findViewById(R.id.tvSaturday_BookNowPitch);
//        tvSunday = findViewById(R.id.tvSunday_BookNowPitch);
//        layoutFixedCalendar = findViewById(R.id.llLayout_FixedCalendar);

    }

    private void openDialog() {
        dialog = new successFull_BookPitch_dialog();
        dialog.show(getSupportFragmentManager(), "TAG");
    }

    private void openDialogPutPichMtltiDate() {
        dialogPutPitchMulti = new successFull_PutPitchMultiDate_dialog();
        dialogPutPitchMulti.show(getSupportFragmentManager(), "TAGPUTPITCHMULTI");
    }

    private void openDialogPutPichSelectDate() {
        dialogPutPitchSelectDate = new successFull_PutPitchSelectDate_dialog();
        dialogPutPitchSelectDate.show(getSupportFragmentManager(), "TAGPUTPITCHSELECT");
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
            if (cbSetMultiDate.isChecked()) {
                callAPIBookSelectDate();
            } else {
                if (cbFixedCalendar.isChecked()) {
                    callAPIBookMultiDate();
                } else {
                    callAPIBookPitch();
                }
            }
        } else if (cbSetMultiDate.getId() == v.getId()) {
            if (cbSetMultiDate.isChecked()) {
                layout_SetMultiDate.setVisibility(View.VISIBLE);
                relative_BookDate.setVisibility(View.GONE);
                cbFixedCalendar.setVisibility(View.GONE);
                cbFixedCalendar.setChecked(false);
            } else {
                layout_SetMultiDate.setVisibility(View.GONE);
                relative_BookDate.setVisibility(View.VISIBLE);
                cbFixedCalendar.setVisibility(View.VISIBLE);
            }
        }
//        else if (btnSelectMultiDate.getId() == v.getId()) {
//            edtMultiDate.setText("");
//            ArrayList<Date> selectedDates = (ArrayList<Date>) calendar.getSelectedDates();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//            for (int i = 0; i < selectedDates.size(); i++) {
//                Date tempDate = selectedDates.get(i);
//                String formattedDate = sdf.format(tempDate);
//                edtMultiDate.append(formattedDate);
//
//                // định dạng ngày truyền api
//                SimpleDateFormat sdfAPI = new SimpleDateFormat("ddMMyyyy");
//                strformatDateAPI = sdfAPI.format(tempDate);
//
//            }
//        }
//        else if (cbFixedCalendar.getId() == v.getId()) {
//            if (cbFixedCalendar.isChecked()) {
//                layoutFixedCalendar.setVisibility(View.VISIBLE);
//            } else {
//                layoutFixedCalendar.setVisibility(View.GONE);
//            }
//        }
//        else if (tvMonday.getId() == v.getId()) {
//            Thu2 = !Thu2;
//            tvMonday.setTextColor(Thu2 ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvMonday.setBackgroundColor(Thu2 ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//
//        } else if (tvTuesday.getId() == v.getId()) {
//            Thu3 = !Thu3;
//            tvTuesday.setTextColor(Thu3 ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvTuesday.setBackgroundColor(Thu3 ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//        } else if (tvWednesday.getId() == v.getId()) {
//            Thu4 = !Thu4;
//            tvWednesday.setTextColor(Thu4 ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvWednesday.setBackgroundColor(Thu4 ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//        } else if (tvThursday.getId() == v.getId()) {
//            Thu5 = !Thu5;
//            tvThursday.setTextColor(Thu5 ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvThursday.setBackgroundColor(Thu5 ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//        } else if (tvFriday.getId() == v.getId()) {
//            Thu6 = !Thu6;
//            tvFriday.setTextColor(Thu6 ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvFriday.setBackgroundColor(Thu6 ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//        } else if (tvSaturday.getId() == v.getId()) {
//            Thu7 = !Thu7;
//            tvSaturday.setTextColor(Thu7 ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvSaturday.setBackgroundColor(Thu7 ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//        } else if (tvSunday.getId() == v.getId()) {
//            CN = !CN;
//            tvSunday.setTextColor(CN ? BookNowPitchActivity.this.getResources().getColor(R.color.white)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.black));
//            tvSunday.setBackgroundColor(CN ? BookNowPitchActivity.this.getResources().getColor(R.color.green_)
//                    : BookNowPitchActivity.this.getResources().getColor(R.color.gray_bg));
//        }
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
        } else if (spanPitch.getId().equals("00")) {
            notificationApp(getString(R.string.lua_chon_ca_dat_san));
        } else if (edtName.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_ho_ten));
        }
//        else if (edtPhone.getText().toString().isEmpty()) {
//            notificationApp(getString(R.string.nhap_sdt));
//        } else if (!edtPhone.getText().toString().matches(regexStr)) {
//            notificationApp(getString(R.string.sai_dinh_dang_sdt));
//        }
        else {
            Map<String, String> maps = new HashMap<>();
            maps.put("pitchID", YardList_Adapter.strPitchId);
            maps.put("pitchName", YardList_Adapter.strName);
            maps.put("span", spanPitch.getId());
            maps.put("userID", strPhoneBookPitch);
            maps.put("userName", edtName.getText().toString());
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
                                    FcmNotificationsSender sender = new FcmNotificationsSender("/topics/all",
                                            "Thông báo ca đặt sân mới", YardList_Adapter.strName + " " + spanPitch.getTime() + " " + edtDate.getText().toString(), getApplicationContext(), BookNowPitchActivity.this);
                                    sender.SendNotifications();
                                    // show bottom dialog
                                    openDialog();
                                } else {
                                    notificationApp(getString(R.string.ca_da_co_nguoi_dat));
                                }
                            } else {
                                APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                                notificationApp(message.getMessage());
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

    public class APIError {
        private String message;

        public String getMessage() {
            return message;
        }
    }

    private void callAPIBookMultiDate() {
        if (edtDate.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.chon_ngay_dat));
        } else if (spanPitch.getId().equals("00")) {
            notificationApp(getString(R.string.lua_chon_ca_dat_san));
        } else if (edtName.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_ho_ten));
        } else {
            strPhonePutPitchMulti = edtPhone.getText().toString();
            List<String> dateAny = new ArrayList<>();
            dateAny.add(strDateAPI);
            dateAny.add(strDateAPINext);
            dateAny.add(strDateAPINext3);
            dateAny.add(strDateAPINext4);
//        dateAny.add("24092021");
//        dateAny.add("25092021");
//        dateAny.add("26092021");
//        if (Thu2) {
//            Calendar c = Calendar.getInstance();
//            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date2 = sdf.parse(String.valueOf(c.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf.format(date2));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf.format(date2));
//
//            // thứ tuần kế tiếp
//            Calendar cNext = Calendar.getInstance();
//            cNext.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//            cNext.add(Calendar.DATE, +7);
//            SimpleDateFormat sdfNext = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdfNext = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date2Next = sdfNext.parse(String.valueOf(cNext.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdfNext.format(date2Next));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf.format(date2Next));
//
//        } else {
////            t2 = 0;
//
//        }
//        if (Thu3) {
//            Calendar c3 = Calendar.getInstance();
//            c3.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
//            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf3 = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date3 = sdf3.parse(String.valueOf(c3.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf3.format(date3));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf3.format(date3));
//
//            // thứ tuần kế tiếp
//            Calendar c3Next = Calendar.getInstance();
//            c3Next.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
//            c3Next.add(Calendar.DATE, +7);
//            SimpleDateFormat sdf3Next = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf3Next = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date3Next = sdf3Next.parse(String.valueOf(c3Next.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf3Next.format(date3Next));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf3.format(date3Next));
//
//        } else {
////            t2 = 0;
//        }
//        if (Thu4) {
//            Calendar c4 = Calendar.getInstance();
//            c4.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
//            SimpleDateFormat sdf4 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf4 = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date4 = sdf4.parse(String.valueOf(c4.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf4.format(date4));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            dateAny.add(outputSdf4.format(date4));
//
//            // thứ tuần kế tiếp
//            Calendar c4Next = Calendar.getInstance();
//            c4Next.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
//            c4Next.add(Calendar.DATE, +7);
//            SimpleDateFormat sdf4Next = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf4Next = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date4Next = sdf4Next.parse(String.valueOf(c4Next.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf4Next.format(date3Next));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf4.format(date4Next));
//
//        } else {
////            t2 = 0;
//        }
//        if (Thu5) {
//            Calendar c5 = Calendar.getInstance();
//            c5.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
//            SimpleDateFormat sdf5 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf5 = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date5 = sdf5.parse(String.valueOf(c5.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf5.format(date5));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf5.format(date5));
//
//            // thứ tuần kế tiếp
//            Calendar c5Next = Calendar.getInstance();
//            c5Next.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
//            c5Next.add(Calendar.DATE, +7);
//            SimpleDateFormat sdf5Next = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf5Next = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date5Next = sdf5Next.parse(String.valueOf(c5Next.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf5Next.format(date5Next));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf5.format(date5Next));
//
//        } else {
//            //  t2 = 0;
//        }
//        if (Thu6) {
//            Calendar c6 = Calendar.getInstance();
//            c6.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
//            SimpleDateFormat sdf6 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf6 = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date6 = sdf6.parse(String.valueOf(c6.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf6.format(date6));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf6.format(date6));
//
//            // thứ tuần kế tiếp
//            Calendar c6Next = Calendar.getInstance();
//            c6Next.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
//            c6Next.add(Calendar.DATE, +7);
//            SimpleDateFormat sdf6Next = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf6Next = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date6Next = sdf6Next.parse(String.valueOf(c6Next.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf6Next.format(date6Next));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf6.format(date6Next));
//
//        } else {
//            //  t2 = 0;
//        }
//        if (Thu7) {
//            Calendar c7 = Calendar.getInstance();
//            c7.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//            SimpleDateFormat sdf7 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf7 = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date7 = sdf7.parse(String.valueOf(c7.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf7.format(date7));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf7.format(date7));
//
//            // thứ tuần kế tiếp
//            Calendar c7Next = Calendar.getInstance();
//            c7Next.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//            c7Next.add(Calendar.DATE, +7);
//            SimpleDateFormat sdf7Next = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdf7Next = new SimpleDateFormat("ddMMyyyy");
//            try {
//                date7Next = sdf7Next.parse(String.valueOf(c7Next.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdf7Next.format(date7Next));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdf7.format(date7Next));
//
//        } else {
//            //  t2 = 0;
//        }
//        if (CN) {
//            Calendar cn = Calendar.getInstance();
//            cn.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//            cn.add(Calendar.DATE, +7);
//            SimpleDateFormat sdfcn = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdfcn = new SimpleDateFormat("ddMMyyyy");
//            try {
//                datecn = sdfcn.parse(String.valueOf(cn.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdfcn.format(datecn));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdfcn.format(datecn));
//
//            // thứ tuần kế tiếp
//            Calendar cnNext = Calendar.getInstance();
//            cnNext.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//            cnNext.add(Calendar.DATE, +14);
//            SimpleDateFormat sdfcnNext = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
//            SimpleDateFormat outputSdfcnNext = new SimpleDateFormat("ddMMyyyy");
//            try {
//                datecnNext = sdfcnNext.parse(String.valueOf(cnNext.getTime()));
////                Log.e(BookNowPitchActivity.class.getName(), "day of week: " + outputSdfcnNext.format(datecnNext));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            dateAny.add(outputSdfcn.format(datecnNext));
//
//        } else {
//            //  t2 = 0;
//        }

            Log.e(BookNowPitchActivity.class.getName(), "list dateany: " + dateAny);
            Map<String, List<String>> mapDateAny = new HashMap<>();

            Map<String, String> maps = new HashMap<>();
            maps.put("pitchID", YardList_Adapter.strPitchId);
            maps.put("pitchName", YardList_Adapter.strName);
            maps.put("span", spanPitch.getId());
            maps.put("userID", strPhonePutPitchMulti);
            maps.put("userName", edtName.getText().toString());
            mapDateAny.put("dateAny", dateAny);
            maps.put("state", "0");
            maps.put("totalPrice", String.valueOf(totalPrice));
            maps.put("price", YardList_Adapter.strPrice);
            maps.put("umpire", cbTrongTai.isChecked() == true ? "true" : "false");
            maps.put("quantityWater", String.valueOf(slNuoc));
            maps.put("priceWater", YardList_Adapter.strPriceWater);
            maps.put("image", YardList_Adapter.strImg);
            maps.put("tshirt", cbAoDau.isChecked() == true ? "true" : "false");
            maps.put("detail", YardList_Adapter.strDetail);
            maps.put("createBy", "user");
            maps.put("dayOfWeek", "3");

            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
            String strDateNow = dateFormat.format(Calendar.getInstance().getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("hhmm", Locale.getDefault());
            String strTimeNow = timeFormat.format(Calendar.getInstance().getTime());
//        Log.e(BookNowPitchActivity.class.getName(), "date time" + strDateNow + "-" + strTimeNow);
            maps.put("codeSpecial", "Many" + 0 + currentUser.getPhoneNumber().substring(3) + strDateNow + strTimeNow); //ngày giờ hệ thống

            Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
            retrofit.create(InterfaceAPI.class)
                    .putPitchMultiDate(maps, mapDateAny)
                    .enqueue(new Callback<PutPitchMultiDate>() {
                        @Override
                        public void onResponse(Call<PutPitchMultiDate> call, Response<PutPitchMultiDate> response) {
                            PutPitchMultiDate putPitchMultiDate = response.body();
                            if (putPitchMultiDate != null) {
                                if (putPitchMultiDate.getSuccess() == true) {
//                                Toast.makeText(BookNowPitchActivity.this, "đặt sân thành công", Toast.LENGTH_SHORT).show();
                                    openDialogPutPichMtltiDate();
                                } else {
                                    notificationApp(getString(R.string.ca_da_co_nguoi_dat));
//                                    Toast.makeText(BookNowPitchActivity.this, "ca có ai đặt", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                                notificationApp(message.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<PutPitchMultiDate> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });
        }

    }

    private void callAPIBookSelectDate() {
        if (spanPitch.getId().equals("00")) {
            notificationApp(getString(R.string.lua_chon_ca_dat_san));
        } else if (edtName.getText().toString().isEmpty()) {
            notificationApp(getString(R.string.nhap_ho_ten));
        } else {

            dateAnySelectDate = new ArrayList<>();
            ArrayList<Date> selectedDates = (ArrayList<Date>) calendar.getSelectedDates();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            for (int i = 0; i < selectedDates.size(); i++) {
                Date tempDate = selectedDates.get(i);
                String formattedDate = sdf.format(tempDate);

                dateAnySelectDate.add(formattedDate);
            }
            Log.e(BookNowPitchActivity.class.getName(), "date any chọn lịch: " + dateAnySelectDate);
            if (dateAnySelectDate.size() == 1) {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 1 + priceNuoc + priceTrongTai + priceAoDau;
            } else if (dateAnySelectDate.size() == 2) {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 2 + priceNuoc + priceTrongTai + priceAoDau;
            } else if (dateAnySelectDate.size() == 3) {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 3 + priceNuoc + priceTrongTai + priceAoDau;
            } else if (dateAnySelectDate.size() == 4) {
                totalPrice = Integer.valueOf(YardList_Adapter.strPrice) * 4 + priceNuoc + priceTrongTai + priceAoDau;
            }
            Log.e(BookNowPitchActivity.class.getName(), "total price select date: " + totalPrice);
            strPhonePutPitchSelectDate = edtPhone.getText().toString();

            Map<String, List<String>> mapDateAny = new HashMap<>();
            Map<String, String> maps = new HashMap<>();

            maps.put("pitchID", YardList_Adapter.strPitchId);
            maps.put("pitchName", YardList_Adapter.strName);
            maps.put("span", spanPitch.getId());
            maps.put("userID", strPhonePutPitchSelectDate);
            maps.put("userName", edtName.getText().toString());
            mapDateAny.put("dateAny", dateAnySelectDate);
            maps.put("state", "0");
            maps.put("totalPrice", String.valueOf(totalPrice));
            maps.put("price", YardList_Adapter.strPrice);
            maps.put("umpire", cbTrongTai.isChecked() == true ? "true" : "false");
            maps.put("quantityWater", String.valueOf(slNuoc));
            maps.put("priceWater", YardList_Adapter.strPriceWater);
            maps.put("image", YardList_Adapter.strImg);
            maps.put("tshirt", cbAoDau.isChecked() == true ? "true" : "false");
            maps.put("detail", YardList_Adapter.strDetail);
            maps.put("createBy", "user");
            maps.put("dayOfWeek", "3");

            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
            String strDateNow = dateFormat.format(Calendar.getInstance().getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("hhmm", Locale.getDefault());
            String strTimeNow = timeFormat.format(Calendar.getInstance().getTime());
//        Log.e(BookNowPitchActivity.class.getName(), "date time" + strDateNow + "-" + strTimeNow);
            maps.put("codeSpecial", "Many" + 0 + currentUser.getPhoneNumber().substring(3) + strDateNow + strTimeNow); //ngày giờ hệ thống

            Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
            retrofit.create(InterfaceAPI.class)
                    .putPitchMultiDate(maps, mapDateAny)
                    .enqueue(new Callback<PutPitchMultiDate>() {
                        @Override
                        public void onResponse(Call<PutPitchMultiDate> call, Response<PutPitchMultiDate> response) {
                            PutPitchMultiDate putPitchMultiDate = response.body();
                            if (putPitchMultiDate != null) {
                                if (putPitchMultiDate.getSuccess() == true) {
                                    openDialogPutPichSelectDate();
//                                    Toast.makeText(BookNowPitchActivity.this, "đặt sân thành công", Toast.LENGTH_SHORT).show();

                                } else {
                                    notificationApp(getString(R.string.ca_da_co_nguoi_dat));
//                                    Toast.makeText(BookNowPitchActivity.this, "ca có ai đặt", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);

                                if (message.getMessage().length() == 20) {
                                    notificationApp("Vui lòng kiểm tra lại ngày đặt sân không quá 4 ngày");
                                } else {
                                    notificationApp(message.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PutPitchMultiDate> call, Throwable t) {
                            notificationApp(getString(R.string.loi_call_api));
                        }
                    });
        }
    }

    private void dataSpan() {
        ArrayList<SpanPitch> list = new ArrayList<>();
        list.add(new SpanPitch("00", getString(R.string.lua_chon_ca_dat_san)));
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
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FF000000"));
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

//    private void daysOfCurrentWeek() {
//        Date refDate = new Date();
//
//        Date[] days = getDaysOfWeek(refDate, Calendar.getInstance().getFirstDayOfWeek());
//
//        for (Date day : days) {
////            System.out.println(day);
//            Log.e(BookNowPitchActivity.class.getName(), "daysOfCurrentWeek" + day);
//        }
//
//    }
//
//    private static Date[] getDaysOfWeek(Date refDate, int firstDayOfWeek) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(refDate);
//        calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
//        Date[] daysOfWeek = new Date[7];
//        for (int i = 0; i < 7; i++) {
//            daysOfWeek[i] = calendar.getTime();
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        return daysOfWeek;
//    }
//
//    private void printDatesInMonth(int year, int month) {
//        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
//        Calendar cal = Calendar.getInstance();
//        cal.clear();
//        cal.set(year, month - 1, 1);
//        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        for (int i = 0; i < daysInMonth; i++) {
//            System.out.println(fmt.format(cal.getTime()));
//            cal.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//
//    }

//    private void getListDateMonth() {
//        dateMonthList = new ArrayList<>();
//        dateMonthList.add(new DateMonth("14092021"));
//        dateMonthList.add(new DateMonth("15092021"));
//        dateMonthList.add(new DateMonth("16092021"));
//        dateMonthList.add(new DateMonth("17092021"));
//        dateMonthList.add(new DateMonth("18092021"));
//
//
//        dateMonthAdapter = new DateMonth_Adapter(BookNowPitchActivity.this, dateMonthList, quantityListener);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookNowPitchActivity.this, LinearLayoutManager.VERTICAL, false);
//        recycListDateMonth.setLayoutManager(linearLayoutManager);
//        recycListDateMonth.setAdapter(dateMonthAdapter);
//    }


//    @Override
//    public void onQuantityChange(List<DateMonth> arrayList) {
//        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_SHORT).show();
//        Log.e(BookNowPitchActivity.class.getName(), arrayList.toString());
//    }
}