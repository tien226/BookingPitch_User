package com.mobile.bookingpitch_user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobile.bookingpitch_user.R;
import com.mobile.bookingpitch_user.ZaloPay.Api.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CreateNewOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtMoney;
    private Button btnCreateNewOrder, btnPay, btnYesNotiApp;
    private TextView tvResult, tvYesNotiApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_order);

        Toolbar toolbar = findViewById(R.id.toolbar_CreateNewOrder);
        toolbar.setTitle(R.string.thanh_toan);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);
        // init components with ids
        initView();

        btnCreateNewOrder.setOnClickListener(this);
        btnPay.setOnClickListener(this);

    }
    private void notificationApp(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewOrderActivity.this);
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
        edtMoney = findViewById(R.id.edtMoney_CreateNewOrder);
        btnCreateNewOrder = findViewById(R.id.btnCreateNewOrder);
        btnPay = findViewById(R.id.btnPay_CreateNewOrder);
        tvResult = findViewById(R.id.tvResult_CreateNewOrder);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (btnCreateNewOrder.getId() == v.getId()) {
            if (edtMoney.getText().toString().isEmpty()){
                notificationApp(getString(R.string.nhap_so_tien_thanh_toan));
            }else {
                CreateOrder orderAPI = new CreateOrder();

                try {
                    JSONObject data = orderAPI.createOrder(edtMoney.getText().toString());
                    Log.d("Amount", edtMoney.getText().toString());
                    tvResult.setVisibility(View.VISIBLE);
                    String code = data.getString("return_code");
//                Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                    if (code.equals("1")) {
                        tvResult.setText(data.getString("zp_trans_token"));
                        tvResult.setVisibility(View.GONE);
                        btnPay.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String token = tvResult.getText().toString();

                ZaloPaySDK.getInstance().payOrder(CreateNewOrderActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String transactionId, String transToken, final String appTransID) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notificationApp("Payment Success \n" + transactionId);

//                                new AlertDialog.Builder(CreateNewOrderActivity.this)
//                                        .setTitle("Payment Success")
//                                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                            }
//                                        })
//                                        .setNegativeButton("Cancel", null).show();
                            }
                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        notificationApp("User Cancel Payment \n" + zpTransToken);

//                        new AlertDialog.Builder(CreateNewOrderActivity.this)
//                                .setTitle("User Cancel Payment")
//                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .setNegativeButton("Cancel", null).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        notificationApp("Payment Fail: \n" + zaloPayError.toString());


//                        new AlertDialog.Builder(CreateNewOrderActivity.this)
//                                .setTitle("Payment Fail")
//                                .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .setNegativeButton("Cancel", null).show();
                    }

                });
            }
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}