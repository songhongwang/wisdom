package com.cbt.main.model;

/**
 * Created by vigorous on 17/12/19.
 * 融云sdk token
 */

public class RtokenRsp {
    private int code;
    private String token;
    private String userId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
