package com.cbt.main.model;

/**
 * Created by vigorous on 18/6/11.
 * 消息数量附近到接口中
 */

public class BaseMsgModel<T> {
    private MsgCountModel mscount;
    T nongqinglist;

    public MsgCountModel getMscount() {
        return mscount;
    }

    public void setMscount(MsgCountModel mscount) {
        this.mscount = mscount;
    }

    public T getNongqinglist() {
        return nongqinglist;
    }

    public void setNongqinglist(T nongqinglist) {
        this.nongqinglist = nongqinglist;
    }
}
