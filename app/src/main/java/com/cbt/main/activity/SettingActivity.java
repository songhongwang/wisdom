package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.utils.appclear.ClearApp;

import butterknife.BindView;

/**
 * Created by vigorous on 17/12/18.
 */

public class SettingActivity extends BaseActivity{

    @BindView(R.id.rl_clear)
    View mVClearApp;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.tv_quit)
    TextView mTvQuit;
    ClearApp mClearApp;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void initUI() {
        mClearApp = new ClearApp(SettingActivity.this);

        String cacheSize = mClearApp.caculateCacheSize();
        mTvCacheSize.setText(cacheSize);

        mVClearApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClearApp.clearAppCache();
                mTvCacheSize.setText("0KB");
            }
        });
        mTvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
