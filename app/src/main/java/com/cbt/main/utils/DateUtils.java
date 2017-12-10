package com.cbt.main.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static String DEFAULT_DATE_FORMAT1 = "MM月dd日";
    public static String DEFAULT_SIMPLE_TIME_FORMAT = "HH:mm";
    // public final static String START_DATE1 = "2013-12-26 00:00:00";
    // public final static String END_DATE1 = "2013-12-27 23:59:59";
    public final static String START_DATE1 = "2013-12-31 00:00:00";
    public final static String END_DATE1 = "2014-01-01 23:59:59";

    public final static String START_DATE2 = "2014-01-20 00:00:00";
    public final static String END_DATE2 = "2014-02-08 23:59:59";
    public final static String DEFAULT_DATE_STARTID = "2016-01-01 00:00:00";

    private static SimpleDateFormat mSimpleDateFormat;
    public static String getDatetime() {
        return getDatetime(new Date());
    }

    public static String getDatetime(long ms) {
        if (ms > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            return sdf.format(new Date(ms));
        } else {
            return "";
        }
    }

    public static String getDatetime1(long ms) {
        if (ms > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT1);
            return sdf.format(new Date(ms * 1000L));
        } else {
            return "";
        }
    }

    public static String getSecondsDatetime(long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        return sdf.format(new Date(ms * 1000L));
    }

    public static String getDatetime(Date d) {
        return getDatetime(d.getTime());
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(new Date());
    }

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(date);
    }

    public static int getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return Integer.parseInt(sdf.format(new Date()));
    }

    public static String getCurrentDay() {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static boolean testIsToday(long time) {
        return android.text.format.DateUtils.isToday(time);
    }

    public static String getSimpleTime(String datetime) {
        if (TextUtils.isEmpty(datetime)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SIMPLE_TIME_FORMAT);
        return sdf.format(parseDatetimeToDate(datetime));
    }

    public static Date parseDate(String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long parseDatetimeToTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            Date d = sdf.parse(datetime);
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date parseDatetimeToDate(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            return sdf.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date add(Date date, int type, int time) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(type, time);
            return new Date(cal.getTime().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int calcDiffSecond(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (int) ((d1.getTime() - d2.getTime()) / 1000);
    }

    public static boolean showCardFlag() {
        long startDate1 = parseDatetimeToTime(START_DATE1);
        long endDate1 = parseDatetimeToTime(END_DATE1);
        long startDate2 = parseDatetimeToTime(START_DATE2);
        long endDate2 = parseDatetimeToTime(END_DATE2);

        long current = System.currentTimeMillis();
        return current > startDate1 && current < endDate1 || current > startDate2 && current < endDate2;

    }

    public static int diffDate(Date date1, Date date2) throws ParseException {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long ca = date1.getTime() - date2.getTime();
        if (ca <= 0) {
            return 0;
        }

        return (int) (ca / 1000 / 60 / 60 / 24);
    }

    public static int diffHour(Date date1, Date date2) throws ParseException {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long ca = date1.getTime() - date2.getTime();
        if (ca <= 0) {
            return 0;
        }

        return (int) (ca / 1000 / 60 / 60);
    }

    public static int diffMinute(Date date1, Date date2) throws ParseException {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long ca = date1.getTime() - date2.getTime();
        if (ca <= 0) {
            return 0;
        }

        return (int) (ca / 1000 / 60);
    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return "请在24小时内完成此用户的搭配，还剩余" + hours + "小时 " + minutes + "分钟。";
    }

    public static String formatDuring(String date) {
        Date end = parseDatetimeToDate(date);
        return formatDuring(end.getTime() - new Date().getTime());
    }

    public static Date parseDatetime(String d) {
        try {
            if (TextUtils.isEmpty(d)) {
                return new Date();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            return sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SimpleDateFormat getSimpleDateFormat() {
        if (mSimpleDateFormat == null) {
            mSimpleDateFormat = new SimpleDateFormat();
        }
        return mSimpleDateFormat;
    }


    public static SimpleDateFormat getMMdd() {
        getSimpleDateFormat().applyPattern(DEFAULT_DATE_FORMAT1);
        return mSimpleDateFormat;
    }

    public static long getIntTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        Date sDate = null;
        try {
            date = format.parse(time);
            sDate = format.parse("00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getTime()-sDate.getTime())/1000;
    }
}
