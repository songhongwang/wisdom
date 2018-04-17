package com.cbt.main.model;

import java.io.Serializable;

public class ReplayMarketView implements Serializable {
	
	private String rid;

	private String content;

	private String time;

	private String replayuserid;

	private String replayusername;
	private String replyicon;
	private String commenicon;
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
	public String getReplayuserid() {
		return replayuserid;
	}
	public void setReplayuserid(String replayuserid) {
		this.replayuserid = replayuserid;
	}
	public String getReplayusername() {
		return replayusername;
	}
	public void setReplayusername(String replayusername) {
		this.replayusername = replayusername;
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
	public String getReplyicon() {
		return replyicon;
	}
	public void setReplyicon(String replyicon) {
		this.replyicon = replyicon;
	}
	public String getCommenicon() {
		return commenicon;
	}
	public void setCommenicon(String commenicon) {
		this.commenicon = commenicon;
	}
	
}