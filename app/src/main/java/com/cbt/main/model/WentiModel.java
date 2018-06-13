package com.cbt.main.model;

import java.io.Serializable;
import java.util.List;

public class WentiModel implements Serializable {

	private String iid;
//	用户头像
	private String usericon;
//	用户名称
	private String username;
	
	private String userid;
//	用户图标
	private String icon1;
   
	private String icon2;
   
	private String icon3;
//	发布内容
	private String content;
   
	private String img1;
   
	private String img2;
   
	private String img3;
   
	private String img4;
   
	private String img5;
   
	private String img6;
   
	private String img7;
   
	private String img8;
   
	private String img9;
	
	private String jiaobiao;
	
	private int messagecount;

	private int state;
	
	private int isshoucang;

	private List<String> imglist;

	public List<String> getImglist() {
		return imglist;
	}

	public void setImglist(List<String> imglist) {
		this.imglist = imglist;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getIsshoucang() {
		return isshoucang;
	}

	public void setIsshoucang(int isshoucang) {
		this.isshoucang = isshoucang;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getUsericon() {
		return usericon;
	}

	public void setUsericon(String usericon) {
		this.usericon = usericon;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIcon1() {
		return icon1;
	}

	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}

	public String getIcon2() {
		return icon2;
	}

	public void setIcon2(String icon2) {
		this.icon2 = icon2;
	}

	public String getIcon3() {
		return icon3;
	}

	public void setIcon3(String icon3) {
		this.icon3 = icon3;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg4() {
		return img4;
	}

	public void setImg4(String img4) {
		this.img4 = img4;
	}

	public String getImg5() {
		return img5;
	}

	public void setImg5(String img5) {
		this.img5 = img5;
	}

	public String getImg6() {
		return img6;
	}

	public void setImg6(String img6) {
		this.img6 = img6;
	}

	public String getImg7() {
		return img7;
	}

	public void setImg7(String img7) {
		this.img7 = img7;
	}

	public String getImg8() {
		return img8;
	}

	public void setImg8(String img8) {
		this.img8 = img8;
	}

	public String getImg9() {
		return img9;
	}

	public void setImg9(String img9) {
		this.img9 = img9;
	}

	public String getJiaobiao() {
		return jiaobiao;
	}

	public void setJiaobiao(String jiaobiao) {
		this.jiaobiao = jiaobiao;
	}

	public int getMessagecount() {
		return messagecount;
	}

	public void setMessagecount(int messagecount) {
		this.messagecount = messagecount;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static Data convert(WentiModel self){
		Data data = new Data();
		data.setAvatar(self.getUsericon());
		data.setContent(self.getContent());
		data.setCreateTime(self.getJiaobiao());
		data.setNickname(self.getUsername());
		data.setPictureList(self.getImglist());
		data.setPictureThumbList(self.getImglist());
		data.setIid(self.getIid());
		data.setUid(self.getUserid());
		data.setShenfen(self.getState() == 2 ? "普通农民" : "");
		data.setRplaycount(self.getMessagecount()+"");
		return data;
	}
}
