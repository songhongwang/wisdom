package com.cbt.main.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cbt.main.R;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.callback.IWatcherImage;
import com.cbt.main.engin.SceneSurfaceView;
import com.cbt.main.model.BottomTabTip;
import com.cbt.main.model.UpdateModel;
import com.cbt.main.model.event.OnBackPressedEvent;
import com.cbt.main.fragment.ExpertFragment;
import com.cbt.main.fragment.MarketFragment;
import com.cbt.main.fragment.MineFragment;
import com.cbt.main.fragment.IndexFragment;
import com.cbt.main.fragment.MoreFragment;
import com.cbt.main.model.RtokenRsp;
import com.cbt.main.model.User;
import com.cbt.main.model.event.EventLogout;
import com.cbt.main.moments.ImageWatcher;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.Utils;
import com.cbt.main.utils.VersionCodeUpdate;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.Constants;
import com.cbt.main.utils.net.RongYunTokenUtil;
import com.cbt.main.view.pagertab.PagerSlidingTabStrip;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;
import com.cbt.main.view.sceneweather.WeatherSceneView;
import com.github.matteobattilana.weather.PrecipType;
import com.github.matteobattilana.weather.WeatherView;
import com.github.matteobattilana.weather.WeatherViewSensorEventListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cbt.main.R.id.v_cloudy;

public class MainActivity extends BaseActivity implements OnClickListener, IWatcherImage, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener,IUnReadMessageObserver {
    private WeatherSceneView mWeatherSceneView;
    private WeatherViewSensorEventListener mWeatherViewSensorEventListener;
    private WeatherView weather_view;
    private View mVCloudy; // 阴天遮罩
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

    // 底部四个红点
    private View mVTip2;
    private View mVTip3;
    private View mVTip4;

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
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions =
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
//                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                if (Build.VERSION.SDK_INT >= 19) {
                    uiOptions |= 0x00001000;
                } else {
                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                getWindow().getDecorView().setSystemUiVisibility(uiOptions);
            }
        });
        setContentView(R.layout.activity_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //透明导航栏

        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
        initOther(isTranslucentStatus); // 其他页面用的数据
        mImgIndex.postDelayed(new Runnable() {
            @Override
            public void run() {
                initRongYunSdk();
             }
        }, 500);

        checkUpdate();
    }

    @Override
    public void initUI() {
        mVCloudy = findViewById(v_cloudy);
        mWeatherSceneView = findViewById(R.id.mWeatherSceneView);
        weather_view = findViewById(R.id.weather_view);
        mWeatherViewSensorEventListener = new WeatherViewSensorEventListener(this, weather_view);
        weather_view.setWeatherData(PrecipType.CLEAR);
        weather_view.setEmissionRate(150f);
        weather_view.setSpeed(1000);
        weather_view.setAngle(2);
        weather_view.setFadeOutPercent(10f);
        weather_view.setVisibility(View.GONE);

        mWeatherSceneView.setVisibility(View.VISIBLE);

        checkAndRequestPermission(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE));
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
                return "占位"; // 占位符作用
            }
        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
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
        EventBus.getDefault().register(this);

        //设置四个Tab的点击事件
        mTabIndex.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
        mTabExpert.setOnClickListener(this);
        mTabMarket.setOnClickListener(this);
        mTabMore.setOnClickListener(this);
    }

    //初始化控件
    private void initViews() {
//        mSceneSurfaceView = (SceneSurfaceView) findViewById(R.id.mSceneSurfaceView);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_main);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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

        mVTip2= findViewById(R.id.msg_unread_count_mine);
        mVTip3= findViewById(R.id.msg_unread_count_expert);
        mVTip4= findViewById(R.id.msg_unread_count_market);

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
        mPagerSlidingTabStrip.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, dm));
        mPagerSlidingTabStrip.setTextColor(android.R.color.white);
        //选中tab是否加粗
//            mPagerSlidingTabStrip.setTextSize(12);
        // 设置Tab Indicator的颜色
        mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.green));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
//            mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.c_333333));
        // 取消点击Tab时的背景色
        mPagerSlidingTabStrip.setTabBackground(0);

        mPagerSlidingTabStrip.setSelectedTabColor(R.color.green);

        mPagerSlidingTabStrip.setScrollOffset(0);
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


    private int connectRongYunRetryCount = 0;
    private void initRongYunSdk() {
        Map<String, String> headers = RongYunTokenUtil.getHeaderMap();

        User user = SharedPreferencUtil.getLogin(this);
        if(user == null){
            ToastUtils.show(MainActivity.this, "rong sdk token load fail");
            return;
        }

        ApiClient.getInstance().getRongYunService().getToken(user.getUid(), user.getUname(), Constants.getBaseUrl() + user.getIcon(),headers).enqueue(new Callback<RtokenRsp>() {
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

        ApiClient.getInstance().getRongYunService().setNickName(user.getUname(), headers).enqueue(new Callback<RtokenRsp>() {
            @Override
            public void onResponse(Call<RtokenRsp> call, Response<RtokenRsp> response) {
                Log.d("rong_set_nick", "rong nick setup success");
            }

            @Override
            public void onFailure(Call<RtokenRsp> call, Throwable t) {
                Log.d("rong_set_nick_err", "rong nick setup fail");
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
                        if(connectRongYunRetryCount < 3){
                            connectRongYunRetryCount ++;

                            initRongYunSdk();
                        }
                    }
                });
            }
            @Override
            public void onSuccess(String userid) {
                Log.d("LoginActivity", "--onSuccess" + userid);
                User user = SharedPreferencUtil.getLogin(MainActivity.this);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(user.getUid(), user.getUname(), Uri.parse(Constants.getBaseUrl() + user.getIcon())));
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

        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };

        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSceneSurfaceView.stop();
//        mWeatherViewSensorEventListener.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mSceneSurfaceView.start();
//        mWeatherViewSensorEventListener.onResume();
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() != 2){
            super.onBackPressed();
        }else{
            EventBus.getDefault().post(new OnBackPressedEvent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventLogout eventLogout) {
        finish();
    }

    @Override
    public void onCountChanged(int i) {
//        ToastUtils.show(MainActivity.this, "message count " + i);
//        if(i > 0){
//            mMsgUnRead.setText(""+i);
//            mMsgUnRead.setVisibility(View.VISIBLE);
//        }else{
//            mMsgUnRead.setVisibility(View.GONE);
//        }
    }

    // 控制天气背景动态效果
    public void sunshineOrRain(String weather){
        if(weather.contains("雨")){
            weather_view.setVisibility(View.VISIBLE);
            weather_view.setWeatherData(PrecipType.RAIN);
            mWeatherSceneView.setVisibility(View.GONE);
            mVCloudy.setVisibility(View.VISIBLE); // 阴天遮罩开启
        }else{
            weather_view.setVisibility(View.GONE);
            weather_view.setWeatherData(PrecipType.CLEAR);
            mWeatherSceneView.setVisibility(View.VISIBLE);
            mVCloudy.setVisibility(View.GONE); // 阴天遮罩关闭
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            GlobalApplication.mApp.updateLocation();
        } else {
            // 如果用户没有授权
            ToastUtils.show(this, "请在手机设置中添加应用权限");
        }
    }


    public void updateBottomTabTip(BottomTabTip bottomTabTip, boolean visiable){
        switch (bottomTabTip){
            case tab2:
                mVTip2.setVisibility(visiable?View.VISIBLE:View.INVISIBLE);
                break;
            case tab3:
                mVTip3.setVisibility(visiable?View.VISIBLE:View.INVISIBLE);
                break;
            case tab4:
                mVTip4.setVisibility(visiable?View.VISIBLE:View.INVISIBLE);
                break;
        }
    }

    private void checkUpdate() {
        ApiClient.getInstance().getBasicService(this).checkUpdate().enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                if(response != null && response.body() != null){
                    UpdateModel updateModel = response.body();
                    VersionCodeUpdate versionCodeUpdate = new VersionCodeUpdate(MainActivity.this);
                    int versionCode = versionCodeUpdate.getVersionCode();
                    if(updateModel.getVersioncode() > versionCode){
                        versionCodeUpdate.showDialogUpdate(updateModel);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {

            }
        });

    }
}
