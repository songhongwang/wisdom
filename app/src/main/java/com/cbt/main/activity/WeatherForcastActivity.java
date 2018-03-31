package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cbt.main.R;
import com.cbt.main.fragment.WeatherLineFragment;

/**
 * Created by vigorous on 18/3/31.
 */

public class WeatherForcastActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_weather_forcast);
    }

    @Override
    public void initUI() {
        mIvFinish.setVisibility(View.GONE);

        WeatherLineFragment weatherLineFragment = new WeatherLineFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container,weatherLineFragment);
        transaction.commitAllowingStateLoss();
    }
}
