package com.cbt.main.model;

import java.io.Serializable;
import java.util.List;

public class ShichangModel implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column agricultural.iid
     *
     * @mbggenerated Thu Nov 10 19:24:02 CST 2016
     */
    private List<MarketinformationView> nongqinglist;

    private MessageCount mscount;

	public List<MarketinformationView> getNongqinglist() {
		return nongqinglist;
	}

	public void setNongqinglist(List<MarketinformationView> nongqinglist) {
		this.nongqinglist = nongqinglist;
	}

	public MessageCount getMscount() {
		return mscount;
	}

	public void setMscount(MessageCount mscount) {
		this.mscount = mscount;
	}
    
    
}