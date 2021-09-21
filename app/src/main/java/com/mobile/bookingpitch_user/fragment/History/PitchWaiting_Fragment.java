package com.mobile.bookingpitch_user.fragment.History;

import android.app.AlertDialog;
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
import android.widget.EditText;
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
import com.mobile.bookingpitch_user.activity.SelectLoginActivity;
import com.mobile.bookingpitch_user.adapter.PitchWaitting_Adapter;
import com.mobile.bookingpitch_user.config.ConfigRetrofitApi;
import com.mobile.bookingpitch_user.config.InterfaceAPI;
import com.mobile.bookingpitch_user.config.InterfaceClickDialog;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.model.CancelPitch;
import com.mobile.bookingpitch_user.model.CreateUser;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.HistoryPitch;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsWaiting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PitchWaiting_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, InterfaceClickDialog {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<ListPitchsWaiting> listPitchsWaitingArrayList;
    private PitchWaitting_Adapter adapter;
    private TextView tvYes, tvYesNotiApp;
    private LinearLayout linearLayout;
    private Button btnLogin, btnYesNotiApp, btnYesHistory;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText edtPhoneHistory;
    private String regexStr = "^[0-9]{10}$";
    public static String strPhone;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private InterfaceClickDialog clickDialog = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.save_fragment, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        initView();

        if (!SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            // init progress dialog
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.dang_tai_dl));
            getAllSavePitch();
        }

        view.findViewById(R.id.btnLogin_SaveFragment).setOnClickListener(new View.OnClickListener() {
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


    private void getAllSavePitch() {
        progressDialog.show();

        Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
        retrofit.create(InterfaceAPI.class)
                .getAllMyPitch(0 + currentUser.getPhoneNumber().substring(3))
                .enqueue(new Callback<HistoryPitch>() {
                    @Override
                    public void onResponse(Call<HistoryPitch> call, Response<HistoryPitch> response) {
                        progressDialog.dismiss();
                        HistoryPitch historyPitch = response.body();
                        if (historyPitch != null) {
                            listPitchsWaitingArrayList = new ArrayList<>();
                            listPitchsWaitingArrayList.addAll(historyPitch.getData().getListPitchsWaiting());

                            adapter = new PitchWaitting_Adapter(getContext(), listPitchsWaitingArrayList, clickDialog);
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
//                        notificationApp(getString(R.string.noti_history));
                        notificationApp(getString(R.string.loi_call_api));
//                        dialogHistory();
                        Log.e("err save pitch", t.getMessage());
                    }
                });
    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh_PitchWaiting);
        recyclerView = view.findViewById(R.id.recySavePitch_SaveFragment);
        linearLayout = view.findViewById(R.id.llLayout_SaveFragment);
    }

    @Override
    public void onRefresh() {
        if (listPitchsWaitingArrayList != null) {
            getAllSavePitch();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void onClickDialog(int position) {
        showDialogCancel(position);
    }

    private void showDialogCancel(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View mview = getLayoutInflater().inflate(R.layout.dialog_cancel_bookpitch, null);
        Button btnYes = mview.findViewById(R.id.btnYes_DialogCancel);
        Button btnCancelDialog = mview.findViewById(R.id.btnCancel_DialogCancel);
        builder.setView(mview);

        AlertDialog dialog = builder.show();
        dialog.setCancelable(false);
        // click btn yes
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(PitchWaiting_Fragment.class.getName(), PitchWaitting_Adapter._Id);
//
                if (PitchWaitting_Adapter._date.length() == 8){
                    Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
                    retrofit.create(InterfaceAPI.class)
                            // để test, nhớ sửa
                            .cancelBookPitch(PitchWaitting_Adapter._Id, "-1", "one", "user")
                            .enqueue(new Callback<CancelPitch>() {
                                @Override
                                public void onResponse(Call<CancelPitch> call, Response<CancelPitch> response) {
                                    CancelPitch cancelPitch = response.body();
                                    if (cancelPitch.getSuccess() == true) {

                                        listPitchsWaitingArrayList.remove(position);
                                        adapter.notifyItemRemoved(position);

                                        dialog.dismiss();
                                        Toast.makeText(getContext(), cancelPitch.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Có lỗi xảy ra. Bạn vui lòng tải lại trang!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CancelPitch> call, Throwable t) {
                                    Toast.makeText(getContext(), "Lỗi mạng hoặc lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Retrofit retrofit = ConfigRetrofitApi.getInstance_BooKingPitch();
                    retrofit.create(InterfaceAPI.class)
                            // để test, nhớ sửa
                            .cancelBookPitch(PitchWaitting_Adapter._codeSpecial, "-1", "many", "user")
                            .enqueue(new Callback<CancelPitch>() {
                                @Override
                                public void onResponse(Call<CancelPitch> call, Response<CancelPitch> response) {
                                    CancelPitch cancelPitch = response.body();
                                    if (cancelPitch.getSuccess() == true) {

                                        listPitchsWaitingArrayList.remove(position);
                                        adapter.notifyItemRemoved(position);

                                        dialog.dismiss();
                                        Toast.makeText(getContext(), cancelPitch.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Có lỗi xảy ra. Bạn vui lòng tải lại trang!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CancelPitch> call, Throwable t) {
                                    Toast.makeText(getContext(), "Lỗi mạng hoặc lỗi hệ thống!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
        // click btn cancel
        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
