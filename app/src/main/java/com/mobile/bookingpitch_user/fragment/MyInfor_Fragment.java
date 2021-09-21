package com.mobile.bookingpitch_user.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.AppInfo_Activity;
import com.mobile.bookingpitch_user.activity.HDSDActivity;
import com.mobile.bookingpitch_user.activity.ChangeLanguageActivity;
import com.mobile.bookingpitch_user.activity.SelectLoginActivity;
import com.mobile.bookingpitch_user.activity.SupportActivity;
import com.mobile.bookingpitch_user.config.SharedPref_RC;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfor_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout support, appInfo, logOut, setting, hdsd;
    private CircleImageView imgAvatar;
    private TextView tvname, tvEmail, tvYes;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.my_infor, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        initView();

        if (SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
            Picasso.get().load(R.drawable.logo_register).placeholder(R.drawable.logo_register).error(R.drawable.logo_register).into(imgAvatar);
//            tvname.setText(SharedPref_RC.getInstance_RC(getContext()).LoggedInUserName_RC());
//            tvEmail.setText(SharedPref_RC.getInstance_RC(getContext()).LoggedInEmail_RC());
            tvname.setText(R.string.xin_chao);
            if (currentUser != null) {
                tvEmail.setText(0 + currentUser.getPhoneNumber().substring(3));
            }
            logOut.setVisibility(View.VISIBLE);
        }else {
            tvname.setText(getString(R.string.xin_chao) + getString(R.string.khach));
            logOut.setVisibility(View.GONE);
        }



        support.setOnClickListener(this);
        appInfo.setOnClickListener(this);
        logOut.setOnClickListener(this);
        hdsd.setOnClickListener(this);
        setting.setOnClickListener(this);

        return view;
    }

    private void initView() {
        setting = view.findViewById(R.id.llLayoutSetting);
        hdsd = view.findViewById(R.id.llLayoutHDSD_MyInfo);
        imgAvatar = view.findViewById(R.id.imgAvatar_MyInfor);
        tvname = view.findViewById(R.id.tvName_MyInfor);
        tvEmail = view.findViewById(R.id.tvEmail_MyInfor);
        support = view.findViewById(R.id.llLayoutSupport);
        appInfo = view.findViewById(R.id.llLayoutAppInfo);
        logOut = view.findViewById(R.id.llLayoutLogout);
    }

    @Override
    public void onClick(View v) {
        if (support.getId() == v.getId()) {
            startActivity(new Intent(getContext(), SupportActivity.class));
        } else if (appInfo.getId() == v.getId()) {
            startActivity(new Intent(getContext(), AppInfo_Activity.class));
        } else if (logOut.getId() == v.getId()) {
            if (!SharedPref_RC.getInstance_RC(getActivity()).isLoggedIn_RC()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mview = getLayoutInflater().inflate(R.layout.dialod_notification_login, null);
                tvYes = mview.findViewById(R.id.tvYes_Dialog_NotificationLogin);
                builder.setView(mview);
                // click btn yes
                tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), SelectLoginActivity.class));
                        getActivity().finish();
                    }
                });
                builder.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.ban_muon_dang_xuat);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.khong, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton(R.string.thoat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        requireActivity().finish();
//                        SharedPref_RC.getInstance_RC(getActivity()).logout_RC();

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getContext(), SelectLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }else if (hdsd.getId() == v.getId()){
            startActivity(new Intent(getContext(), HDSDActivity.class));
        }


        else if (setting.getId() == v.getId()) {
            startActivity(new Intent(getContext(), ChangeLanguageActivity.class));
        }

    }
}
