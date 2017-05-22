package com.skeleton.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;
import com.skeleton.util.imagepicker.ImageChooser;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by Rishab on 12-05-2017.
 */

public class SignUpFragment extends BaseFragment {
    private MaterialEditText etName, etCountryCode, etCNumber, etDOB, etEmail, etPassword, etCpassword;
    private RadioGroup rgGender;
    private Button btnSignUp;
    private CheckBox cbTOS;
    private ImageView ivDisplay;
    private ImageChooser imageChooser;
    private File imageFile;
    private int checkedId, mGender;
    private String mOrientation = "Gay";
    private Date date;
    private Calendar myCalendar = Calendar.getInstance();
    private DateFormat dateFormat;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        checkedId = rgGender.getCheckedRadioButtonId();
        checkGender();
//        etDOB.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(final View v) {
//                // TODO Auto-generated method stub
//                new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (validate()) {
                    HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                            .add(AppConstant.KEY_FNAME, etName.getText().toString())
                            .add(AppConstant.KEY_DOB, etDOB.getText().toString())
                            .add(AppConstant.KEY_COUNTRY_CODE, etCountryCode.getText().toString())
                            .add(AppConstant.KEY_PHONE, etCNumber.getText().toString())
                            .add(AppConstant.KEY_EMAIL, etEmail.getText().toString())
                            .add(AppConstant.KEY_PASSWORD, etPassword.getText().toString())
                            .add(AppConstant.KEY_LANGUAGE, AppConstant.VALUE_LANGUAGE)
                            .add(AppConstant.KEY_DEVICE_TYPE, AppConstant.VALUE_DEVICE_TYPE)
                            .add(AppConstant.KEY_DEVICE_TOKEN, AppConstant.VALUE_DEVICE_TOKEN)
                            .add(AppConstant.KEY_APP_VERSION, AppConstant.VALUE_APP_VERSION)
                            .add(AppConstant.KEY_GENDER, mGender)
                            .add(AppConstant.KEY_ORIENTATION, mOrientation)
                            .add(AppConstant.KEY_PROFILE_PIC, imageFile).build().getMap();
                    ApiInterface apiInterface = RestClient.getApiInterface();
                    apiInterface.userRegister(multipartParams)
                            .enqueue(new ResponseResolver<Response>(getActivity(), true, true) {
                                @Override
                                public void success(final com.skeleton.model.Response response) {
                                    Log.d("debug", "success: " + response.getStatusCode());
                                    Log.d("debug", "success: " + response.getStatusCode());

                                    if ("200".equals(response.getStatusCode().toString())) {

                                        clearEditText(etName, etDOB, etCpassword,
                                                etEmail, etPassword, etCNumber);
//                                        Intent intent = new Intent(getActivity(), DisplayResponseActivity.class);
//                                        intent.putExtra("response", response);
//                                        startActivity(intent);
                                        CommonData.saveAccessToken(response.getData().getAccessToken());
                                        CommonData.setUserData(response.getData().getUserDetails());
                                        getActivity().setResult(Activity.RESULT_OK, new Intent());
                                        getActivity().finish();
                                    }


                                }

                                @Override
                                public void failure(final APIError error) {

                                }
                            });

                }

            }
        });


        return view;
    }

    /**
     * Initialisation method
     *
     * @param view view for setting
     */

    public void init(final View view) {
        etName = (MaterialEditText) view.findViewById(R.id.etName);
        etCountryCode = (MaterialEditText) view.findViewById(R.id.etCountryCode);
        etCNumber = (MaterialEditText) view.findViewById(R.id.etPhoneNo);
        etDOB = (MaterialEditText) view.findViewById(R.id.etDOB);
        etEmail = (MaterialEditText) view.findViewById(R.id.etEmailAddr);
        etPassword = (MaterialEditText) view.findViewById(R.id.etPassword);
        etCpassword = (MaterialEditText) view.findViewById(R.id.etConfirmPassword);
        rgGender = (RadioGroup) view.findViewById(R.id.rgGender);
        btnSignUp = (Button) view.findViewById(R.id.btn_signUp);
        ivDisplay = (ImageView) view.findViewById(R.id.ivProfile);
        cbTOS = (CheckBox) view.findViewById(R.id.cbTOS);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(final DatePicker view, final int year, final int monthOfYear,
//                                  final int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
        ivDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                imageChooser = new ImageChooser(new ImageChooser.Builder(SignUpFragment.this));
                imageChooser.selectImage(new ImageChooser.OnImageSelectListener() {
                    @Override
                    public void loadImage(final List<ChosenImage> list) {
                        imageFile = new File(list.get(0).getOriginalPath());
                        if (imageFile != null) {
                            Glide.with(getContext())
                                    .load(list.get(0).getQueryUri())
                                    .into(ivDisplay);

                        }

                    }

                    @Override
                    public void croppedImage(final File mCroppedImage) {

                    }
                });
            }
        });

    }

    /**
     * validation of all the text fields
     *
     * @return returns boolean value
     */
    public Boolean validate() {
        if (ValidateEditText.checkName(etName, true)
                && ValidateEditText.checkPhoneNumber(etCNumber)
                && ValidateEditText.checkEmail(etEmail)
                && (checkDOB(etDOB))
                && ValidateEditText.checkPassword(etPassword, false)
                && ValidateEditText.checkPassword(etCpassword, true)
                && ValidateEditText.comparePassword(etPassword, etCpassword)
                && (checkTOS(cbTOS))) {
            return true;
        }
        return false;

    }

    /**
     * check the validation of Dob
     *
     * @param editText Dob
     * @return Boolean
     */
    private boolean checkDOB(final EditText editText) {
        String s = editText.getText().toString();
        try {
            date = dateFormat.parse(s);
            Log.d("debug", "valid date");
            return true;

        } catch (ParseException e) {
            editText.setError(getString(R.string.error_invalid_data));
            Log.d("debug", "invalid date");
            return false;
        }
    }

    /**
     * if check box is ticked
     *
     * @param checkBox checkbox of tos
     * @return boolean
     */
    private boolean checkTOS(final CheckBox checkBox) {
        if (checkBox.isChecked()) {
            return true;
        } else {
            checkBox.setError(getString(R.string.error_checkbox_unselected));
            return false;
        }
    }

    /**
     * date label
     */
    private void updateLabel() {
        etDOB.setText(dateFormat.format(myCalendar.getTime()));
    }

    /**
     * check gender
     */
    private void checkGender() {
        Log.d("debug", String.valueOf(checkedId));
        if (checkedId == -1) {
            Log.d("debug", "no data");
        } else if (checkedId == R.id.rbMale) {
            mGender = 0;
            Log.d("debug", "0");
        } else {
            mGender = 1;
            Log.d("debug", "1");
        }

    }

    /**
     * deletes previous
     *
     * @param editText eit texts
     */

    public void clearEditText(final EditText... editText) {
        for (EditText editText1 : editText) {
            editText1.setText("");

        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageChooser.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageChooser.onActivityResult(requestCode, resultCode, data);
    }
}
