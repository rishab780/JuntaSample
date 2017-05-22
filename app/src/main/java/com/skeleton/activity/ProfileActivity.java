package com.skeleton.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.skeleton.R;
import com.skeleton.database.CommonData;
import com.skeleton.fragment.ProfileCompleteness1Fragment;
import com.skeleton.fragment.ProfileCompleteness2Fragment;

/**
 * created by rishab
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction ft = fM.beginTransaction();

        if (CommonData.getUserData().getStep1CompleteOrSkip()) {
            ft.replace(R.id.fmDisplayProfile, new ProfileCompleteness2Fragment());
            ft.commit();
        } else {
            ft.replace(R.id.fmDisplayProfile, new ProfileCompleteness1Fragment());
            ft.commit();
        }

    }
}
