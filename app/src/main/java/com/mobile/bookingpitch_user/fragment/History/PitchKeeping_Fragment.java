package com.mobile.bookingpitch_user.fragment.History;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.BookNowPitchActivity;
import com.mobile.bookingpitch_user.activity.SelectLoginActivity;
import com.mobile.bookingpitch_user.activity.VerifyOTPActivity;
import com.mobile.bookingpitch_user.adapter.PitchKeeping_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.HistoryPitch;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsKeeping;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PitchKeeping_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<ListPitchsKeeping> listPitchsKeepings;
    private PitchKeeping_Adapter adapter;
    private TextView tvYes, tvYesNotiApp;
    private LinearLayout linearLayout;
    private Button btnLogin, btnYesNotiApp;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.pitchkeeping_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        initView();

        if (!SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            // init progress dialog
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.dang_tai_dl));
            getAllPitchKeeping();
        }

        view.findViewById(R.id.btnLogin_PitchKeepingFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SelectLoginActivity.class));
                getActivity().finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.green_));

        return view;
    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_PitchKeeping);
        recyclerView = view.findViewById(R.id.recySavePitch_PitchKeepingFragment);
        linearLayout = view.findViewById(R.id.llLayout_PitchKeepingFragment);
    }

    private void notificationApp(String text) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
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

    private void getAllPitchKeeping() {
        progressDialog.show();

        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllMyPitch(0+currentUser.getPhoneNumber().substring(3))
                .enqueue(new Callback<HistoryPitch>() {
                    @Override
                    public void onResponse(Call<HistoryPitch> call, Response<HistoryPitch> response) {
                        progressDialog.dismiss();
                        HistoryPitch historyPitch = response.body();
                        if (historyPitch != null) {
                            listPitchsKeepings = new ArrayList<>();
                            listPitchsKeepings.addAll(historyPitch.getData().getListPitchsKeeping());

                            adapter = new PitchKeeping_Adapter(getContext(), listPitchsKeepings);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryPitch> call, Throwable t) {
                        progressDialog.dismiss();
//                        notificationApp("Không có dữ liệu hiển thị. Bạn vui lòng tới màn hình trang chủ để đặt sân");
                        Log.e("err save pitch", t.getMessage());
                    }
                });
    }

    @Override
    public void onRefresh() {
        if (listPitchsKeepings != null) {
            getAllPitchKeeping();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

}
