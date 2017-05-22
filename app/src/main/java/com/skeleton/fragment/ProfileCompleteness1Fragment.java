package com.skeleton.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skeleton.R;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.userInterests.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

import static com.skeleton.R.layout.fragment_profilecompleteness1;

/**
 * Created by Rishab on 17-05-2017.
 */

public class ProfileCompleteness1Fragment extends BaseFragment {
    private MaterialEditText etRelationshipHistory, etEthnicity, etReligion, etHeight, etBodyType,
            etSmoking, etDrinking;
    private View vSelector1, vSelector2, vSelector3, vSelector4, vSelector5, vSelector6, vSelector7,
            vSelector8;
    private Button btnNext;
    private com.skeleton.model.userInterests.Response myResponse;

    /**
     * Enable floating label for {@link MaterialEditText}
     *
     * @param editTexts :list of editText
     */
    public static void enableFoatingEditText(final MaterialEditText... editTexts) {
        for (MaterialEditText editText : editTexts) {
            editText.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_profilecompleteness1, container, false);
        init(view);
        getProfileConstants();
        enableFoatingEditText(etRelationshipHistory, etEthnicity, etReligion, etHeight, etBodyType
                , etSmoking, etDrinking);

        return view;
    }

    /**
     * initialisation
     *
     * @param view view
     */
    public void init(final View view) {
        etRelationshipHistory = (MaterialEditText) view.findViewById(R.id.mtRelationHistory);
        etEthnicity = (MaterialEditText) view.findViewById(R.id.mtEthnicity);
        etReligion = (MaterialEditText) view.findViewById(R.id.mtReligion);
        etHeight = (MaterialEditText) view.findViewById(R.id.mtHeight);
        etBodyType = (MaterialEditText) view.findViewById(R.id.mtBodyType);
        etSmoking = (MaterialEditText) view.findViewById(R.id.mtSmoking);
        etDrinking = (MaterialEditText) view.findViewById(R.id.mtDrinking);
        vSelector1 = (View) view.findViewById(R.id.selector_v1);
        vSelector2 = (View) view.findViewById(R.id.selector_v2);
        vSelector3 = (View) view.findViewById(R.id.selector_v3);
        vSelector4 = (View) view.findViewById(R.id.selector_v4);
        vSelector5 = (View) view.findViewById(R.id.selector_v5);
        vSelector6 = (View) view.findViewById(R.id.selector_v6);
        vSelector7 = (View) view.findViewById(R.id.selector_v7);
        vSelector8 = (View) view.findViewById(R.id.selector_v8);

        btnNext = (Button) view.findViewById(R.id.btnNext);
    }

    /**
     * profile constants
     */
    public void getProfileConstants() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getConstants().enqueue(new ResponseResolver<Response>(getContext(), true, true) {
            @Override
            public void success(final com.skeleton.model.userInterests.Response response) {
                if ("200".equals(String.valueOf(response.getStatusCode()))) {
                    ProfileCompleteness1Fragment.this.myResponse = response;
                    etRelationshipHistory.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etEthnicity.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etReligion.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etHeight.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etBodyType.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etSmoking.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etDrinking.setOnClickListener(ProfileCompleteness1Fragment.this);
                    btnNext.setOnClickListener(ProfileCompleteness1Fragment.this);
                }
            }

            @Override
            public void failure(final APIError error) {

            }
        });
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mtRelationHistory:
                alertDropBox("Relationship History", myResponse.getData().getRelationshipHistory(), etRelationshipHistory, vSelector1);
                break;
            case R.id.mtEthnicity:
                alertDropBox("Ethnicity", myResponse.getData().getEthnicity(), etEthnicity, vSelector2);

                break;
            case R.id.mtReligion:
                alertDropBox("Religion", myResponse.getData().getReligion(), etReligion, vSelector3);
                break;
            case R.id.mtHeight:
                alertDropBox("Height", myResponse.getData().getHeight(), etHeight, vSelector4);
                break;
            case R.id.mtBodyType:
                alertDropBox("BodyType", myResponse.getData().getBodyType(), etBodyType, vSelector5);
                break;
            case R.id.mtSmoking:
                alertDropBox("Smoking", myResponse.getData().getSmoking(), etSmoking, vSelector6);
                break;
            case R.id.mtDrinking:
                alertDropBox("Drinking", myResponse.getData().getDrinking(), etDrinking, vSelector7);
                break;
            case R.id.btnNext:

                updateInfo();
                break;
            default:
                break;

        }
    }

    /**
     * @param mTitle    title
     * @param list      list for show
     * @param etItem    items
     * @param vSelector view
     */
    public void alertDropBox(final String mTitle, final List<String> list, final MaterialEditText etItem, final View vSelector
    ) {
        final CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle);
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int item) {
                etItem.setText(cs[item]);
                vSelector.setBackgroundResource(R.color.selector_bar);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * update profile to server
     */
    public void updateInfo() {
        Log.d("debug", "updating info");
        HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                .add(AppConstant.KEY_RELATIONSHIP_HISTORY, etRelationshipHistory.getText())
                .add(AppConstant.KEY_ETHNICITY, etEthnicity.getText())
                .add(AppConstant.KEY_RELIGION, etReligion.getText())
                .add(AppConstant.KEY_HEIGHT, etHeight.getText())
                .add(AppConstant.KEY_BODY_TYPE, etBodyType.getText())
                .add(AppConstant.KEY_SMOKING, etSmoking.getText())
                .add(AppConstant.KEY_DRINKING, etDrinking.getText())
                .build().getMap();
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userUpdateProfile("bearer " + CommonData.getAccessToken(), multipartParams)
                .enqueue(new ResponseResolver<com.skeleton.model.Response>(getActivity(), false, false) {
                    @Override
                    public void success(final com.skeleton.model.Response response) {
                        android.util.Log.d("debug", response.getStatusCode().toString());
                        if ("200".equals(response.getStatusCode().toString())) {
                            CommonData.setUserData(response.getData().getUserDetails());
                            android.util.Log.d("debug", "success bro success");
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void failure(final APIError error) {
                        android.util.Log.e("debug", error.getMessage());
                    }
                });
    }
}
