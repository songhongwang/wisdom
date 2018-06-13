package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cbt.main.R;
import com.cbt.main.fragment.MomentsFragment;
import com.cbt.main.fragment.ZaiqingFragment;
import com.cbt.main.model.MomentMode;

/**
 * Created by vigorous on 18/4/12.
 * 他人关注的农气 灾情 页面
 */

public class MyAttentionActivity extends BaseActivity {
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_attention);
    }

    @Override
    public void initUI() {
        mIvFinish.setVisibility(View.GONE);
        String qufen = (String) getIntent().getSerializableExtra("qufen");
        String otheruserid = (String) getIntent().getSerializableExtra("otheruserid");
        if (qufen.equals("nongqing"))
        {
            final MomentsFragment mMomentsFragment = MomentsFragment.getInstance(MomentMode.my_attention,8);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ismy",8);
            bundle.putSerializable("otheruserid",otheruserid);
            mMomentsFragment.setArguments(bundle);
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
        else
        {
            final ZaiqingFragment mMomentsFragment = ZaiqingFragment.getInstance(MomentMode.my_attention,8);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ismy",8);
            bundle.putSerializable("otheruserid",otheruserid);
            mMomentsFragment.setArguments(bundle);
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

}
