package com.cbt.main.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cbt.main.fragment.MomentsFragment;
import com.cbt.main.fragment.TopicFragment;
import com.cbt.main.model.MomentMode;

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
        TopicFragment fragment =new TopicFragment();
        Bundle bundle = new Bundle();
        if (position == 0)
        {
            bundle.putSerializable("mode", MomentMode.zj_zuixin);
        }
        else if (position == 1)
        {
            bundle.putSerializable("mode", MomentMode.zj_wode);
        }
        else
        {
            bundle.putSerializable("mode", MomentMode.zj_zuixin);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}
