package com.cbt.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cbt.main.fragment.MomentsFragment;
import com.cbt.main.fragment.TopicFragment;
import com.cbt.main.fragment.ZaiqingFragment;
import com.cbt.main.fragment.ZhuanjiaFragment;
import com.cbt.main.model.MomentMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 17/12/10.
 * 朋友圈adapter
 */

public class MyShoucangAdapter extends FragmentStatePagerAdapter {
    private List<String> mDataList = new ArrayList<>();
    public MyShoucangAdapter(FragmentManager fm) {
        super(fm);
        initTitlesData();

    }

    private void initTitlesData() {
        mDataList.add("   灾情   ");
        mDataList.add("   农情   ");
        mDataList.add(" 专家咨询 ");
        mDataList.add(" 市场信息 ");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
		if (position == 0)
        {
            return ZaiqingFragment.getInstance(position == 0 ? MomentMode.zai_qing : MomentMode.nong_qing,2);
        }
        else if (position == 1)
        {
            return MomentsFragment.getInstance(position == 0 ? MomentMode.zai_qing : MomentMode.nong_qing,2);
        }
        else if (position == 2)
        {
            return ZhuanjiaFragment.getInstance(MomentMode.my_shoucang);
        }
        else
        {
            return TopicFragment.getInstance(MomentMode.my_shoucang);
        }

        //return MomentsFragment.getInstance(position == 0 ? MomentMode.zai_qing : MomentMode.nong_qing);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}
