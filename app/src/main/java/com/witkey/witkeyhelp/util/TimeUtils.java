package com.witkey.witkeyhelp.util;

import android.text.format.DateFormat;
import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by wapchief on 2017/8/1.
 * 日期毫秒互换辅助类
 * dataForamt:yyyy-MM-dd //格式
 * yyyy-MM-dd HH:mm:ss
 */

public class TimeUtils {

    //时间转化毫秒
    public static long date2ms(String time) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    //毫秒转化成日期
    public static String ms2date(String dateForamt, long ms) {
        Date date = new Date(ms);
        SimpleDateFormat format = new SimpleDateFormat(dateForamt);
        return format.format(date);
    }

    /**
     * 时间戳转日期
     */
    public static String unix2Date(String dateForamt, long ms) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateForamt);
        String sd = sdf.format(new Date(ms * 1000));
        return sd;
    }

    public static String getDateStr(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static long Date2ms(String _data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(_data);
            return date.getTime();
        } catch (Exception e) {

            return 0;
        }
    }

    //将毫秒转换为标准日期格式
    public static String DateDistance2now(long _ms) {
        SimpleDateFormat DateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Long time = new Long(_ms);
            String d = DateF.format(time);
            Date startDate = DateF.parse(d);
            Calendar calendars = Calendar.getInstance();
            calendars.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            String year = String.valueOf(calendars.get(Calendar.YEAR));

            String month = String.valueOf(calendars.get(Calendar.MONTH));

            String day = String.valueOf(calendars.get(Calendar.DATE));

            String hour = String.valueOf(calendars.get(Calendar.HOUR_OF_DAY));

            String min = String.valueOf(calendars.get(Calendar.MINUTE));

            String second = String.valueOf(calendars.get(Calendar.SECOND));

            Boolean isAm = calendars.get(Calendar.AM_PM) == 1 ? true : false;

            Boolean is24 = DateFormat.is24HourFormat(MyAPP.getInstance()) ? true : false;

            long nowDate = Date2ms(year + "-" + (Integer.parseInt(month) + 1) + "-" + day + " " + hour + ":" + min + ":" + second);


            return DateDistance(startDate, nowDate);

        } catch (Exception e) {
            Log.e("测试b", e.getMessage());
            ;
        }
        return null;
    }

    //计算与当前的时间差
    public static String DateDistance(Date startDate, long endDate) {
        if (startDate == null) {
            return null;
        }
        long timeLong = endDate - startDate.getTime();
        if (timeLong < 0) {
            timeLong = 0;
        }
        if (timeLong < 60 * 1000)
            //return timeLong / 1000 + "秒前";
            return "刚刚";

        else if (timeLong < 60 * 60 * 1000) {
            timeLong = timeLong / 1000 / 60;
            return timeLong + "分钟前";
        } else if (timeLong < 60 * 60 * 24 * 1000) {
            long timeLonga = (endDate - getTodayZero());
            timeLong = endDate - startDate.getTime();
            if (timeLong > timeLonga) {
                return "昨天";
            } else {
                timeLong = timeLong / 60 / 60 / 1000;
                return timeLong + "小时前";
            }
        } else if ((timeLong / 1000 / 60 / 60 / 24) < 7) {
            timeLong = timeLong / 1000 / 60 / 60 / 24;
            if (timeLong > 1) {
                return ms2DateOnlyDay(startDate.getTime());
            } else {
                return timeLong + "天前";
            }

        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(startDate);
        }
    }

    public static String ms2DateOnlyDay(long _ms) {
        Date date = new Date(_ms);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(date);
    }

    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l));
    }

    /**
     * 将字符串型日期转换成日期
     *
     * @param dateStr 字符串型日期
     * @param
     * @return
     */
    public static Calendar stringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = formatter.parse(dateStr);
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(parse);
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }
        //获取现在时间
    public static String currentTime() {

        Calendar calendars = Calendar.getInstance();
        calendars.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(calendars.get(Calendar.YEAR));

        String month = String.valueOf(calendars.get(Calendar.MONTH));

        String day = String.valueOf(calendars.get(Calendar.DATE));

        String hour = String.valueOf(calendars.get(Calendar.HOUR_OF_DAY));

        String min = String.valueOf(calendars.get(Calendar.MINUTE));

        String second = String.valueOf(calendars.get(Calendar.SECOND));
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(  year + "-" + (Integer.parseInt(month) + 1) + "-" + day + " " + hour + ":" + min + ":" + second);

        return stringBuffer.toString();
    }
}
