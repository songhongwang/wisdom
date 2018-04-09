package com.cbt.main.model;

import java.util.List;

/**
 * Created by vigorous on 18/4/1.
 */

public class Weather7DaysForcast {

    private String todaytianqi;
    private String todaywendu;
    private String todayfeng;
    private String fabushijian;
    private List<WeatherModel2> d7list;
    public void setTodaytianqi(String todaytianqi) {
        this.todaytianqi = todaytianqi;
    }
    public String getTodaytianqi() {
        return todaytianqi;
    }

    public void setTodaywendu(String todaywendu) {
        this.todaywendu = todaywendu;
    }
    public String getTodaywendu() {
        return todaywendu;
    }

    public void setTodayfeng(String todayfeng) {
        this.todayfeng = todayfeng;
    }
    public String getTodayfeng() {
        return todayfeng;
    }

    public void setFabushijian(String fabushijian) {
        this.fabushijian = fabushijian;
    }
    public String getFabushijian() {
        return fabushijian;
    }

    public void setD7list(List<WeatherModel2> d7list) {
        this.d7list = d7list;
    }
    public List<WeatherModel2> getD7list() {
        return d7list;
    }

}
