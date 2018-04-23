    package com.cbt.main.model;

import java.io.Serializable;
import java.util.List;

    /**   
 * @Title: ClientFarm.java   
 * @Package com.sino.alarm.modelview   
 * @Description: TODO   
 * @author Administrator   
 * @date 2016年11月12日 下午11:25:31   
 * @version V1.0.0   
 */
public class ClientFarm {

	private String fid;
	private String fname;
	private String area;
	private String uid;
	private String level1;
	private String mianji;
	private String poinw;
	private String poinj;
	private List<Usercrop> usercrop;
	
	public List<Usercrop> getUsercrop() {
		return usercrop;
	}


	public void setUsercrop(List<Usercrop> usercrop) {
		this.usercrop = usercrop;
	}


	public String getFid() {
		return fid;
	}
	
	
	public String getLevel1() {
		return level1;
	}


	public void setLevel1(String level1) {
		this.level1 = level1;
	}


	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getMianji() {
		return mianji;
	}
	public void setMianji(String mianji) {
		this.mianji = mianji;
	}
	public String getPoinw() {
		return poinw;
	}
	public void setPoinw(String poinw) {
		this.poinw = poinw;
	}
	public String getPoinj() {
		return poinj;
	}
	public void setPoinj(String poinj) {
		this.poinj = poinj;
	}


	
	
	
	
	  
}
  