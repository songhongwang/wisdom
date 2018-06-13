package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cbt.main.R;
import com.cbt.main.model.event.EventLogout;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.appclear.ClearApp;
import com.cbt.main.utils.net.Constants;

import butterknife.BindView;
import io.rong.eventbus.EventBus;

/**
 * Created by vigorous on 17/12/18.
 *
 */

public class SettingActivity extends BaseActivity{

    @BindView(R.id.rl_clear)
    View mVClearApp;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.tv_quit)
    TextView mTvQuit;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQr;
    ClearApp mClearApp;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void initUI() {
        mIvFinish.setVisibility(View.GONE);
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
                SharedPreferencUtil.logout(SettingActivity.this);

                EventBus.getDefault().post(new EventLogout());

                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
//        findViewById(R.id.rl_user_type).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 修改用户类型
//                Intent intent = new Intent(SettingActivity.this, PerfactAccountActivity.class);
//                startActivity(intent);
//            }
//        });
        findViewById(R.id.rl_user_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SettingActivity.this, RegisterOrForgetActivity.class);
                intent2.putExtra("title", "忘记密码");
                startActivityForResult(intent2, 2);
            }
        });

        Glide.with(this).load(Constants.getBaseUrl() + Constants.sAppQR).into(mIvQr);
    }


}
