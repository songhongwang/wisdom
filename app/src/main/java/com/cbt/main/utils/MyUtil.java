/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.cbt.main.utils;

import com.cbt.main.R;

/**
 * 工具类
 *
 * @author 咖枯
 * @version 1.0 2015/07
 */
public class MyUtil {

    /**
     * 取得对应的天气类型图片id
     *
     * @param type  天气类型
     * @param isDay 是否为白天
     * @return 天气类型图片id
     */
    public static int getWeatherTypeImageID(String type, boolean isDay) {
        if (type == null) {
            return R.drawable.ic_weather_no;
        }
        int weatherId;
        switch (type) {
            case "晴":
                if (isDay) {
                    weatherId = R.drawable.ic_weather_sunny_day;
                } else {
                    weatherId = R.drawable.ic_weather_sunny_night;
                }
                break;
            case "多云":
                if (isDay) {
                    weatherId = R.drawable.ic_weather_cloudy_day;
                } else {
                    weatherId = R.drawable.ic_weather_cloudy_night;
                }
                break;
            case "阴":
                weatherId = R.drawable.ic_weather_overcast;
                break;
            case "雷阵雨":
            case "雷阵雨伴有冰雹":
                weatherId = R.drawable.ic_weather_thunder_shower;
                break;
            case "雨夹雪":
                weatherId = R.drawable.ic_weather_sleet;
                break;
            case "冻雨":
                weatherId = R.drawable.ic_weather_ice_rain;
                break;
            case "小雨":
            case "小到中雨":
            case "阵雨":
                weatherId = R.drawable.ic_weather_light_rain_or_shower;
                break;
            case "中雨":
            case "中到大雨":
                weatherId = R.drawable.ic_weather_moderate_rain;
                break;
            case "大雨":
            case "大到暴雨":
                weatherId = R.drawable.ic_weather_heavy_rain;
                break;
            case "暴雨":
            case "大暴雨":
            case "特大暴雨":
            case "暴雨到大暴雨":
            case "大暴雨到特大暴雨":
                weatherId = R.drawable.ic_weather_storm;
                break;
            case "阵雪":
            case "小雪":
            case "小到中雪":
                weatherId = R.drawable.ic_weather_light_snow;
                break;
            case "中雪":
            case "中到大雪":
                weatherId = R.drawable.ic_weather_moderate_snow;
                break;
            case "大雪":
            case "大到暴雪":
                weatherId = R.drawable.ic_weather_heavy_snow;
                break;
            case "暴雪":
                weatherId = R.drawable.ic_weather_snowstrom;
                break;
            case "雾":
                weatherId = R.drawable.ic_weather_foggy;
                break;
            case "霾":
                weatherId = R.drawable.ic_weather_haze;
                break;
            case "沙尘暴":
                weatherId = R.drawable.ic_weather_duststorm;
                break;
            case "强沙尘暴":
                weatherId = R.drawable.ic_weather_sandstorm;
                break;
            case "浮尘":
            case "扬沙":
                weatherId = R.drawable.ic_weather_sand_or_dust;
                break;
            default:
                if (type.contains("尘") || type.contains("沙")) {
                    weatherId = R.drawable.ic_weather_sand_or_dust;
                } else if (type.contains("雾") || type.contains("霾")) {
                    weatherId = R.drawable.ic_weather_foggy;
                } else if (type.contains("雨")) {
                    weatherId = R.drawable.ic_weather_ice_rain;
                } else if (type.contains("雪") || type.contains("冰雹")) {
                    weatherId = R.drawable.ic_weather_moderate_snow;
                } else {
                    weatherId = R.drawable.ic_weather_no;
                }
                break;
        }

        return weatherId;
    }

}
