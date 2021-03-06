package com.cbt.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cbt.main.fragment.MomentsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 17/12/10.
 * 朋友圈adapter
 */

public class ExpertFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mDataList = new ArrayList<>();
    public ExpertFragmentAdapter(FragmentManager fm) {
        super(fm);
        initTitlesData();

    }

    private void initTitlesData() {
        mDataList.add("        最热        ");
        mDataList.add("        最新        ");
        mDataList.add("        我的        ");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return new MomentsFragment();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}
