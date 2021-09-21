package com.mobile.bookingpitch_user.fragment.History;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobile.bookingpitch_user.CustomViewPager;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.adapter.PagerAdapter;
import com.mobile.bookingpitch_user.adapter.PitchWaitting_Adapter;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.mobile.bookingpitch_user.fragment.Fragment_Virtual;
import com.mobile.bookingpitch_user.model.HistoryPitch_Model.ListPitchsWaiting;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class History_Fragment extends Fragment implements View.OnClickListener{
    private View view;
    private CustomViewPager pager;
    private TabLayout tabLayout;
    private ImageView imgCalendar;
    private String regexStr = "^[0-9]{10}$";
    public static String strPhone;
    private EditText edtPhoneHistory;
    private Button btnYesHistory, btnCancelHistory, btnYesNotiApp;

    private RecyclerView recyclerView;
    private ArrayList<ListPitchsWaiting> listPitchsWaitingArrayList;
    private PitchWaitting_Adapter adapter;
    private TextView tvYes, tvYesNotiApp;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.history_fragment, container, false);

        initView();

        pager.setPagingEnabled(false);
        ViewPagerAndTabLayout();
        SetSpaceTabLayout();

        imgCalendar.setOnClickListener(this);
        if (!SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
//            imgCalendar.setVisibility(View.INVISIBLE);
        }else {
//            imgCalendar.setVisibility(View.VISIBLE);
        }

        return view;
    }



    private void initView() {
        imgCalendar = view.findViewById(R.id.imgCalendar_HistoryFragment);
        pager = view.findViewById(R.id.viewPager_history);
        tabLayout = view.findViewById(R.id.tabLayout_history);
    }

    private void SetSpaceTabLayout() {
        int betweenSpace = 40;
        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);

        for (int i = 0; i < slidingTabStrip.getChildCount() - 1; i++) {
            View v = slidingTabStrip.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.rightMargin = betweenSpace;
        }
    }

    private void ViewPagerAndTabLayout() {
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());

        adapter.AddFragment(new PitchWaiting_Fragment(), getString(R.string.dang_cho));
        adapter.AddFragment(new Fragment_Virtual(), "");
        adapter.AddFragment(new PitchKeeping_Fragment(), getString(R.string.da_dat));
        adapter.AddFragment(new Fragment_Virtual(), "");
        adapter.AddFragment(new PitchCancel_Fragment(), getString(R.string.da_huy));
        adapter.AddFragment(new Fragment_Virtual(), "");
        adapter.AddFragment(new PitchDone_Fragment(), getString(R.string.da_da));

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        tabLayout.getTabAt(1).view.setVisibility(View.GONE);
        tabLayout.getTabAt(3).view.setVisibility(View.GONE);
        tabLayout.getTabAt(5).view.setVisibility(View.GONE);
    }

    private void dialogHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View mview = getLayoutInflater().inflate(R.layout.dialog_history, null);
        edtPhoneHistory = mview.findViewById(R.id.edtPhone_DialogHistory);
        btnYesHistory = mview.findViewById(R.id.btnYes_DialogHistory);
        btnCancelHistory = mview.findViewById(R.id.btnCancel_DialogHistory);
        builder.setView(mview);
        Dialog dialogNotiApp = builder.create();
        dialogNotiApp.setCancelable(false);
        dialogNotiApp.show();
        // click btn yes
        btnYesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPhoneHistory.getText().toString().isEmpty()) {
                    notificationApp(getString(R.string.nhap_sdt));
                } else if (!edtPhoneHistory.getText().toString().matches(regexStr)) {
                    notificationApp(getString(R.string.sai_dinh_dang_sdt));
                } else {
                    strPhone = edtPhoneHistory.getText().toString();
                    dialogNotiApp.dismiss();
                }
            }
        });
        btnCancelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotiApp.dismiss();
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
    
    @Override
    public void onClick(View v) {
        if (imgCalendar.getId() == v.getId()){
            dialogHistory();
        }
    }
}
