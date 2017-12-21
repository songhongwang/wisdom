package com.cbt.main.utils.net;

import com.cbt.main.app.Constants;
import com.cbt.main.utils.SHA1;

import java.util.HashMap;
import java.util.Map;

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
    }

    // 随机数
    private static String getNonce(){
        int code = (int)(Math.random() * (400000000 -100000000)) + 100000000;
        String codestr = String.valueOf(code);
        return codestr;
    }

    // 时间戳 秒
    private static String getTime(){
        return (System.currentTimeMillis() / 1000) + "";
    }

    private static String getSign(String nonce, String timestamp) {
        String appSecret = Constants.SECRET_RONG_YUN;
        return SHA1.encode(appSecret + nonce + timestamp);
    }


    public static Map<String, String> getHeaderMap(){
        String nonce = getNonce();
        String time = getTime();
        String sign = getSign(nonce, time);

        Map<String, String> headers = new HashMap<>();
        headers.put(URL_PARAM_KEY, getKey());
        headers.put(URL_PARAM_NONCE, nonce);
        headers.put(URL_PARAM_TIME, time);
        headers.put(URL_PARAM_SIGN, sign);
        return headers;
    }
}
