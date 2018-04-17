package com.cbt.main.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cbt.main.fragment.MomentsFragment;
import com.cbt.main.fragment.ZhuanjiaFragment;
import com.cbt.main.model.MomentMode;

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
        ZhuanjiaFragment fragment = new ZhuanjiaFragment();
        Bundle bundle = new Bundle();
        if (position == 0)
        {
            bundle.putSerializable("mode", MomentMode.zj_zuire);
        }
        else if (position == 1)
        {
            bundle.putSerializable("mode", MomentMode.zj_zuixin);
        }
        else
        {
            bundle.putSerializable("mode", MomentMode.zj_wode);
        }
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}
