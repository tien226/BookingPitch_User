package com.mobile.bookingpitch_user.fragment.History;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.mobile.bookingpitch_user.activity.SelectLoginActivity;
import com.mobile.bookingpitch_user.adapter.PitchDone_Adapter;
import com.mobile.bookingpitch_user.adapter.PitchKeeping_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.HistoryPitch_Done.HistoryDone;
import com.mobile.bookingpitch_user.model.HistoryPitch_Done.ListPitchsDone;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.HistoryPitch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PitchDone_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private ProgressDialog progressDialog;
    private ArrayList<ListPitchsDone> listPitchsDone;
    private PitchDone_Adapter pitchDoneAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.pitchdone_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        initView();

        if (!SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            // init progress dialog
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.dang_tai_dl));
            getAllPitchDone();
        }

        view.findViewById(R.id.btnLogin_PitchDoneFragment).setOnClickListener(new View.OnClickListener() {
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

    private void getAllPitchDone() {
        progressDialog.show();

        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getPitchDone(0+currentUser.getPhoneNumber().substring(3))
                .enqueue(new Callback<HistoryDone>() {
                    @Override
                    public void onResponse(Call<HistoryDone> call, Response<HistoryDone> response) {
                        progressDialog.dismiss();
                        HistoryDone historyDone = response.body();
                        if (historyDone != null) {
                            listPitchsDone = new ArrayList<>();
                            listPitchsDone.addAll(historyDone.getData().getListPitchsDone());

                            pitchDoneAdapter = new PitchDone_Adapter(getContext(), listPitchsDone);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(pitchDoneAdapter);

                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryDone> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });

    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_PitchDone);
        recyclerView = view.findViewById(R.id.recySavePitch_PitchDoneFragment);
        linearLayout = view.findViewById(R.id.llLayout_PitchDoneFragment);
    }

    @Override
    public void onRefresh() {
        if (listPitchsDone != null) {
            getAllPitchDone();
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
