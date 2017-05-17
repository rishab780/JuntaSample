package com.skeleton.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


import com.skeleton.R;
import com.skeleton.fragment.LoginFragment;
import com.skeleton.fragment.SignUpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * created by rishab
 */

public class HomeActivity extends BaseActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        PagerAdapter pagerAdapter = new com.skeleton.adapter.PagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     *  initialisation function
     */
    public void init() {
        viewPager = (ViewPager) findViewById(R.id.vp_home_activity);
        tabLayout = (TabLayout) findViewById(R.id.tl_home_activity);
        fragmentList = new ArrayList<>();
        fragmentList.add(new LoginFragment());
        fragmentList.add(new SignUpFragment());
    }
}
