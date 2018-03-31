package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.WeatherDaysForecast;
import com.cbt.main.utils.MyUtil;
import com.cbt.main.view.LineChartViewDouble;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Calendar;
import java.util.List;

/**
 * Created by vigorous on 18/3/31.
 * 天气折线图表
 */

public class WeatherLineFragment extends BaseFragment {

    // 多天预报标题1-6
    private TextView mDaysForecastTvWeek1, mDaysForecastTvWeek2, mDaysForecastTvWeek3,
            mDaysForecastTvWeek4, mDaysForecastTvWeek5, mDaysForecastTvWeek6;

    // 多天预报日期1-6
    private TextView mDaysForecastTvDay1, mDaysForecastTvDay2, mDaysForecastTvDay3,
            mDaysForecastTvDay4, mDaysForecastTvDay5, mDaysForecastTvDay6;

    // 多天预报白天天气类型图片1-6
    private ImageView mDaysForecastWeaTypeDayIv1, mDaysForecastWeaTypeDayIv2, mDaysForecastWeaTypeDayIv3,
            mDaysForecastWeaTypeDayIv4, mDaysForecastWeaTypeDayIv5, mDaysForecastWeaTypeDayIv6;

    // 多天预报白天天气类型文字1-6
    private TextView mDaysForecastWeaTypeDayTv1, mDaysForecastWeaTypeDayTv2, mDaysForecastWeaTypeDayTv3,
            mDaysForecastWeaTypeDayTv4, mDaysForecastWeaTypeDayTv5, mDaysForecastWeaTypeDayTv6;

    // 温度曲线
    private LineChartViewDouble mCharView;

    //  多天预报夜间天气类型图片1-6
    private ImageView mDaysForecastWeaTypeNightIv1, mDaysForecastWeaTypeNightIv2, mDaysForecastWeaTypeNightIv3,
            mDaysForecastWeaTypeNightIv4, mDaysForecastWeaTypeNightIv5, mDaysForecastWeaTypeNightIv6;

    // 多天预报夜间天气类型文字1-6
    private TextView mDaysForecastWeaTypeNightTv1, mDaysForecastWeaTypeNightTv2, mDaysForecastWeaTypeNightTv3,
            mDaysForecastWeaTypeNightTv4, mDaysForecastWeaTypeNightTv5, mDaysForecastWeaTypeNightTv6;
    //  多天预报风向1-6
    private TextView mDaysForecastWindDirectionTv1, mDaysForecastWindDirectionTv2, mDaysForecastWindDirectionTv3,
            mDaysForecastWindDirectionTv4, mDaysForecastWindDirectionTv5, mDaysForecastWindDirectionTv6;
    // 多天预报风力1 - 6
    private TextView mDaysForecastWindPowerTv1, mDaysForecastWindPowerTv2, mDaysForecastWindPowerTv3,
            mDaysForecastWindPowerTv4, mDaysForecastWindPowerTv5, mDaysForecastWindPowerTv6;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_weather_line, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        initView();
        Calendar calendar = Calendar.getInstance();
        // 现在小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);


        String jsonData = getJsonData();

        Gson gson = new Gson();
        List<WeatherDaysForecast> weatherDaysForecasts = gson.fromJson(jsonData, new TypeToken<List<WeatherDaysForecast>>() {
        }.getType());


        setDaysForecast(weatherDaysForecasts.get(0),weatherDaysForecasts.get(1),weatherDaysForecasts.get(2),weatherDaysForecasts.get(3),weatherDaysForecasts.get(4),weatherDaysForecasts.get(5),weatherDaysForecasts.get(0),11,11,calendar);

    }

    private void initView() {

        mDaysForecastTvWeek1 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_week1);
        mDaysForecastTvWeek2 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_week2);
        mDaysForecastTvWeek3 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_week3);
        mDaysForecastTvWeek4 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_week4);
        mDaysForecastTvWeek5 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_week5);
        mDaysForecastTvWeek6 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_week6);

        mDaysForecastTvDay1 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_day1);
        mDaysForecastTvDay2 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_day2);
        mDaysForecastTvDay3 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_day3);
        mDaysForecastTvDay4 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_day4);
        mDaysForecastTvDay5 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_day5);
        mDaysForecastTvDay6 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_day6);

        mDaysForecastWeaTypeDayIv1 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_iv1);
        mDaysForecastWeaTypeDayIv2 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_iv2);
        mDaysForecastWeaTypeDayIv3 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_iv3);
        mDaysForecastWeaTypeDayIv4 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_iv4);
        mDaysForecastWeaTypeDayIv5 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_iv5);
        mDaysForecastWeaTypeDayIv6 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_iv6);

        mDaysForecastWeaTypeDayTv1 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_tv1);
        mDaysForecastWeaTypeDayTv2 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_tv2);
        mDaysForecastWeaTypeDayTv3 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_tv3);
        mDaysForecastWeaTypeDayTv4 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_tv4);
        mDaysForecastWeaTypeDayTv5 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_tv5);
        mDaysForecastWeaTypeDayTv6 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_day_tv6);

        mCharView = (LineChartViewDouble) mRootView.findViewById(R.id.line_char);

        mDaysForecastWeaTypeNightIv1 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_iv1);
        mDaysForecastWeaTypeNightIv2 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_iv2);
        mDaysForecastWeaTypeNightIv3 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_iv3);
        mDaysForecastWeaTypeNightIv4 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_iv4);
        mDaysForecastWeaTypeNightIv5 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_iv5);
        mDaysForecastWeaTypeNightIv6 = (ImageView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_iv6);

        mDaysForecastWeaTypeNightTv1 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_tv1);
        mDaysForecastWeaTypeNightTv2 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_tv2);
        mDaysForecastWeaTypeNightTv3 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_tv3);
        mDaysForecastWeaTypeNightTv4 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_tv4);
        mDaysForecastWeaTypeNightTv5 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_tv5);
        mDaysForecastWeaTypeNightTv6 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_weather_night_tv6);

        mDaysForecastWindDirectionTv1 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_direction_tv1);
        mDaysForecastWindDirectionTv2 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_direction_tv2);
        mDaysForecastWindDirectionTv3 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_direction_tv3);
        mDaysForecastWindDirectionTv4 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_direction_tv4);
        mDaysForecastWindDirectionTv5 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_direction_tv5);
        mDaysForecastWindDirectionTv6 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_direction_tv6);

        mDaysForecastWindPowerTv1 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_power_tv1);
        mDaysForecastWindPowerTv2 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_power_tv2);
        mDaysForecastWindPowerTv3 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_power_tv3);
        mDaysForecastWindPowerTv4 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_power_tv4);
        mDaysForecastWindPowerTv5 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_power_tv5);
        mDaysForecastWindPowerTv6 = (TextView) mRootView.findViewById(R.id.wea_days_forecast_wind_power_tv6);
    }

    @Override
    protected void lazyLoad() {

    }

    private String getJsonData(){
        String json = "[{\"mDate\":\"30日星期五\",\"mHigh\":\"高温 17℃\",\"mLow\":\"低温 8℃\",\"mTypeDay\":\"多云\",\"mTypeNight\":\"多云\",\"mWindDirectionDay\":\"南风\",\"mWindDirectionNight\":\"南风\",\"mWindPowerDay\":\"\\u003c3级\",\"mWindPowerNight\":\"\\u003c3级\"},{\"mDate\":\"31日星期六\",\"mHigh\":\"高温 24℃\",\"mLow\":\"低温 9℃\",\"mTypeDay\":\"多云\",\"mTypeNight\":\"多云\",\"mWindDirectionDay\":\"南风\",\"mWindDirectionNight\":\"东北风\",\"mWindPowerDay\":\"\\u003c3级\",\"mWindPowerNight\":\"\\u003c3级\"},{\"mDate\":\"1日星期天\",\"mHigh\":\"高温 25℃\",\"mLow\":\"低温 11℃\",\"mTypeDay\":\"晴\",\"mTypeNight\":\"多云\",\"mWindDirectionDay\":\"南风\",\"mWindDirectionNight\":\"南风\",\"mWindPowerDay\":\"\\u003c3级\",\"mWindPowerNight\":\"\\u003c3级\"},{\"mDate\":\"2日星期一\",\"mHigh\":\"高温 25℃\",\"mLow\":\"低温 11℃\",\"mTypeDay\":\"多云\",\"mTypeNight\":\"多云\",\"mWindDirectionDay\":\"北风\",\"mWindDirectionNight\":\"东北风\",\"mWindPowerDay\":\"\\u003c3级\",\"mWindPowerNight\":\"3-4级\"},{\"mDate\":\"3日星期二\",\"mHigh\":\"高温 16℃\",\"mLow\":\"低温 6℃\",\"mTypeDay\":\"多云\",\"mTypeNight\":\"阴\",\"mWindDirectionDay\":\"东北风\",\"mWindDirectionNight\":\"东风\",\"mWindPowerDay\":\"3-4级\",\"mWindPowerNight\":\"\\u003c3级\"},{\"mDate\":\"4日星期三\",\"mHigh\":\"高温 13℃\",\"mLow\":\"低温 4℃\",\"mTypeDay\":\"阴\",\"mTypeNight\":\"小雨\",\"mWindDirectionDay\":\"东风\",\"mWindDirectionNight\":\"南风\",\"mWindPowerDay\":\"\\u003c3级\",\"mWindPowerNight\":\"\\u003c3级\"}]";
        return json;
    }

    /**
     * 设置多天天气预报
     */
    private void setDaysForecast(WeatherDaysForecast weather, WeatherDaysForecast weather1,
                                 WeatherDaysForecast weather2, WeatherDaysForecast weather3,
                                 WeatherDaysForecast weather4, WeatherDaysForecast weather5,
                                 WeatherDaysForecast weather6, int hour1, int minute1,
                                 Calendar calendar) {
        // 日期和星期标题 【索引0：日期;索引1：星期】
        String[] day1;
        String[] day2;
        String[] day3;
        String[] day4;
        String[] day5;
        String[] day6;
        if ((hour1 == 23 && minute1 >= 45) || (hour1 < 5) || ((hour1 == 5) && (minute1 < 20))) {
            if (weather != null) {
                day1 = getDay(weather.getDate());
            } else {
                day1 = null;
            }

            assert weather1 != null;
            day2 = getDay(weather1.getDate());
            day3 = getDay(weather2.getDate());
            day4 = getDay(weather3.getDate());
            day5 = getDay(weather4.getDate());
            day6 = getDay(weather5.getDate());
        } else {
            if (weather1 != null) {
                day1 = getDay(weather1.getDate());
            } else {
                day1 = null;
            }

            day2 = getDay(weather2.getDate());
            day3 = getDay(weather3.getDate());
            day4 = getDay(weather4.getDate());
            day5 = getDay(weather5.getDate());
            day6 = getDay(weather6.getDate());
        }

        // 设置标题星期
        mDaysForecastTvWeek1.setText(getString(R.string.yesterday));
        mDaysForecastTvWeek2.setText(getString(R.string.today));
        mDaysForecastTvWeek3.setText(getWeek(day3[1]));
        mDaysForecastTvWeek4.setText(getWeek(day4[1]));
        mDaysForecastTvWeek5.setText(getWeek(day5[1]));
        mDaysForecastTvWeek6.setText(getWeek(day6[1]));

        // 月份
        calendar.add(Calendar.DATE, -1);
        String month1 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.DATE, 1);
        String month2 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.DATE, 1);
        String month3 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.DATE, 1);
        String month4 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.DATE, 1);
        String month5 = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        calendar.add(Calendar.DATE, 1);
        String month6 = String.valueOf(calendar.get(Calendar.MONTH) + 1);

        // 日
        String day01;
        if (day1 != null) {
            day01 = day1[0].split("日")[0];
        } else {
            day01 = null;
        }

        String day02 = day2[0].split("日")[0];
        String day03 = day3[0].split("日")[0];
        String day04 = day4[0].split("日")[0];
        String day05 = day5[0].split("日")[0];
        String day06 = day6[0].split("日")[0];

        // 斜杠
        String date = getString(R.string.date);
        // 设置日期
        if (day01 != null) {
            mDaysForecastTvDay1.setText(String.format(date, month1, day01));
        } else {
            mDaysForecastTvDay1.setText(R.string.dash);
        }

        mDaysForecastTvDay2.setText(String.format(date, month2, day02));
        mDaysForecastTvDay3.setText(String.format(date, month3, day03));
        mDaysForecastTvDay4.setText(String.format(date, month4, day04));
        mDaysForecastTvDay5.setText(String.format(date, month5, day05));
        mDaysForecastTvDay6.setText(String.format(date, month6, day06));

        // 取得白天天气类型图片id
        int weatherDayId1;
        if (weather != null) {
            assert weather1 != null;
            weatherDayId1 = MyUtil.getWeatherTypeImageID(weather1.getTypeDay(), true);
        } else {
            weatherDayId1 = R.drawable.ic_weather_no;
        }
        int weatherDayId2 = MyUtil.getWeatherTypeImageID(weather2.getTypeDay(), true);
        int weatherDayId3 = MyUtil.getWeatherTypeImageID(weather3.getTypeDay(), true);
        int weatherDayId4 = MyUtil.getWeatherTypeImageID(weather4.getTypeDay(), true);
        int weatherDayId5 = MyUtil.getWeatherTypeImageID(weather5.getTypeDay(), true);
        int weatherDayId6 = MyUtil.getWeatherTypeImageID(weather6.getTypeDay(), true);

        //设置白天天气类型图片
        mDaysForecastWeaTypeDayIv1.setImageResource(weatherDayId1);
        mDaysForecastWeaTypeDayIv2.setImageResource(weatherDayId2);
        mDaysForecastWeaTypeDayIv3.setImageResource(weatherDayId3);
        mDaysForecastWeaTypeDayIv4.setImageResource(weatherDayId4);
        mDaysForecastWeaTypeDayIv5.setImageResource(weatherDayId5);
        mDaysForecastWeaTypeDayIv6.setImageResource(weatherDayId6);

        // 设置白天天气类型文字
        if (weather != null) {
            mDaysForecastWeaTypeDayTv1.setText(weather1.getTypeDay());
        } else {
            mDaysForecastWeaTypeDayTv1.setText(R.string.dash);
        }

        mDaysForecastWeaTypeDayTv2.setText(weather2.getTypeDay());
        mDaysForecastWeaTypeDayTv3.setText(weather3.getTypeDay());
        mDaysForecastWeaTypeDayTv4.setText(weather4.getTypeDay());
        mDaysForecastWeaTypeDayTv5.setText(weather5.getTypeDay());
        mDaysForecastWeaTypeDayTv6.setText(weather6.getTypeDay());

        // 设置白天温度曲线
        if (weather != null) {
            mCharView.setTempDay(new int[]{getTemp(weather1.getHigh()),
                    getTemp(weather2.getHigh()), getTemp(weather3.getHigh()),
                    getTemp(weather4.getHigh()), getTemp(weather5.getHigh()),
                    getTemp(weather6.getHigh())});
        } else {
            mCharView.setTempDay(new int[]{-1000,
                    getTemp(weather2.getHigh()), getTemp(weather3.getHigh()),
                    getTemp(weather4.getHigh()), getTemp(weather5.getHigh()),
                    getTemp(weather6.getHigh())});
        }
        // 设置夜间温度曲线
        if (weather != null) {
            mCharView.setTempNight(new int[]{getTemp(weather1.getLow()),
                    getTemp(weather2.getLow()), getTemp(weather3.getLow()),
                    getTemp(weather4.getLow()), getTemp(weather5.getLow()),
                    getTemp(weather6.getLow())});
        } else {
            mCharView.setTempNight(new int[]{-1000,
                    getTemp(weather2.getLow()), getTemp(weather3.getLow()),
                    getTemp(weather4.getLow()), getTemp(weather5.getLow()),
                    getTemp(weather6.getLow())});
        }
        mCharView.invalidate();

        // 设置夜间天气类型文字
        if (weather != null) {
            mDaysForecastWeaTypeNightTv1.setText(weather1.getTypeNight());
        } else {
            mDaysForecastWeaTypeNightTv1.setText(R.string.dash);
        }
        mDaysForecastWeaTypeNightTv2.setText(weather2.getTypeNight());
        mDaysForecastWeaTypeNightTv3.setText(weather3.getTypeNight());
        mDaysForecastWeaTypeNightTv4.setText(weather4.getTypeNight());
        mDaysForecastWeaTypeNightTv5.setText(weather5.getTypeNight());
        mDaysForecastWeaTypeNightTv6.setText(weather6.getTypeNight());

        // 取得夜间天气类型图片id
        int weatherNightId1;
        if (weather != null) {
            weatherNightId1 = MyUtil.getWeatherTypeImageID(weather1.getTypeNight(), false);
        } else {
            weatherNightId1 = R.drawable.ic_weather_no;
        }
        int weatherNightId2 = MyUtil.getWeatherTypeImageID(weather2.getTypeNight(), false);
        int weatherNightId3 = MyUtil.getWeatherTypeImageID(weather3.getTypeNight(), false);
        int weatherNightId4 = MyUtil.getWeatherTypeImageID(weather4.getTypeNight(), false);
        int weatherNightId5 = MyUtil.getWeatherTypeImageID(weather5.getTypeNight(), false);
        int weatherNightId6 = MyUtil.getWeatherTypeImageID(weather6.getTypeNight(), false);

        //设置夜间天气类型图片
        mDaysForecastWeaTypeNightIv1.setImageResource(weatherNightId1);
        mDaysForecastWeaTypeNightIv2.setImageResource(weatherNightId2);
        mDaysForecastWeaTypeNightIv3.setImageResource(weatherNightId3);
        mDaysForecastWeaTypeNightIv4.setImageResource(weatherNightId4);
        mDaysForecastWeaTypeNightIv5.setImageResource(weatherNightId5);
        mDaysForecastWeaTypeNightIv6.setImageResource(weatherNightId6);

        // 设置风向
        if (weather != null) {
            mDaysForecastWindDirectionTv1.setText(weather1.getWindDirectionDay());
        } else {
            mDaysForecastWindDirectionTv1.setText(R.string.dash);
        }
        mDaysForecastWindDirectionTv2.setText(weather2.getWindDirectionDay());
        mDaysForecastWindDirectionTv3.setText(weather3.getWindDirectionDay());
        mDaysForecastWindDirectionTv4.setText(weather4.getWindDirectionDay());
        mDaysForecastWindDirectionTv5.setText(weather5.getWindDirectionDay());
        mDaysForecastWindDirectionTv6.setText(weather6.getWindDirectionDay());

        // 设置风力
        if (weather != null) {
            mDaysForecastWindPowerTv1.setText(weather1.getWindPowerDay());
        } else {
            mDaysForecastWindPowerTv1.setText(R.string.dash);
        }
        mDaysForecastWindPowerTv2.setText(weather2.getWindPowerDay());
        mDaysForecastWindPowerTv3.setText(weather3.getWindPowerDay());
        mDaysForecastWindPowerTv4.setText(weather4.getWindPowerDay());
        mDaysForecastWindPowerTv5.setText(weather5.getWindPowerDay());
        mDaysForecastWindPowerTv6.setText(weather6.getWindPowerDay());
    }

    /**
     * 取得温度
     *
     * @param temp 温度信息
     * @return 温度
     */
    private int getTemp(String temp) {
        String temperature;
        if (!temp.contains("-")) {
            if (temp.length() == 6) {
                temperature = temp.substring(3, 5);
            } else {
                temperature = temp.substring(3, 4);
            }
        } else {
            if (temp.length() == 7) {
                temperature = temp.substring(3, 6);
            } else {
                temperature = temp.substring(3, 5);
            }
        }
        return Integer.parseInt(temperature);
    }

    /**
     * 截取日期和星期
     *
     * @param date 日期信息
     * @return 包含日期和星期的数组
     */
    private String[] getDay(String date) {
        String[] date1 = new String[2];
        if (date.length() == 5) {
            date1[0] = date.substring(0, 2);
            date1[1] = date.substring(2);
        } else {
            date1[0] = date.substring(0, 3);
            date1[1] = date.substring(3);
        }
        return date1;
    }

    /**
     * 转换周的标题
     *
     * @param week 需要转换的周标题
     * @return 周的标题
     */
    private String getWeek(String week) {
        String week1;
        switch (week) {
            case "星期一":
                week1 = getString(R.string.monday);
                break;
            case "星期二":
                week1 = getString(R.string.tuesday);
                break;
            case "星期三":
                week1 = getString(R.string.wednesday);
                break;
            case "星期四":
                week1 = getString(R.string.thursday);
                break;
            case "星期五":
                week1 = getString(R.string.friday);
                break;
            case "星期六":
                week1 = getString(R.string.saturday);
                break;
            case "星期天":
            case "星期日":
                week1 = getString(R.string.sunday);
                break;
            default:
                week1 = week;
                break;
        }
        return week1;
    }

    /**
     * 设置温度图片
     *
     * @param temp1     温度
     * @param imageView imageView控件
     */
    private void setTemperatureImage(int temp1, ImageView imageView) {
        switch (temp1) {
            case 0:
                imageView.setImageResource(R.drawable.number_0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.number_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.number_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.number_3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.number_4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.number_5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.number_6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.number_7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.number_8);
                break;
            case 9:
                imageView.setImageResource(R.drawable.number_9);
                break;
            default:
                imageView.setImageResource(R.drawable.number_0);
                break;
        }
    }

}
