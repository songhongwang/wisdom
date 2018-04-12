package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cbt.main.R;

/**
 * Created by vigorous on 18/4/12.
 */

public class MyProfileActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void initUI() {
        mIvFinish.setImageResource(R.drawable.icon_complete);

    }
}
