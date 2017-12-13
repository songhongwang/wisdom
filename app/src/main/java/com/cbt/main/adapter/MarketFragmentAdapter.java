package com.cbt.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cbt.main.fragment.MomentsFragment;
import com.cbt.main.fragment.TopicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 17/12/10.
 * 朋友圈adapter
 */

public class MarketFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mDataList = new ArrayList<>();
    public MarketFragmentAdapter(FragmentManager fm) {
        super(fm);
        initTitlesData();

    }

    private void initTitlesData() {
        mDataList.add("        最新消息        ");
        mDataList.add("        我的发布        ");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return new TopicFragment();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}
