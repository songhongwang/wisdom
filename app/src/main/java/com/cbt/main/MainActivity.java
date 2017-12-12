package com.cbt.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.fragment.ExpertFragment;
import com.cbt.main.fragment.MarketFragment;
import com.cbt.main.fragment.MineFragment;
import com.cbt.main.fragment.IndexFragment;
import com.cbt.main.fragment.MoreFragment;
import com.cbt.main.moments.ImageWatcher;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener, IWatcherImage, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
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
    private ImageWatcher vImageWatcher; // 朋友圈滑动图片工具

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isTranslucentStatus = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            isTranslucentStatus = true;
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //透明导航栏

        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
        initOther(isTranslucentStatus); // 其他页面用的数据
    }

    private void initOther(boolean isTranslucentStatus) {
        vImageWatcher = ImageWatcher.Helper.with(this) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(this) // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                .setLoader(new ImageWatcher.Loader() {
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                lc.onResourceReady(resource);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                lc.onLoadStarted(placeholder);
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                lc.onLoadFailed(errorDrawable);
                            }
                        });

                    }
                })
                .create();
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


    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
        Toast.makeText(MainActivity.this, "长按" + pos + " " + url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public MessagePicturesLayout.Callback getWatcherCallBack() {
        return this;
    }
}
