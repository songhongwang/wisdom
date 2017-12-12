package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.cbt.main.R;
import com.cbt.main.adapter.ExpertFragmentAdapter;
import com.cbt.main.utils.Utils;

/**
 * Created by caobotao on 16/1/4.
 */
public class ExpertFragment extends BaseFragment {

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;

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

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) mRootView.findViewById(R.id.tabs);
        mPagerSlidingTabStrip.setViewPager(mViewPager);

        setTabsValue();
    }

    private void setTabsValue() {
        if (isAdded()) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            // 设置Tab是自动填充满屏幕的
            mPagerSlidingTabStrip.setShouldExpand(false);
            // 设置Tab的分割线是透明的
//            mPagerSlidingTabStrip.setDividerColor(getResources().getColor(R.color.transparent));
            // 设置Tab底部线的高度
            mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, dm));
            // 设置Tab Indicator的高度
            mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));
            // 设置Tab Indicator的宽度
//            mPagerSlidingTabStrip.setindicatorLinePadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, dm));
            // 设置Tab标题文字的大小
            mPagerSlidingTabStrip.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, dm));
            //选中tab是否加粗
//            mPagerSlidingTabStrip.setTextIsBold(true);
            // 设置Tab Indicator的颜色
            mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.yellow));
            // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
//            mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.c_333333));
//            mPagerSlidingTabStrip.setTextColorResource(R.color.c_666666);
            // 取消点击Tab时的背景色
            mPagerSlidingTabStrip.setTabBackground(0);

            mPagerSlidingTabStrip.setScrollOffset((int) (Utils.getScreenWidth(getActivity()) * 0.5f));
        }
    }
    @Override
    protected void lazyLoad() {

    }
}
