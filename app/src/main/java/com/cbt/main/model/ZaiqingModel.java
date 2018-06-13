package com.cbt.main.model;

import java.io.Serializable;
import java.util.List;

public class ZaiqingModel implements Serializable {

	private String iid;
//	用户头像
	private String usericon;
//	用户名称
	private String username;
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
	
	   private List<String> imglist;
	   
	   public List<String> getImglist() {
		return imglist;
	}

	public void setImglist(List<String> imglist) {
		this.imglist = imglist;
	}
//	发布时间
	private String time;
//	地区
	private String area;
	
//   作物类型名称
	private String disone;

//   作物名称
	private String distwo;
	
	private String userid;
	
public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	//  生长期名称 
	private String disthree;
	private int iszan;

	public int getIszan() {
		return iszan;
	}

	public void setIszan(int iszan) {
		this.iszan = iszan;
	}
private int isshoucang;
	
	public int getIsshoucang() {
		return isshoucang;
	}

	public void setIsshoucang(int isshoucang) {
		this.isshoucang = isshoucang;
	}
	
	private List<ReplyModel> replayList;
	
	public List<ReplyModel> getReplayList() {
		return replayList;
	}
	public void setReplayList(List<ReplyModel> replayList) {
		this.replayList = replayList;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDisone() {
		return disone;
	}
	public void setDisone(String disone) {
		this.disone = disone;
	}
	public String getDistwo() {
		return distwo;
	}
	public void setDistwo(String distwo) {
		this.distwo = distwo;
	}
	public String getDisthree() {
		return disthree;
	}
	public void setDisthree(String disthree) {
		this.disthree = disthree;
	}

	public static Data convert(ZaiqingModel self){
		Data data = new Data();
		data.setAvatar(self.getUsericon());
		data.setContent(self.getContent());
		data.setCreateTime(self.getTime());
		data.setNickname(self.getUsername());
		data.setPictureList(self.getImglist());
		data.setPictureThumbList(self.getImglist());
		data.setIid(self.getIid());
		data.setUid(self.getUserid());
		data.setReplyList(self.getReplayList());
		data.setIsccang(self.getIsshoucang()+"");
		data.setIszan(self.getIszan()+"");
		return data;
	}
}
