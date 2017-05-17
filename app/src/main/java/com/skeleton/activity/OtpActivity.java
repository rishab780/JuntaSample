package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;

import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;

import java.util.HashMap;


/**
 * created by Rishab
 */
public class OtpActivity extends BaseActivity implements AppConstant {
    private String mPhoneno, mCountryCode, mOTP;
    private TextView tvResndOTP, tvEditNo, tvPhone;
    private Button btnVerify;
    private EditText etOTP1, etOTP2, etOTP3, etOTP4;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                verifyOTP();
            }

        });
        tvResndOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                resendOTP();
            }
        });
        tvEditNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                editNumber();
            }
        });


    }
    /**
     * initialisation function
     */
    public void init() {
        mPhoneno = CommonData.getUserData().getPhoneNo();
        mCountryCode = CommonData.getUserData().getCountryCode();
        tvPhone = (TextView) findViewById(R.id.tv_otp_phone);
        tvPhone.setText(mCountryCode + " " + mPhoneno);
        tvResndOTP = (TextView) findViewById(R.id.tv_resendOTP);
        tvEditNo = (TextView) findViewById(R.id.tv_editNumber);
        btnVerify = (Button) findViewById(R.id.btn_verify);
        etOTP1 = (EditText) findViewById(R.id.et_otp1);
        etOTP2 = (EditText) findViewById(R.id.et_otp2);
        etOTP4 = (EditText) findViewById(R.id.et_otp4);
        etOTP3 = (EditText) findViewById(R.id.et_otp3);
        mOTP = String.valueOf(etOTP1) + String.valueOf(etOTP2) + String.valueOf(etOTP3) + String.valueOf(etOTP4);


    }
    /**
     * server call to resend otp
     */
    public void resendOTP() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userResendOTP("bearer" + " " + CommonData.getAccessToken())
                .enqueue(new ResponseResolver<CommonResponse>(OtpActivity.this, true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        Log.d("debug", commonResponse.getMessage());
                        Toast.makeText(OtpActivity.this, "New OTP Sent", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debug", error.getMessage());

                    }
                });


    }
    /**
     * server call to verify otp
     */
    public void verifyOTP() {
        HashMap<String, String> commonParams = new CommonParams.Builder()
                .add(AppConstant.KEY_COUNTRY_CODE, mCountryCode)
                .add(AppConstant.KEY_PHONE, mPhoneno)
                .add(AppConstant.KEY_OTP, mOTP).build().getMap();
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userVerifyOTP("bearer" + " " + CommonData.getAccessToken(), commonParams)
                .enqueue(new ResponseResolver<CommonResponse>(OtpActivity.this, true, true) {
                    @Override
                    public void success(final CommonResponse commonResponse) {
                        Log.d("debug", commonResponse.getMessage());

                    }

                    @Override
                    public void failure(final APIError error) {
                        Log.d("debug", error.getMessage());

                    }
                });
    }
    /**
     * @param requestCode request code for given result
     * @param resultCode  result ok or not
     * @param data        data from child activity
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            tvPhone.setText(CommonData.getUserData().getCountryCode() + CommonData.getUserData().getPhoneNo());
            resendOTP();
        }
    }
    /**
     * edit no
     */
    public void editNumber() {
        Intent intent = new Intent(OtpActivity.this, ChangePhoneNoActivity.class);
        startActivityForResult(intent, 0);

    }
}
