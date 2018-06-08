package com.cbt.main.view.sceneweather;

import android.content.Context;
import android.view.View;

/**
 * Created by vigorous on 18/6/8.
 */

public abstract class BaseView extends View {
    public Context mContext;
    public float width;
    public float height;

    public BaseView(Context context, float w, float h) {
        super(context);
        mContext = context;
        width = w;
        height = h;
        initUI();
    }

    public abstract void initUI();

}
