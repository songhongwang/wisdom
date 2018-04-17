package com.cbt.main.model;

import com.cbt.main.utils.net.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/4/2.
 * 朋友圈model
 */

public class IndexFeedModel {

    private String area;
    private String content;
    private String icon1;
    private String icon2;
    private String icon3;
    private String iid;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;
    private String img7;
    private String img8;
    private String img9;
    private List<String> imglist;
    private List<ReplyModel> interactivelist;
    private String time;
    private String usericon;
    private String userid;
    private String username;
    public void setArea(String area) {
        this.area = area;
    }
    public String getArea() {
        return area;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }
    public String getIcon1() {
        return icon1;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }
    public String getIcon2() {
        return icon2;
    }

    public void setIcon3(String icon3) {
        this.icon3 = icon3;
    }
    public String getIcon3() {
        return icon3;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
    public String getIid() {
        return iid;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }
    public String getImg1() {
        return img1;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }
    public String getImg2() {
        return img2;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }
    public String getImg3() {
        return img3;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }
    public String getImg4() {
        return img4;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }
    public String getImg5() {
        return img5;
    }

    public void setImg6(String img6) {
        this.img6 = img6;
    }
    public String getImg6() {
        return img6;
    }

    public void setImg7(String img7) {
        this.img7 = img7;
    }
    public String getImg7() {
        return img7;
    }

    public void setImg8(String img8) {
        this.img8 = img8;
    }
    public String getImg8() {
        return img8;
    }

    public void setImg9(String img9) {
        this.img9 = img9;
    }
    public String getImg9() {
        return img9;
    }

    public List<ReplyModel> getInteractivelist() {
        return interactivelist;
    }

    public void setInteractivelist(List<ReplyModel> interactivelist) {
        this.interactivelist = interactivelist;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }
    public String getUsericon() {
        return usericon;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUserid() {
        return userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }

    public static Data convert(IndexFeedModel self){
        Data data = new Data();
        data.setIid(self.getIid());
        data.setUid(self.getUserid());
        data.setAvatar(self.getUsericon());
        data.setContent(self.getContent());
        data.setCreateTime(self.getTime());
        data.setNickname(self.getUsername());
        data.setPictureList(self.getImglist());
        data.setPictureThumbList(self.getImglist());

        data.setReplyList(self.getInteractivelist());
        return data;
    }
}
