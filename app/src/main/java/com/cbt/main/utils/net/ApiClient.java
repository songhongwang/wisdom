package com.cbt.main.utils.net;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vigorous on 16/11/4.
 * 数据层依赖工程 暴露接口类
 */

public class ApiClient {
    private static BasicService mBasicService;
    private static RongYunService mRongYunService;
    private OkHttpClient mOkHttpClient;

    private ApiClient() {
        mOkHttpClient = HttpClientUtil.getDefaultHttpClient();
    }

    // static inner class implements singleInstance
    private static class Holder {
        private static final ApiClient mApiClient = new ApiClient();
    }

    private static <T> T init(Class<T> targetClass, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(Holder.mApiClient.mOkHttpClient)
                .build();
        return retrofit.create(targetClass);
    }

    public static ApiClient getInstance() {
        return Holder.mApiClient;
    }

    public BasicService getBasicService() {
        if(mBasicService == null){
            mBasicService = init(BasicService.class, Constants.getBaseUrl());
        }

        return mBasicService;
    }
    public RongYunService getRongYunService() {
        if(mRongYunService == null){
            mRongYunService = init(RongYunService.class, Constants.getRongBaseUrl());
        }

        return mRongYunService;
    }
}
