package com.cbt.main.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by vigorous on 17/12/10.
 */

public class GlobalApplication extends Application {

    public static Context mApp;
    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;

    }
}
