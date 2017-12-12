package com.cbt.main.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;


public class Utils {
    public static void fitsSystemWindows(boolean isTranslucentStatus, View view) {
        if (isTranslucentStatus) {
            view.getLayoutParams().height = calcStatusBarHeight(view.getContext());
        }
    }

    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static float getScreenWidth(FragmentActivity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        // 获取屏幕密度（方法1）
        return wm.getDefaultDisplay().getWidth(); // 屏幕宽度（像素，如：800p）
    }
}
