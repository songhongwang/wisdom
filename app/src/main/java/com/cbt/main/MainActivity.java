package com.cbt.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cbt.main.fragment.ExpertFragment;
import com.cbt.main.fragment.MarketFragment;
import com.cbt.main.fragment.MineFragment;
import com.cbt.main.fragment.IndexFragment;
import com.cbt.main.fragment.MoreFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //四个Tab对应的布局
    private LinearLayout mTabIndex;
    private LinearLayout mTabMine;
    private LinearLayout mTabExpert;
    private LinearLayout mTabMarket;
    private LinearLayout mTabMore;

    //四个Tab对应的ImageButton
    private ImageButton mImgIndex;
    private ImageButton mImgMine;
    private ImageButton mImgExpert;
    private ImageButton mImgMarket;
    private ImageButton mImgMore;

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //透明导航栏

        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将四个Fragment加入集合中
        mFragments.add(new IndexFragment());
        mFragments.add(new MineFragment());
        mFragments.add(new ExpertFragment());
        mFragments.add(new MarketFragment());
        mFragments.add(new MoreFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTabIndex.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
        mTabExpert.setOnClickListener(this);
        mTabMarket.setOnClickListener(this);
        mTabMore.setOnClickListener(this);
    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mTabIndex = (LinearLayout) findViewById(R.id.id_tab_index);
        mTabMine = (LinearLayout) findViewById(R.id.id_tab_mine);
        mTabExpert = (LinearLayout) findViewById(R.id.id_tab_expert);
        mTabMarket = (LinearLayout) findViewById(R.id.id_tab_market);
        mTabMore = (LinearLayout) findViewById(R.id.id_tab_more);

        mImgIndex = (ImageButton) findViewById(R.id.id_tab_index_img);
        mImgMine = (ImageButton) findViewById(R.id.id_tab_mine_img);
        mImgExpert = (ImageButton) findViewById(R.id.id_tab_expert_img);
        mImgMarket = (ImageButton) findViewById(R.id.id_tab_market_img);
        mImgMore= (ImageButton) findViewById(R.id.id_tab_more_img);
    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_index:
                selectTab(0);
                break;
            case R.id.id_tab_mine:
                selectTab(1);
                break;
            case R.id.id_tab_expert:
                selectTab(2);
                break;
            case R.id.id_tab_market:
                selectTab(3);
                break;
            case R.id.id_tab_more:
                selectTab(4);
                break;
        }
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgIndex.setImageResource(R.mipmap.tab_icon_home_pre);
                break;
            case 1:
                mImgMine.setImageResource(R.mipmap.tab_icon_farm_pre);
                break;
            case 2:
                mImgExpert.setImageResource(R.mipmap.tab_icon_expert_pre);
                break;
            case 3:
                mImgMarket.setImageResource(R.mipmap.tab_icon_market_pre);
                break;
            case 4:
                mImgMore.setImageResource(R.mipmap.tab_icon_more_pre);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgIndex.setImageResource(R.mipmap.tab_icon_home);
        mImgMine.setImageResource(R.mipmap.tab_icon_farm);
        mImgExpert.setImageResource(R.mipmap.tab_icon_expert);
        mImgMarket.setImageResource(R.mipmap.tab_icon_market);
        mImgMore.setImageResource(R.mipmap.tab_icon_more);
    }
}
