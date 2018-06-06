package com.cbt.main.model;

/**
 * Created by vigorous on 18/6/6.
 */

public class UpdateModel {
    private String id;
    private String url;
    private String versionname;
    private String versioncode;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getVersioncode() {
        try{
            return Integer.parseInt(versioncode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
