package com.cbt.main.model;


import java.io.Serializable;

public class MarketinformationView implements Serializable {

	 private String iid;
	 private String ititle;
	 private String provincename;
	 private String cityname;
	 private String countryname;
	 private String uname;
	 private String userid;
	 private String time;
	 private String state;
	 private int messagecount;
	 private String ctname;
	 private String cname;
	 private String gname;
	 private String nongzitype;
	 private String gongyingtype;
	 
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public String getProvincename() {
		return provincename;
	}
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getCountryname() {
		return countryname;
	}
	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getMessagecount() {
		return messagecount;
	}
	public void setMessagecount(int messagecount) {
		this.messagecount = messagecount;
	}
	public String getCtname() {
		return ctname;
	}
	public void setCtname(String ctname) {
		this.ctname = ctname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getNongzitype() {
		return nongzitype;
	}
	public void setNongzitype(String nongzitype) {
		this.nongzitype = nongzitype;
	}
	public String getGongyingtype() {
		return gongyingtype;
	}
	public void setGongyingtype(String gongyingtype) {
		this.gongyingtype = gongyingtype;
	}

	public static Data convert(MarketinformationView self){
		Data data = new Data();
		data.setCreateTime(self.getTime());
		data.setNickname(self.getNongzitype());
		data.setShenfen(self.getItitle());
		data.setIid(self.getIid());
		data.setUid(self.getUserid());
		data.setRplaycount(self.getMessagecount()+"");
		data.setContent(self.getGongyingtype());
		return data;
	}
	 
}
