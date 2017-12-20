package com.cbt.main.model;

/**
 * Created by vigorous on 16/11/7.
 */

public class BaseModel<T> {
    private int code;
    private String msg;

    private T datas;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getDatas() {
        return datas;
    }

}
