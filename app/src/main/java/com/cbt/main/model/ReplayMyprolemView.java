package com.cbt.main.model;


import java.io.Serializable;

public class ReplayMyprolemView implements Serializable {
	private String rid;

	private String content;

	private String time;
	private boolean isadopt;

	private String userid;

	private String username;
	private String uicon;

	private String icon1;
	private String icon2;
	private String icon3;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUicon() {
		return uicon;
	}

	public void setUicon(String uicon) {
		this.uicon = uicon;
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

	public boolean isIsadopt() {
		return isadopt;
	}

	public void setIsadopt(boolean isadopt) {
		this.isadopt = isadopt;
	}
}
