package com.cbt.main.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vigorous on 17/12/18.
 * 区别BaseActivity在于没有设置状态栏
 */

public abstract class BaseActivity2 extends FragmentActivity {
    @Nullable@BindView(R.id.tv_title)
    TextView mTvTitle;
    @Nullable@BindView(R.id.iv_back)
    ImageView mIvBack;
    @Nullable@BindView(R.id.iv_complete)
    ImageView mIvFinish;

    private Activity mActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏高度
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = this.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
        onCCreate(savedInstanceState);
        ButterKnife.bind(this);
        mActivity = this;
        if(mIvBack != null){
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.finish();
                }
            });
        }

        initUI();
    }

    public abstract void onCCreate(@Nullable Bundle savedInstanceState);

    public abstract void initUI();


}
