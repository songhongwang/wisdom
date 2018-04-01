package com.cbt.main.utils.net;

import android.content.Context;

import com.cbt.main.model.User;
import com.cbt.main.utils.SharedPreferencUtil;

import okhttp3.Interceptor;
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

    public BasicService getBasicService(Context context) {
        // 通用参数
        CommonParamIntercept commonInterceptor = getCommonInterceptor(context);
        if(commonInterceptor != null){
            mOkHttpClient = HttpClientUtil.getCommonQueryParamsHttpClient(commonInterceptor);
        }

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

    public CommonParamIntercept getCommonInterceptor(Context context){
        User login = SharedPreferencUtil.getLogin(context);

        if(login == null){
            return null;
        }
        CommonParamIntercept commonParamIntercept = new CommonParamIntercept
                .Builder()
                .addQueryParam("userid",  login.getUid())
                .addQueryParam("username", login.getUname()).build();
        return commonParamIntercept;
    }
}
