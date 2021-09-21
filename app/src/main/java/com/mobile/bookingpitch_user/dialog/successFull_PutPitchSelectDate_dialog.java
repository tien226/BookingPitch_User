package com.mobile.bookingpitch_user.dialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.activity.BookNowPitchActivity;
import com.mobile.bookingpitch_user.activity.MainActivity;


public class successFull_PutPitchSelectDate_dialog extends BottomSheetDialogFragment implements View.OnClickListener{
    public successFull_PutPitchSelectDate_dialog() { }
    private View view;
    private Button btnHome;
    private TextView tvPhone, tvMaps, tvPhoneUser, tvDate, tvTime;
    private int PERMISSION_REQUEST_CODE_CALL_PHONE = 555;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_successfull_putpitselectdate, container, false);

        initView();



        // nhan thong tin put pitch multi date
        tvPhoneUser.setText(BookNowPitchActivity.strPhonePutPitchSelectDate);
        tvTime.setText(BookNowPitchActivity.strSpanSpinner);
        tvDate.setText("Đặt Nhiều Ngày");

        //
        btnHome.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        tvMaps.setOnClickListener(this);

        return view;
    }

    private void initView() {
        tvPhoneUser = view.findViewById(R.id.tvPhoneUser_DialogPutPitchSelectDate);
        tvDate = view.findViewById(R.id.tvDate_DialogPutPitchSelectDate);
        tvTime = view.findViewById(R.id.tvTime_DialogPutPitchSelectDate);
        btnHome = view.findViewById(R.id.btnHome_DialogPutPitchSelectDate);
        tvPhone = view.findViewById(R.id.tvPhone_DialogPutPitchSelectDate);
        tvMaps = view.findViewById(R.id.tvMaps_DialogPutPitchSelectDate);
    }

    @Override
    public void onClick(View v) {
        if (btnHome.getId() == v.getId()) {
            startActivity(new Intent(getContext(), MainActivity.class));
        } else if (tvPhone.getId() == v.getId()) {
            askPermissionAndCall();
        } else if (tvMaps.getId() == v.getId()) {
            openAppMaps();
        }
    }


    private void askPermissionAndCall() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23

            int sendSmsPermisson = ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        PERMISSION_REQUEST_CODE_CALL_PHONE
                );
                return;
            }
        }
        callNow();
    }

    private void callNow() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + "0981480462"));
        try {
            startActivity(callIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
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
}
