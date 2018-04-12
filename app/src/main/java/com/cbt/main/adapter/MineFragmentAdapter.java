package com.cbt.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cbt.main.fragment.MomentsFragment;
import com.cbt.main.fragment.ZaiqingFragment;
import com.cbt.main.model.MomentMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 17/12/10.
 * 朋友圈adapter
 */

public class MineFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mDataList = new ArrayList<>();
    public MineFragmentAdapter(FragmentManager fm) {
        super(fm);
        initTitlesData();

    }

    private void initTitlesData() {
        mDataList.add("        灾情        ");
        mDataList.add("        农情        ");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return ZaiqingFragment.getInstance(position == 0 ? MomentMode.zai_qing : MomentMode.nong_qing);
        }
        else
        {
            return MomentsFragment.getInstance(position == 0 ? MomentMode.zai_qing : MomentMode.nong_qing);
        }

    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}
