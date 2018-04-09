package com.cbt.main.utils.net;


import com.cbt.main.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by vigorous on 16/11/4.
 */

public class HttpClientUtil {
    private static final int READ_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 10;
    private static OkHttpClient mOKHttpClient;
    private static OkHttpClient mDefaultOKHttpClient;

    private HttpClientUtil() {
    }

    public static OkHttpClient getCommonQueryParamsHttpClient(Interceptor commonQueryInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        mOKHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .addInterceptor(commonQueryInterceptor)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();
        return mOKHttpClient;
    }

    public static OkHttpClient getDefaultHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mDefaultOKHttpClient == null) {
            mDefaultOKHttpClient = new OkHttpClient
                    .Builder()
                    .addInterceptor(logging)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }
        return mDefaultOKHttpClient;
    }
}
