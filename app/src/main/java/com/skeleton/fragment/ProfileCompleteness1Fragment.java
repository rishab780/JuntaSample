package com.skeleton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skeleton.R;
import com.skeleton.model.Response;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.customview.MaterialEditText;

import static com.skeleton.R.layout.fragment_profilecompleteness1;

/**
 * Created by Rishab on 17-05-2017.
 */

public class ProfileCompleteness1Fragment extends BaseFragment {
    private MaterialEditText etRelationshipHistory, etEthnicity, etReligion, etHeight, etBodyType,
            etSmoking, etDrinking;
    private Button btnNext;
    private com.skeleton.model.userProfile.Response appConstants;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_profilecompleteness1, container, false);
        init(view);
        getProfileConstants();

        return view;
    }

    public void init(View view) {
        etRelationshipHistory = (MaterialEditText) view.findViewById(R.id.mtRelationHistory);
        etEthnicity = (MaterialEditText) view.findViewById(R.id.mtEthnicity);
        etReligion = (MaterialEditText) view.findViewById(R.id.mtReligion);
        etHeight = (MaterialEditText) view.findViewById(R.id.mtHeight);
        etBodyType = (MaterialEditText) view.findViewById(R.id.mtBodyType);
        etSmoking = (MaterialEditText) view.findViewById(R.id.mtSmoking);
        etDrinking = (MaterialEditText) view.findViewById(R.id.mtDrinking);
        btnNext = (Button) view.findViewById(R.id.btnNext);
    }

    public void getProfileConstants() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getConstants().enqueue(new ResponseResolver<Response>(getContext(), true, true) {
            @Override
            public void success(final Response response) {
                if ("200".equals(String.valueOf(response.getStatusCode()))) {
                    appConstants = response;
                    etRelationshipHistory.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etEthnicity.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etReligion.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etHeight.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etBodyType.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etSmoking.setOnClickListener(ProfileCompleteness1Fragment.this);
                    etDrinking.setOnClickListener(ProfileCompleteness1Fragment.this);
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
                alertDropBox("Relationship History", appConstants.getData().getRelationshipHistory();
                break;
            case R.id.etEthnicity:
                alertDropBox("Ethnicity", profileConstants.getData().getEthnicity(), etEthnicity);
                break;
            case R.id.etReligion:
                alertDropBox("Religion", profileConstants.getData().getReligion(), etReligion);
                break;
            case R.id.etHeight:
                alertDropBox("Height", profileConstants.getData().getReligion(), etHeight);
                break;
            case R.id.etBodyType:
                alertDropBox("BodyType", profileConstants.getData().getReligion(), etBodyType);
                break;
            case R.id.etSmoking:
                alertDropBox("Smoking", profileConstants.getData().getSmoking(), etSmoking);
                break;
            case R.id.etDrinking:
                alertDropBox("Drinking", profileConstants.getData().getDrinking(), etDrinking);
                break;
            case R.id.etOrientation:
                alertDropBox("Orientation", profileConstants.getData().getOrientation(), etOrientation);
                break;
            default:
                break;

        }
    }
}
