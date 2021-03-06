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
import com.mobile.bookingpitch_user.adapter.PitchCancel_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.HistoryPitch;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsCancel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PitchCancel_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private TextView tvYes, tvYesNotiApp;
    private Button btnLogin, btnYesNotiApp;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    private ArrayList<ListPitchsCancel> listPitchsCancels;
    private PitchCancel_Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.pitchcancel_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        initView();

        if (!SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            // init progress dialog
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.dang_tai_dl));
            getAllCancelPitch();
        }

        view.findViewById(R.id.btnLogin_PitchCancelFragment).setOnClickListener(new View.OnClickListener() {
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

    private void getAllCancelPitch() {
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
                            listPitchsCancels = new ArrayList<>();
                            listPitchsCancels.addAll(historyPitch.getData().getListPitchsCancel());

                            adapter = new PitchCancel_Adapter(getContext(), listPitchsCancels);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(getContext(), "Kh??ng c?? d??? li???u!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryPitch> call, Throwable t) {
                        progressDialog.dismiss();
//                        notificationApp(getString(R.string.noti_history));
                        notificationApp(getString(R.string.loi_call_api));
                        Log.e("err save pitch",  t.getMessage());
                    }
                });
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

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_PitchCancel);
        recyclerView = view.findViewById(R.id.recySavePitch_PitchCancelFragment);
        linearLayout = view.findViewById(R.id.llLayout_PitchCancelFragment);
    }

    @Override
    public void onRefresh() {
        if (listPitchsCancels != null) {
            getAllCancelPitch();
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
