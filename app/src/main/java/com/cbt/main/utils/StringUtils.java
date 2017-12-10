package com.cbt.main.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;


import com.cbt.main.app.GlobalApplication;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private final static String TAG = StringUtils.class.getSimpleName();

    // public static String stringFilter(String str) {
    // if (str == null) {
    // return str;
    // }
    //
    // return str;
    // }
    //
    public static String[] getMergeArray(String[] al, String[] bl) {
        String[] a = al;
        String[] b = bl;
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static String get1(String str, int size, String spliter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(spliter).append(str);
        }

        if (sb.length() > 0) {
            return sb.substring(spliter.length());
        }

        return sb.toString();
    }

    public static String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        // if (in != null && in.startsWith("\ufeff")) {
        // in = in.substring(1);
        // }

        return in;
    }

    public static String ToDBC(String str) {
        if (str == null) {
            return str;
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static String trim(String s) {
        if (s == null) {
            return s;
        }
        String rgx = "[ ,\\.?!，。！？　'\":; ]";
        s = s.replaceAll(rgx, "");

        return s.trim().replaceAll("　", "").replaceAll("\n", "").replaceAll("\r", "");
    }

    public static String trimRight(String origin, String regex) {
        if (origin == null) {
            return "";
        }
        if (origin.endsWith(regex)) {
            origin = origin.substring(0, origin.lastIndexOf(regex));
        }
        return origin;
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     */
    public static String stringFilter(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":").replaceAll("（", "(").replaceAll("）", ")");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static void setColor(TextView view, List<String> colorKey, int colorId, String drawableKey, int drawableId) {
        if (view == null || colorKey == null || colorKey.size() <= 0) {
            return;
        }
        String temp = view.getText().toString();
        Spannable spannable = new SpannableString(temp);

        int color = GlobalApplication.mApp.getResources().getColor(colorId);
        for (String key : colorKey) {
            Pattern p = getAtPattern(key);
            Matcher m = p.matcher(temp);

            while (m.find()) {
                ForegroundColorSpan span = new ForegroundColorSpan(color);
                spannable.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        try {
            if (drawableId > 0 && !TextUtils.isEmpty(drawableKey)) {
                Drawable drawable = GlobalApplication.mApp.getResources().getDrawable(drawableId);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    int start = temp.indexOf(drawableKey);
                    if (start >= 0) {
                        spannable.setSpan(new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE), start, start + drawableKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setText(spannable);
    }

    public static void setColor(TextView view, String colorKey, int colorId, String drawableKey, int drawableId) {
        if (view == null || TextUtils.isEmpty(colorKey)) {
            return;
        }
        String temp = view.getText().toString();
        Spannable spannable = new SpannableString(temp);

        int color = GlobalApplication.mApp.getResources().getColor(colorId);

        // for (String key : colorKey) {
        Pattern p = getAtPattern(colorKey);
        Matcher m = p.matcher(temp);

        while (m.find()) {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            spannable.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // }

        try {
            if (drawableId > 0 && !TextUtils.isEmpty(drawableKey)) {
                Drawable drawable = GlobalApplication.mApp.getResources().getDrawable(drawableId);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    int start = temp.indexOf(drawableKey);
                    if (start >= 0) {
                        spannable.setSpan(new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE), start, start + drawableKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setText(spannable);
    }

    public static void setTextviewColorAndBold(TextView textView, String key, String value, int color, boolean isbold) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (!TextUtils.isEmpty(key)) {
            SpannableStringBuilder style = new SpannableStringBuilder(value);
            int index = value.indexOf(key);
            if (index >= 0) {
                while (index < value.length() && index >= 0) {
                    if (color != 0) {
                        style.setSpan(new ForegroundColorSpan(color), index, index + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (isbold) {
                        style.setSpan(new StyleSpan(Typeface.BOLD), index, index + key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    textView.setText(style);
                    index = value.indexOf(key, index + key.length());
                }
            } else {
                textView.setText(value);
            }
        } else {
            textView.setText(value);
        }
    }

    public static void setDrawable(TextView view, String drawableKey, int drawableId) {
        if (view == null || TextUtils.isEmpty(drawableKey) || drawableId <= 0) {
            return;
        }
        String temp = view.getText().toString();
        Spannable spannable = new SpannableString(temp);

        Drawable drawable = GlobalApplication.mApp.getResources().getDrawable(drawableId);
        if (drawable != null && !TextUtils.isEmpty(drawableKey)) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            int start = temp.indexOf(drawableKey);
            if (start >= 0) {
                spannable.setSpan(new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE), start, start + drawableKey.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        view.setText(spannable);
    }

    public static final String LANLORD_SPAN = "LEHE_WY_LANLORD";

    private static Pattern getAtPattern(String keyword) {
        Pattern atpattern = Pattern.compile(Pattern.quote(keyword));
        return atpattern;
    }

    /**
     * 判断字符串是否为数字
     */

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String email) {
        return !TextUtils.isEmpty(email) && email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }

    /**
     * 时间比较大小
     */

    public static int compare_date(String DATE1, String DATE2) {
        try {
            Date dt1 = DateUtils.parseDatetimeToDate(DATE1);
            Date dt2 = DateUtils.parseDatetimeToDate(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /*
     * 对json字符串进行过滤,防止乱码和黑框
     */
    public static String JsonFilter(String jsonstr) {
        return jsonstr.substring(jsonstr.indexOf("{")).replace("\\n", "\n");
    }

    public static String toSmallUrl(String s) {
        if (!TextUtils.isEmpty(s)) {
            return s.replaceAll("_large", "_small");
        } else {
            return s;
        }
    }

    /*
     * list<Sting> 转换为以“，”为分割的字符串
     *
     */
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static String getdir(double index) {

        return "v" + String.valueOf((int) Math.ceil((index / 100000)));
    }


    public static String getURL(String vid) {
        if (TextUtils.isEmpty(vid)) {
            return null;
        }

        String result = null;

        vid = "v" + addZeroForNum(vid, 9);

        // Sending side
        try {
            byte[] data = vid.getBytes("UTF-8");

            String str = android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP);

            ArrayList<String> a = new ArrayList<>();
            ArrayList<String> b = new ArrayList<>();

            int j = 0;
            for (int i = 1; i <= str.length(); i++) {
                String s = str.substring(i - 1, i);
                if (s.equals("=")) {
                    j++;
                } else {
                    if (i % 2 == 0) {
                        b.add(s);
                    } else {
                        a.add(s);
                    }
                }
            }

            a.addAll(b);
            a.add(String.valueOf(j));

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < a.size(); i++) {
                sb.append(a.get(i));
            }

            result = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*数字不足位数左补0
      *
      * @param str
      * @param strLength
      */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                // sb.append(str).append("0");//右补0
                str = "0" + str;
                strLen = str.length();
            }
        }
        return str;
    }

    public static String getfilesize(String title) {
        if (title == null) {
            return null;
        }

        int charIndex = title.indexOf('M');

        if (-1 == charIndex) {
            return title;
        } else {
            return title.substring(0, charIndex);
        }
    }

    public static String doubleFormat(double d) {

        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        return df.format(d);
    }

    public static String oneFormat(double d) {

        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
        return df.format(d);
    }

    public static String converttowan1(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }

        String result = str;

        try {
            if (str.length() > 4) {
                result = str.substring(0, str.length() - 4) + ".";
                result = result + str.substring(str.length() - 4, str.length() - 3) + "万";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String converttowan(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        long num;
        try {
            num = Long.valueOf(str);
            if (num < 10000) {
                return str;
            } else {
                double n = (double) num / 10000;
                BigDecimal bg = new BigDecimal(n);
                double f = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                return f + "万";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return converttowan1(str);
        }
    }

    public static boolean isDefalutName(String name) {
        boolean result = false;

        try {

            if (!TextUtils.isEmpty(name) && name.length() > 2) {
                String tmpStr = name.trim().substring(0, 2);
                if (tmpStr.equals("糖粉")) {
                    if (StringUtils.isNumeric(name.substring(4).trim())) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    ////手机号正则
    //public static boolean isMobileNO(String mobiles) {
    //    if (TextUtils.isEmpty(mobiles) || mobiles.length() != 11) {
    //        return false;
    //    }
    //
    //    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(18[0,0-9]))\\d{8}$");
    //    Matcher m = p.matcher(mobiles);
    //    return m.matches();
    //}

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean isMobileNO(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean isPhoneNO(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && phoneNumber.length() >= 8 && phoneNumber.length() <= 11;
    }

    /// <summary>

    /// 替换手机号中间四位为*

    /// </summary>

    /// <param name="phoneNo"></param>

    /// <returns></returns>

    public static String ReturnPhoneNO(String phoneNo)

    {
        if (TextUtils.isEmpty(phoneNo)) {
            return "";
        }
        return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String formatString(String stringToFormat, int max) {
        if (stringToFormat.length() > max) {
            stringToFormat = stringToFormat.substring(0, max - 1) + "...";
        }
        return stringToFormat;
    }

    public static String replaceChare(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replace("&lt;", "<");
            str = str.replace("&gt;", ">");
            str = str.replace("&quot;", "\"");
            str = str.replace("&apos", "'");
            str = str.replace("&amp;", "&");
        }

        return str;
    }

    public static String moreNum(int num) {
        if (num <= 0) {
            return "0";
        }
        String result = "";
        if (num <= 999) {
            return String.valueOf(num);
        } else {
            return "999+";
        }
    }

    /**
     * @param context
     * @return ImageGetter
     */
    public static Html.ImageGetter getImageGetterStr(final Context context, final int width, final int height) {
        return new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                int id = context.getResources().getIdentifier(source, "drawable", "com.bokecc.dance");

                try {
                    Drawable d = context.getResources().getDrawable(id);
                    d.setBounds(0, 0, width, height);
                    return d;
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }


    // 将毫秒数转换为时间格式
    public static String progresstime(int progress) {
        Date date = new Date(progress);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(date);
    }

    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


}
