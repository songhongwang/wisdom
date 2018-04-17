package com.cbt.main.model;

import java.io.Serializable;
import java.util.List;

public class MarketinformationDetailView implements Serializable {

	private String iid;
	private String ititle;
	private String faburen;
	private String fabushijian;
	private String content;
	private String uid;
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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
	private List<ReplayMarketView> rlist;
	
private int isshoucang;
	
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

	public String getItitle() {
		return ititle;
	}

	public void setItitle(String ititle) {
		this.ititle = ititle;
	}

	public String getFaburen() {
		return faburen;
	}

	public void setFaburen(String faburen) {
		this.faburen = faburen;
	}

	public String getFabushijian() {
		return fabushijian;
	}

	public void setFabushijian(String fabushijian) {
		this.fabushijian = fabushijian;
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

	public List<ReplayMarketView> getRlist() {
		return rlist;
	}

	public void setRlist(List<ReplayMarketView> rlist) {
		this.rlist = rlist;
	}
}
