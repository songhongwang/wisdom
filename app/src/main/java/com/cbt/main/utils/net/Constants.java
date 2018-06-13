package com.cbt.main.utils.net;

/**
 * Created by vigorous on 17/12/19.
 */

public class Constants {
    private static String sBaseUrl = "http://114.215.25.185:2522/hnzhnq/\";";
//    private static String sBaseUrl = "http://192.168.31.152:9999/hnzhnq/
    private static String sRongBaseUrl = "http://api.cn.ronghub.com";

    public static String getBaseUrl() {
        return sBaseUrl;
    }
    public static String getRongBaseUrl() {
        return sRongBaseUrl;
    }
}
