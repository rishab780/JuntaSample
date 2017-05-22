package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;

import okhttp3.RequestBody;


/**
 * created by Rishab
 */
public class ChangePhoneNoActivity extends BaseActivity {
    private MaterialEditText etNewNumber;
    private TextView tvSubmit;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_no);
        etNewNumber = (MaterialEditText) findViewById(R.id.newNumber);
        tvSubmit = (TextView) findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                HashMap<String, RequestBody> params = new MultipartParams.Builder()
                        .add(AppConstant.KEY_NEW_NUMBER, etNewNumber.getText()).build().getMap();
                ApiInterface apiInterface = RestClient.getApiInterface();
                apiInterface.userUpdateProfile("bearer " + CommonData.getAccessToken(), params)
                        .enqueue(new ResponseResolver<Response>(ChangePhoneNoActivity.this, true, true) {
                            @Override
                            public void success(final Response response) {
                                Log.d("debug123", response.getMessage());
                                if ("200".equals(response.getStatusCode().toString())) {
                                    CommonData.setUserData(response.getData().getUserDetails());
                                    Intent intent = new Intent();
                                    intent.putExtra("MESSAGE", "num changed");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            }

                            @Override
                            public void failure(final APIError error) {
                                Log.d("debug123", error.getMessage());
                            }
                        });

            }
        });

    }
}
