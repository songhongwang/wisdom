package com.cbt.main.utils.location;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cbt.main.app.GlobalApplication;

public class LocationProviderGD {
    private static final String TAG = "LocationProvider";

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    public String mTeamid = "";

    Context context;

    public LocationProviderGD(Context context) {
        super();
        this.context = context;
        initOption();
    }

    public LocationProviderGD() {

    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //解析定位结果
                try {
                    if (location != null) {
                        if (location.getErrorCode() == 0) {
                            GlobalApplication.mLocationData.errorcode = 0;
                            System.out.println("LocationToString " + location.toString());

                            if (GlobalApplication.mLocationData == null) {
                                GlobalApplication.mLocationData = new LocationData();
                            }

                            GlobalApplication.mLocationData.city = location.getCity();

                            GlobalApplication.mLocationData.addr = location.getProvince();
                            GlobalApplication.mLocationData.lat = location.getLatitude();
                            GlobalApplication.mLocationData.lon = location.getLongitude();

                        } else {
                            GlobalApplication.mLocationData.errorcode = location.getErrorCode();
                        }
                        if (mOnLocationListener != null) {
                            mOnLocationListener.onLocationChanged(location.getErrorCode());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (locationClient != null) {
                    Log.i(TAG, "mLocationClient.stop()");
                    locationClient.stopLocation();
                }

            } else {
            }
        }
    };

    // 根据控件的选择，重新设置定位参数
    private void initOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(false);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(false);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(false);
        //设置是否使用传感器
        locationOption.setSensorEnable(false);
        //设置是否开启wifi扫描，如果设置为false时同时会停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationOption.setWifiScan(true);
        try {
            // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
            locationOption.setInterval(60 * 1000);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            // 设置网络请求超时时间
            locationOption.setHttpTimeOut(30 * 1000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //初始化client
        locationClient = new AMapLocationClient(context.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    private OnLocationListener mOnLocationListener;

    public void setLocationListener(OnLocationListener locationListener) {
        mOnLocationListener = locationListener;
    }

    public void clearLocationListener() {
        if (mOnLocationListener != null) {
            mOnLocationListener = null;
        }
    }

    public interface OnLocationListener {
        void onLocationChanged(int code);
    }

}