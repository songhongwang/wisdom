package com.cbt.main.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
import com.cbt.main.R;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.fragment.ExpertFragment;
import com.cbt.main.fragment.MarketFragment;
import com.cbt.main.fragment.MineFragment;
import com.cbt.main.fragment.IndexFragment;
import com.cbt.main.fragment.MoreFragment;
import com.cbt.main.model.RtokenRsp;
import com.cbt.main.moments.ImageWatcher;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.Utils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.RongYunTokenUtil;
import com.cbt.main.view.pagertab.PagerSlidingTabStrip;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements OnClickListener, IWatcherImage, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
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
    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        boolean isTranslucentStatus = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            isTranslucentStatus = true;
        }
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题

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
        initRongYunSdk();
    }

    @Override
    public void initUI() {

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

            @Override
            public CharSequence getPageTitle(int position) {
                return "        " + position;
            }
        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        setTabsValue();

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
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_main);

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
    private void setTabsValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 设置Tab是自动填充满屏幕的
        mPagerSlidingTabStrip.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mPagerSlidingTabStrip.setDividerColor(getResources().getColor(R.color.transparent));
        // 设置Tab底部线的高度
        mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab Indicator的高度
        mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));
        // 设置Tab Indicator的宽度
//            mPagerSlidingTabStrip.setindicatorLinePadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, dm));
        // 设置Tab标题文字的大小
        mPagerSlidingTabStrip.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, dm));
        //选中tab是否加粗
//            mPagerSlidingTabStrip.setTextSize(12);
        // 设置Tab Indicator的颜色
        mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.green));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
//            mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.c_333333));
        // 取消点击Tab时的背景色
        mPagerSlidingTabStrip.setTabBackground(0);

        mPagerSlidingTabStrip.setSelectedTabColor(R.color.green);

        mPagerSlidingTabStrip.setScrollOffset((int) (Utils.getScreenWidth(this) * 0.5f));
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


    private void initRongYunSdk() {
        Map<String, String> headers = RongYunTokenUtil.getHeaderMap();

        String uid = "18600211554";
        String uname = "vigorous2";
        String logo = "https://www.baidu.com/img/bd_logo1.png";

        ApiClient.getInstance().getRongYunService().getToken(uid, uname, logo,headers).enqueue(new Callback<RtokenRsp>() {
            @Override
            public void onResponse(Call<RtokenRsp> call, Response<RtokenRsp> response) {
                if(response != null){
                    if(response.body() != null){
                        connectRongYunserver(response.body().getToken());
                    }else{
                        ToastUtils.show(MainActivity.this, "rong sdk token lost");
                    }
                }
            }
            @Override
            public void onFailure(Call<RtokenRsp> call, Throwable t) {
                ToastUtils.show(MainActivity.this, "rong sdk token load fail");
            }
        });
    }

    private void connectRongYunserver(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(MainActivity.this, "rong sdk server load incorrect");
                    }
                });
            }
            @Override
            public void onSuccess(String userid) {
                Log.d("LoginActivity", "--onSuccess" + userid);
//                startActivity(new Intent(ChatActivity.this, MainActivity.class));
//                finish();
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(MainActivity.this, "rong sdk server load fail");
                    }
                });
            }
        });
    }
}
