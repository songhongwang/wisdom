package com.cbt.main.utils.net;

import com.cbt.main.app.Constants;
import com.cbt.main.utils.SHA1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vigorous on 16/11/7.
 * 添加通用请求参数拦截器
 */

public class RongYunTokenUtil  {
    public static final String URL_PARAM_KEY = "App-Key";
    public static final String URL_PARAM_NONCE = "Nonce";
    public static final String URL_PARAM_TIME = "Timestamp";
    public static final String URL_PARAM_SIGN = "Signature";

    private static String getKey(){
        return Constants.KEY_RONG_YUN;
//        return "uwd1c0sxdlx2";
    }

    private static String getNonce(){
        int max=10000;
        int min=99999;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        return s+ "";
//        return "14314";
    }

    private static String getTime(){
        return System.currentTimeMillis() + "";
//        return "1408706337";
    }

    private static String getSignFromList() {
        String appSecret = Constants.SECRET_RONG_YUN;
        String nonce = getNonce();
        String timestamp = getTime();

        return SHA1.encode(appSecret + nonce + timestamp);
    }


    public static Map<String, String> getHeaderMap(){
        Map<String, String> headers = new HashMap<>();
        headers.put(URL_PARAM_KEY, getKey());
        headers.put(URL_PARAM_NONCE, getNonce());
        headers.put(URL_PARAM_TIME, getTime());
        headers.put(URL_PARAM_SIGN, getSignFromList());
        return headers;
    }
}
