package com.cbt.main.app;

import android.app.Application;
import android.content.Context;

import com.cbt.main.utils.location.LocationData;
import com.cbt.main.utils.location.LocationProviderGD;

import io.rong.imkit.RongIM;

/**
 * Created by vigorous on 17/12/10.
 */

public class GlobalApplication extends Application {

    public static GlobalApplication mApp;


    public static LocationProviderGD mLocationProviderGD;
    public static LocationData mLocationData = new LocationData();

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;

        RongIM.init(this);

        //初始化地理位置信息
        startLocation();
    }

    public void updateLocation() {
        if (mLocationProviderGD != null) {
            mLocationProviderGD.startLocation();
        } else {
            mLocationData = new LocationData();
            mLocationProviderGD = new LocationProviderGD(getApplicationContext());
            mLocationProviderGD.startLocation();
        }
    }

    public void startLocation() {
        if (mLocationProviderGD != null) {
            mLocationProviderGD.startLocation();
        } else {
            mLocationData = new LocationData();
            mLocationProviderGD = new LocationProviderGD(getApplicationContext());
            mLocationProviderGD.startLocation();
        }
    }
}
