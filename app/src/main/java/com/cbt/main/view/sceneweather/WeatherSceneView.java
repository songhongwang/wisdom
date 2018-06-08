package com.cbt.main.view.sceneweather;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vigorous on 18/6/8.
 * 晴天 动态效果
 */

public class WeatherSceneView extends View {
    private SunShineView mSunShineView;
    private BirdUpView mBirdUpView;
    private BirdDownView mBirdDownView;
    private CloudLeftView mCloudLeftView;
    private CloudRightView mCloudRightView;


    public WeatherSceneView(Context context) {
        super(context);
        initUI(context);
    }

    public WeatherSceneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public WeatherSceneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }


    private void initUI(Context context) {
        mSunShineView = new SunShineView(context);
        mBirdUpView = new BirdUpView(context);
        mBirdDownView = new BirdDownView(context);
        mCloudLeftView = new CloudLeftView(context);
        mCloudRightView = new CloudRightView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSunShineView.draw(canvas);
        mBirdUpView.draw(canvas);
        mBirdDownView.draw(canvas);
        mCloudLeftView.draw(canvas);
        mCloudRightView.draw(canvas);
        postInvalidateDelayed(50);
    }
}
