package com.witkey.witkeyhelp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 */
public class CalendarUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
//    public static List<String> queryData(String startAt, String endAt) {
//        List<String> dates = new ArrayList<>();
//        try {
//            Date startDate = dateFormat.parse(startAt);
//            Date endDate = dateFormat.parse(endAt);
//            dates.addAll(queryData(startDate, endDate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return dates;
//    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<Date> queryData(String startAt, String endAt) {
        List<Date> dates = new ArrayList<>();
        try {
            Date startDate = dateFormat.parse(startAt);
            Date endDate = dateFormat.parse(endAt);
            dates.addAll(queryData(startDate, endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<Date> queryData(Date startAt, Date endAt) {
        List<Date> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(startAt);
        Calendar end = Calendar.getInstance();
        end.setTime(endAt);
        while (start.before(end) || start.equals(end)) {
            dates.add(start.getTime());
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
//    } public static List<String> queryData(Date startAt, Date endAt) {
//        List<String> dates = new ArrayList<>();
//        Calendar start = Calendar.getInstance();
//        start.setTime(startAt);
//        Calendar end = Calendar.getInstance();
//        end.setTime(endAt);
//        while (start.before(end) || start.equals(end)) {
//            dates.add(dateFormat.format(start.getTime()));
//            start.add(Calendar.DAY_OF_YEAR, 1);
//        }
//        return dates;
//    }
    }

    public static Date getYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return time;
    }

    public static Date getTommrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date time = cal.getTime();
        return time;
    }

    public static String getTodayString() {
        String format = "%02d";
        return getTime()[0] + "-" + String.format(format, (getTime()[1] + 1)) + "-" + String.format(format, getTime()[2]);
    }


    public static int[] getTime() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return new int[]{year, monthOfYear, dayOfMonth};
    }
}