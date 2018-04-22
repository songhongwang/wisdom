package com.cbt.main.model;

import com.cbt.main.fragment.WeatherLineFragment;

import java.util.Calendar;

/**
 * Created by vigorous on 18/4/1.
 */

public class WeatherModel2 {

    private String ybzuigaowendu;
    private String ybzuidiwendu;
    private String ybshidu;
    private String ybfengsu;
    private String ybfengxiang;
    private String ybjiangshui;
    private String ybtianqi;
    private String ybdate;
    private String ybday;
    private String ybiconindex;

    public void setYbzuigaowendu(String ybzuigaowendu) {
        this.ybzuigaowendu = ybzuigaowendu;
    }
    public String getYbzuigaowendu() {
        return ybzuigaowendu;
    }

    public void setYbzuidiwendu(String ybzuidiwendu) {
        this.ybzuidiwendu = ybzuidiwendu;
    }
    public String getYbzuidiwendu() {
        return ybzuidiwendu;
    }

    public void setYbshidu(String ybshidu) {
        this.ybshidu = ybshidu;
    }
    public String getYbshidu() {
        return ybshidu;
    }

    public void setYbfengsu(String ybfengsu) {
        this.ybfengsu = ybfengsu;
    }
    public String getYbfengsu() {
        return ybfengsu;
    }

    public void setYbfengxiang(String ybfengxiang) {
        this.ybfengxiang = ybfengxiang;
    }
    public String getYbfengxiang() {
        return ybfengxiang;
    }

    public void setYbjiangshui(String ybjiangshui) {
        this.ybjiangshui = ybjiangshui;
    }
    public String getYbjiangshui() {
        return ybjiangshui;
    }

    public void setYbtianqi(String ybtianqi) {
        this.ybtianqi = ybtianqi;
    }
    public String getYbtianqi() {
        return ybtianqi;
    }

    public void setYbdate(String ybdate) {
        this.ybdate = ybdate;
    }
    public String getYbdate() {
        return ybdate;
    }

    public void setYbday(String ybday) {
        this.ybday = ybday;
    }
    public String getYbday() {
        return ybday;
    }

    public void setYbiconindex(String ybiconindex) {
        this.ybiconindex = ybiconindex;
    }
    public String getYbiconindex() {
        return ybiconindex;
    }


    public static WeatherModel convert(WeatherModel2 self, int index){
        Calendar calendar = Calendar.getInstance();
        int dayOfMouth = calendar.get(Calendar.DAY_OF_MONTH) -1; // -1 代表昨天

        WeatherModel good = new WeatherModel();
        good.setDate((dayOfMouth + index) + "日" + self.getYbday());


        int heigth = 0;
        int low = 0;
        try{
            if(self.getYbzuidiwendu().contains("℃")){
                low = Integer.parseInt(self.getYbzuidiwendu().substring(0, self.getYbzuidiwendu().indexOf("℃")));
            }

            if(self.getYbzuigaowendu().contains("℃")){
                heigth = Integer.parseInt(self.getYbzuigaowendu().substring(0, self.getYbzuigaowendu().indexOf("℃")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        good.setHigh("高温 " + Math.max(heigth, low) + "℃");
        good.setLow("低温 " + Math.min(heigth, low) + "℃");
        good.setTypeDay(self.getYbtianqi());
        good.setTypeNight(self.getYbtianqi());
        good.setWindDirectionDay(self.getYbfengxiang());
        good.setWindPowerDay(self.getYbfengsu());
        good.setWindDirectionNight(self.getYbfengxiang());
        good.setWindPowerNight(self.getYbfengsu());

        return good;
    }
}
