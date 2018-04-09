package com.cbt.main.model;

/**
 * Created by vigorous on 18/4/1.
 */

public class IndexProductModel {
    private String pid;
    private String pdfpath;
    private String productname;
    private long producttime;
    private String isreadall;
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getPid() {
        return pid;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }
    public String getPdfpath() {
        return pdfpath;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
    public String getProductname() {
        return productname;
    }

    public long getProducttime() {
        return producttime;
    }

    public void setProducttime(long producttime) {
        this.producttime = producttime;
    }

    public void setIsreadall(String isreadall) {
        this.isreadall = isreadall;
    }
    public String getIsreadall() {
        return isreadall;
    }

}
