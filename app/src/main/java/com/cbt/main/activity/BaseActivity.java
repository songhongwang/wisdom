package com.cbt.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * Created by vigorous on 17/12/18.
 */

public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCCreate(savedInstanceState);
        ButterKnife.bind(this);
        initUI();
    }

    public abstract void onCCreate(@Nullable Bundle savedInstanceState);
    public abstract void initUI();
}
