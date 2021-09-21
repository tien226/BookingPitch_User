package com.mobile.bookingpitch_user.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.PitchMapActivity;
import com.mobile.bookingpitch_user.activity.SupportActivity;
import com.mobile.bookingpitch_user.adapter.News_Adapter;
import com.mobile.bookingpitch_user.adapter.PitchBusyByDate_Adapter;
import com.mobile.bookingpitch_user.adapter.YardList_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.CreateUser;
import com.mobile.bookingpitch_user.model.GetAllUser_Model.GetAllUser;
import com.mobile.bookingpitch_user.model.News_Model.News;
import com.mobile.bookingpitch_user.model.PitchBusyByDate.PutPitch;
import com.mobile.bookingpitch_user.model.YardList_Model.Datum;
import com.mobile.bookingpitch_user.model.YardList_Model.YardList;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    //    private ViewPager viewPager;
//    private PhotoAdapter photoAdapter;
//    private WormDotsIndicator worm_dots_indicator;
    private CircleImageView cImgAvatar;
    private TextView tvViewUserName, tvYesNotiApp, tvDatePitchBusy, tvDateNowPichBusy;
    private ArrayList<Datum> datumArrayList;
    private YardList_Adapter adapter;
    private RecyclerView recyListYard, recyNews, recyPitchBusyByDate;
    private ArrayList<com.mobile.bookingpitch_user.model.News_Model.Datum> newsArrayList;
    private News_Adapter newsAdapter;
    private LinearLayout llPitchMap, llPitchInfo, llMap;
    //    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Button btnYesNotiApp;
    private ImageView imgSelectDate;
    private String strDate;
    private PitchBusyByDate_Adapter pitchBusyByDatAdapter;
    private ArrayList<com.mobile.bookingpitch_user.model.PitchBusyByDate.Datum> listBusyBydate;
    private String strPhone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.home_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            strPhone = 0 + currentUser.getPhoneNumber().substring(3);
        }

        initView();

        // login no acc
        if (SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
            Picasso.get().load(R.drawable.logo_register).placeholder(R.drawable.logo_register).error(R.drawable.logo_register).into(cImgAvatar);
//            tvViewUserName.setText( ", " + SharedPref_RC.getInstance_RC(getContext()).LoggedInUserName_RC());
            if (currentUser != null) {
                tvViewUserName.setText(", " + 0 + currentUser.getPhoneNumber().substring(3));
            }

            Thread ThreadGetUser = new Thread() {
                @Override
                public void run() {
                    getAllUser();
                }
            };
            ThreadGetUser.start();
        }

        //
//        setDataIntro();

        //
        Thread ThreadYardList = new Thread() {
            @Override
            public void run() {
                getAllYardList();
            }
        };
        ThreadYardList.start();
        //
        Thread ThreadNews = new Thread() {
            @Override
            public void run() {
                getAllNews();
            }
        };
        ThreadNews.start();
        //
        Thread ThreadPitchBusyByDate = new Thread() {
            @Override
            public void run() {
                getDataPitchBusyByDate();
            }
        };
        ThreadPitchBusyByDate.start();
        //
        llPitchMap.setOnClickListener(this);
//        llPitchPrice.setOnClickListener(this);
        llPitchInfo.setOnClickListener(this);
        llMap.setOnClickListener(this);
        imgSelectDate.setOnClickListener(this);

        return view;
    }

//    private boolean isFirstTime() {
//        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
//        boolean ranBefore = preferences.getBoolean("RanBefore", false);
//        if (!ranBefore) {
//            // first time
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("RanBefore", true);
//            editor.commit();
//        }
//        return !ranBefore;
//    }

    private void getAllUser() {
        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllAccUser()
                .enqueue(new Callback<GetAllUser>() {
                    @Override
                    public void onResponse(Call<GetAllUser> call, Response<GetAllUser> response) {
                        GetAllUser getAllUser = response.body();
                        List<String> listUser = new ArrayList<>();
                        if (getAllUser != null) {
                            if (getAllUser.getSuccess() == true) {
                                for (int i = 0; i < getAllUser.getData().size(); i++) {
                                    listUser.add(getAllUser.getData().get(i).getUserID());
                                }
                                Log.e(Home_Fragment.class.getName(), "list user" + listUser);

                                if (listUser.contains(strPhone)) {
//                                    Toast.makeText(getContext(), "giống", Toast.LENGTH_SHORT).show();
                                } else {
                                    notifiInputUser();
                                }

                            } else {
                                Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAllUser> call, Throwable t) {
                        notificationApp(getString(R.string.loi_call_api));
                    }
                });

    }

    private void getAllNews() {
        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllNews()
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        News news = response.body();
                        if (news.getData() != null) {
                            newsArrayList = new ArrayList<>();
                            newsArrayList.addAll(news.getData());

                            newsAdapter = new News_Adapter(getContext(), newsArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyNews.setLayoutManager(linearLayoutManager);
                            recyNews.setAdapter(newsAdapter);
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

//                        Toast.makeText(getContext(), "Lỗi mạng hoặc lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void notifiInputUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View mview = getLayoutInflater().inflate(R.layout.dialog_input_user, null);

        EditText edtPhone = mview.findViewById(R.id.edtPhone_DialogInputUser);
        EditText edtName = mview.findViewById(R.id.edtName_DialogInputUser);
        Button btnYes = mview.findViewById(R.id.btnYes_DialogInputUser);
        builder.setView(mview);
        Dialog dialogInputUser = builder.create();
        dialogInputUser.setCancelable(false);
        dialogInputUser.show();

        edtPhone.setText(0 + currentUser.getPhoneNumber().substring(3));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().isEmpty()) {
                    notificationApp(getString(R.string.nhap_ho_ten));
                } else {

                    Map<String, String> maps = new HashMap<>();
                    maps.put("userID", 0 + currentUser.getPhoneNumber().substring(3));
                    maps.put("userName", edtName.getText().toString());
                    maps.put("typeOfUser", "0");
                    maps.put("password", "");
                    maps.put("favourite", "");
                    maps.put("history", "");

                    Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
                    retrofit.create(InterfaceAPI.class)
                            .createUser(maps)
                            .enqueue(new Callback<CreateUser>() {
                                @Override
                                public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                                    CreateUser createUser = response.body();
                                    if (createUser.getSuccess() == true) {
                                        Toast.makeText(getContext(), createUser.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialogInputUser.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), createUser.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialogInputUser.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CreateUser> call, Throwable t) {
                                    notificationApp(getString(R.string.loi_call_api));
                                }
                            });
                }

            }
        });


    }

    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    private void getAllYardList() {
        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllPitch()
                .enqueue(new Callback<YardList>() {
                    @Override
                    public void onResponse(Call<YardList> call, Response<YardList> response) {
                        YardList yardList = response.body();
                        if (yardList.getData() != null) {
                            datumArrayList = new ArrayList<>();
                            datumArrayList.addAll(yardList.getData());

                            adapter = new YardList_Adapter(getContext(), datumArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyListYard.setLayoutManager(linearLayoutManager);
                            adapter.setDataChange(datumArrayList);
                            recyListYard.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<YardList> call, Throwable t) {
//                        Toast.makeText(getContext(), "Lỗi mạnh hoặc lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                        notificationApp(getString(R.string.loi_call_api));
                        Log.e("err yard list", t.getMessage());
                    }
                });

    }

//    private void setDataIntro() {
////        photoAdapter = new PhotoAdapter(getContext(), getListPhoto());
////        viewPager.setAdapter(photoAdapter);
////        worm_dots_indicator.setViewPager(viewPager);
//
//        viewPager2.setAdapter(new sliderImgAdapter(getListPhoto(), viewPager2));
//
//        viewPager2.setClipToPadding(false);
//        viewPager2.setClipChildren(false);
//        viewPager2.setOffscreenPageLimit(3);
//        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//
//        CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer(40));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.85f + r * 0.15f);
//            }
//        });
//        viewPager2.setPageTransformer(transformer);
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                slideHandler.removeCallbacks(slideRunnable);
//                slideHandler.postDelayed(slideRunnable, 5000);
//            }
//        });
//
//    }
//
//    private Runnable slideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
//        }
//    };
//
//    private List<IntroHome> getListPhoto() {
//        List<IntroHome> list = new ArrayList<>();
//        list.add(new IntroHome(R.drawable.poster_home));
//        list.add(new IntroHome(R.drawable.poster_home));
//        list.add(new IntroHome(R.drawable.poster_home));
//        list.add(new IntroHome(R.drawable.poster_home));
//        return list;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        slideHandler.removeCallbacks(slideRunnable);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        slideHandler.postDelayed(slideRunnable, 5000);
//    }

    private void initView() {
        tvDateNowPichBusy = view.findViewById(R.id.tvDateNowPichBusyByDate_HomeFragment);
        tvDatePitchBusy = view.findViewById(R.id.tvDatePichBusyByDate_HomeFragment);
        imgSelectDate = view.findViewById(R.id.imgSelectDate_HomeFragment);
        recyPitchBusyByDate = view.findViewById(R.id.recyPitchBusyByDate_HomeFragment);
//        viewPager2 = view.findViewById(R.id.viewPager2_HomeFragment);
        llPitchMap = view.findViewById(R.id.llLayoutPitchMap_HomeFragment);
//        llPitchPrice = view.findViewById(R.id.llLayoutYardPrice_HomeFragment);
        llPitchInfo = view.findViewById(R.id.llLayoutPitchInfo_HomeFragment);
        llMap = view.findViewById(R.id.llLayoutMap_HomeFragment);
        recyNews = view.findViewById(R.id.recyNews_HomeFragment);
        recyListYard = view.findViewById(R.id.recyListYard_HomeFragment);
//        viewPager = view.findViewById(R.id.viewPager_HomeFragment);
//        worm_dots_indicator = view.findViewById(R.id.worm_dots_indicator);
        cImgAvatar = view.findViewById(R.id.imgAvatar_Home);
        tvViewUserName = view.findViewById(R.id.tvViewUserName_Home);
    }

    @Override
    public void onClick(View v) {
        if (llMap.getId() == v.getId()) {
            openAppMaps();
        } else if (llPitchMap.getId() == v.getId()) {
            startActivity(new Intent(getContext(), PitchMapActivity.class));
        } else if (imgSelectDate.getId() == v.getId()) {
            getDateNow();
            getDataPitchBusyByDate();
        } else if (llPitchInfo.getId() == v.getId()) {
            startActivity(new Intent(getContext(), SupportActivity.class));
        }
    }

    private void openAppMaps() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:21.03824794998889,105.74670126690945"));
//        Intent chooser = Intent.createChooser(intent, "Lauch Maps");
//        startActivity(chooser);

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=21.03824794998889,105.74670126690945&mode=l"));
        startActivity(intent);
    }

    private void getDateNow() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                strDate = simpleDateFormat.format(calendar.getTime());
                //
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                tvDatePitchBusy.setText(getString(R.string.ngay) + ": " + dateFormat.format(calendar.getTime()));

                getDataPitchBusyByDate();
//                Toast.makeText(getContext(), strDate, Toast.LENGTH_SHORT).show();
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void getDataPitchBusyByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        String strDateNow = dateFormat.format(Calendar.getInstance().getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        if (strDate == null) {
            tvDateNowPichBusy.setText(getString(R.string.ngay) + ": " + format.format(Calendar.getInstance().getTime()));
        } else {
            tvDateNowPichBusy.setVisibility(View.GONE);
        }

        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllPitchBusyByDate(strDate == null ? strDateNow : strDate)
                .enqueue(new Callback<PutPitch>() {
                    @Override
                    public void onResponse(Call<PutPitch> call, Response<PutPitch> response) {
                        PutPitch putPitch = response.body();
                        if (putPitch.getData() != null) {
                            listBusyBydate = new ArrayList<>();
                            listBusyBydate.addAll(putPitch.getData());

                            pitchBusyByDatAdapter = new PitchBusyByDate_Adapter(getContext(), listBusyBydate);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyPitchBusyByDate.setLayoutManager(linearLayoutManager);
                            recyPitchBusyByDate.setAdapter(pitchBusyByDatAdapter);
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PutPitch> call, Throwable t) {
                        Toast.makeText(getContext(), "lỗi mạng, hệ thống", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
