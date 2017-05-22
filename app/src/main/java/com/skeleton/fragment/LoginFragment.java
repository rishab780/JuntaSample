package com.skeleton.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;

/**
 * Created by Rishab on 12-05-2017.
 */

public class LoginFragment extends BaseFragment {
    private MaterialEditText etLogin, etPasword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        btnLogin = (Button) view.findViewById(R.id.btnSignin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (validateData()) {
                    HashMap<String, String> hashMap = new CommonParams.Builder()
                            .add(AppConstant.KEY_EMAIL, etLogin.getText().toString())
                            .add(AppConstant.KEY_PASSWORD, etPasword.getText().toString())
                            .add(AppConstant.KEY_DEVICE_TYPE, AppConstant.VALUE_DEVICE_TYPE)
                            .add(AppConstant.KEY_LANGUAGE, AppConstant.VALUE_LANGUAGE)
                            .add(AppConstant.KEY_DEVICE_TOKEN, AppConstant.VALUE_DEVICE_TOKEN)
                            .add(AppConstant.KEY_FLUSH_SESSIONS, AppConstant.VALUE_FLUSH_SESSION)
                            .add(AppConstant.KEY_APP_VERSION, AppConstant.VALUE_APP_VERSION)
                            .build().getMap();
                    ApiInterface apiInterface = RestClient.getApiInterface();
                    apiInterface.userLogin(null, hashMap).enqueue(new ResponseResolver<Response>(getContext(), false, false) {
                        @Override
                        public void success(final Response response) {

                            if ("200".equals(response.getStatusCode().toString())) {
                                CommonData.saveAccessToken(response.getData().getAccessToken());
                                CommonData.setUserData(response.getData().getUserDetails());
                                Log.d("debug", "accEss ALLOWED");
                                getActivity().setResult(Activity.RESULT_OK);
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void failure(final APIError error) {
                            android.util.Log.d("debug", error.getMessage());

                        }
                    });


                }

            }
        });
        return view;
    }

    /**
     * initialisaton function
     *
     * @param view view to fragment
     */
    public void init(final View view) {
        etLogin = (MaterialEditText) view.findViewById(R.id.etLoginEmail);
        etPasword = (MaterialEditText) view.findViewById(R.id.etLoginPassword);

    }

    /**
     * validation
     *
     * @return boolean ans
     */
    public Boolean validateData() {
        if (ValidateEditText.checkEmail(etLogin)
                && ValidateEditText.checkPassword(etPasword, false)) {
            return true;
        }
        return false;

    }
}
