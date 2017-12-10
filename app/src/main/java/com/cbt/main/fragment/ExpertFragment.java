package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;
import com.cbt.main.adapter.ExpertFragmentAdapter;

/**
 * Created by caobotao on 16/1/4.
 */
public class ExpertFragment extends BaseFragment {

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.id_viewpager_moments);
        mViewPager.setOffscreenPageLimit(1);
        ExpertFragmentAdapter adapter = new ExpertFragmentAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

    }

    @Override
    protected void lazyLoad() {

    }
}
