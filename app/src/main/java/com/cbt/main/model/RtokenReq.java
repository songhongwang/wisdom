package com.cbt.main.model;

/**
 * Created by vigorous on 17/12/19.
 * 融云sdk token
 */

public class RtokenReq {
    private String userId;
    private String name;
    private String portraitUri;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }
}
