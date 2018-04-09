package com.cbt.main.model;

/**
 * Created by vigorous on 16/11/7.
 */

public class BaseModel<T> {
    private int code;
    private String message;
    private String userid;

    private T datas;

    public T getDatas() {
        return datas;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
