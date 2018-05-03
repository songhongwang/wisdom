package com.cbt.main.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cbt.main.R;
import com.cbt.main.moments.ImageWatcher;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vigorous on 17/12/18.
 * 区别BaseActivity在于没有设置状态栏
 */

public abstract class BaseActivity2 extends FragmentActivity implements MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
    @Nullable@BindView(R.id.tv_title)
    TextView mTvTitle;
    @Nullable@BindView(R.id.iv_back)
    ImageView mIvBack;
    @Nullable@BindView(R.id.iv_complete)
    ImageView mIvFinish;

    private ImageWatcher vImageWatcher;
    private Activity mActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏高度
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = this.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
        onCCreate(savedInstanceState);
        ButterKnife.bind(this);
        mActivity = this;
        if(mIvBack != null){
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.finish();
                }
            });
        }
        initImageViewer();
        initUI();
    }
    private void initImageViewer(){
        vImageWatcher = ImageWatcher.Helper.with(this) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(false ? Utils.calcStatusBarHeight(this) : 0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
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

    public abstract void onCCreate(@Nullable Bundle savedInstanceState);

    public abstract void initUI();

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);

    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {

    }
}
