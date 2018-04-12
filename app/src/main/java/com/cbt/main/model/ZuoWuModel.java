package com.cbt.main.model;

/**
 * Created by vigorous on 18/4/1.
 */

public class ZuoWuModel {
    public long id;
    public String leixin;
    public String pinzhong;
    public String strLeixin;
    public String strPinzhong;

    public ZuoWuModel(String leixin, String pinzhong) {
        id = System.currentTimeMillis();
        this.leixin = leixin;
        this.pinzhong = pinzhong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZuoWuModel that = (ZuoWuModel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
