package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cbt.main.R;
import com.cbt.main.fragment.MyMineFragment;
import com.cbt.main.fragment.MyShoucangFragment;
import com.cbt.main.model.MomentMode;

/**
 * Created by vigorous on 18/4/12.
 */

public class MyShoucangActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_attention);
    }

    @Override
    public void initUI() {
        mTvTitle.setText("我的收藏");
        mIvFinish.setVisibility(View.GONE);

        final MyShoucangFragment mMomentsFragment = MyShoucangFragment.getInstance(MomentMode.my_attention);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container,mMomentsFragment);
        transaction.commitAllowingStateLoss();

        mIvFinish.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMomentsFragment.refresh();
            }
        }, 300);
    }

}
