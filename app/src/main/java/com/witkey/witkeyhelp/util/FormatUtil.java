package com.witkey.witkeyhelp.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author lingxu
 * @date 2019/7/17 15:52
 * @description 格式化工具类
 */
public class FormatUtil {
    private static String className="FormatUtil";
    /**
     * 格式化日期
     */
    public static String formatTime(long timeMillis) {
        Date d = new Date(timeMillis);
        return new SimpleDateFormat("MM月dd日", Locale.getDefault()).format(d);
    }

    public static String formatTime(long timeMillis, String format) {
        Date d = new Date(timeMillis);
        return new SimpleDateFormat(format, Locale.getDefault()).format(d);
    }

    public static String formatTimeYMD(long timeMillis) {
        Date d = new Date(timeMillis);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(d);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        LogUtil.logInfo(className, "stringToDate: " + strTime);
        date = formatter.parse(strTime);
        return date;
    }

    public static Date stringToDateYMD(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        LogUtil.logInfo(className, "stringToDateYMD: " + strTime);
        date = formatter.parse(strTime);
        return date;
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static long stringToLongYMD(String strTime)
            throws ParseException {
        Date date = stringToDate(strTime, "yyyy-MM-dd"); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 格式化价钱
     */
    public static String formatPrice(long price) {
        // DecimalFormat df = new DecimalFormat();
        // df.applyPattern("0.00#");
        // return "¥ " + df.format(price / 100.0);
        return "¥ " + (price / 100f);
    }

    public static SpannableStringBuilder formatTotalprice(long price) {
        String start = "票总额：";
        String text = start + (price / 100);
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(0xFFE13F3D);
        builder.setSpan(redSpan, start.length(), text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder formatTicketdisprice(long price) {
        String start = "需支付  ";
        String text = start + formatPrice(price).trim();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(0xFFE13F3D);
        builder.setSpan(redSpan, start.length(), text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static String getFilmType(int type) {
        String hallType = "";
        if (type == 0) {
            hallType = "普通";
        } else if (type == 1) {
            hallType = "VIP";
        } else if (type == 2) {
            hallType = "IMAX";
        } else if (type == 3) {
            hallType = "X-LAND";
        }
        return hallType;
    }

    public static String getFilmDimen(int dimen) {
        String filmType = "";
        if (dimen == 0) {
            filmType = "2D";
        } else if (dimen == 1) {
            filmType = "3D";
        } else if (dimen == 4) {
            filmType = "2D/3D";
        } else if (dimen == 5) {
            filmType = "2D/IMAX-2D";
        } else if (dimen == 6) {
            filmType = "3D/IMAX-3D";
        } else if (dimen == 7) {
            filmType = "2D/3D/IMAX-3D";
        }

        return filmType;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        mobiles = mobiles.trim().replaceAll(" ", "");
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证姓名
     */
    public static boolean isName(String name) {
        String telRegex = "[\\u4e00-\\u9fa5]{2,20}|[a-zA-Z]{3,20}";//
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            return name.matches(telRegex);
        }
    }

    /**
     * 判断是否是邮箱
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        // String strPattern =
        // "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 判断密码(数字加字母)
     */
    public static boolean isPassword(String pass) {
        // String strPattern = ".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]";
        String strPattern = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,12})$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(pass);
        return m.matches();
    }

    /**
     * 判断是否为6位数字
     */
    public static boolean isFourNumber(String source) {
        if (!TextUtils.isEmpty(source) && source.length() ==6) {
            return true;
        }
        return false;
    }


    /**
     * 判断昵称
     */
    public static boolean isNick(String nick) {
        String reg = "^[a-zA-Z0-9\u4E00-\u9FA5_-]*$";
        if (TextUtils.isEmpty(nick))
            return false;
        else
            return nick.matches(reg);
    }

    /**
     * 判断微信号
     */
    public static boolean isWeChat(String weChat) {
        String reg = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$";
        if (TextUtils.isEmpty(weChat))
            return false;
        else
            return weChat.matches(reg);
    }

    /**
     * 检测是否有emoji表情
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;

    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 去除emoji表情
     */
    public static String deleteEmoji(String source) {
        StringBuffer str = new StringBuffer(source);
        for (int i = 0; i < source.length(); i++) {
            char codePoint = source.charAt(i);
            String substring = source.substring(i, i + 1);
            if (!isEmojiCharacter(codePoint) || "☺".equals(substring)) {
                // 如果不能匹配,则该字符是Emoji表情
                StringBuffer deleteCharAt = str.deleteCharAt(i);
                return deleteEmoji(deleteCharAt.toString());
            }
        }
        return source;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumber(String source) {
        StringBuffer str = new StringBuffer(source);
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断密码(数字)
     */
    public static boolean isPass(String pass) {
        boolean flag1 = true;
        char str = pass.charAt(0);
        for (int i = 0; i < pass.length(); i++) {
            if (str != pass.charAt(i)) {
                flag1 = false;
                break;
            }
        }
        boolean flag2 = true;
        for (int i = 0; i < pass.length(); i++) {
            if (i > 0) {// 判断如123456
                int num = Integer.parseInt(pass.charAt(i) + "");
                int num_ = Integer.parseInt(pass.charAt(i - 1) + "") + 1;
                if (num != num_) {
                    flag2 = false;
                    break;
                }
            }
        }
        boolean flag3 = true;
        for (int i = 0; i < pass.length(); i++) {
            if (i > 0) {// 判断如654321
                int num = Integer.parseInt(pass.charAt(i) + "");
                int num_ = Integer.parseInt(pass.charAt(i - 1) + "") - 1;
                if (num != num_) {
                    flag3 = false;
                    break;
                }
            }
        }
        if (flag1 == false && flag2 == false && flag3 == false) {
            return true;
        }

        return false;
    }

    /**
     * 获取优酷视频ID
     */
    public static String idFromUrl(String url) {
        int index = url.indexOf("id_");
        String substring = null;
        if (index != -1) {
            substring = url.substring(index + 3, index + 16);
        } else {
            substring = url;
        }

        return substring;

    }

    /**
     * 获取当前小时和分钟
     */
    public static String getWhenTime() {
        Time time = new Time("GMT+8");
        time.setToNow();
        Calendar calendar = Calendar.getInstance();
        String whentime = calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + time.minute;
        return whentime;
    }

    /*
 * 毫秒转化时分秒毫秒
 */
    public static String formatTime2String(Long ms) {
        ms *= 1000;

        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long allHour = ms / hh;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

//        StringBuffer sb = new StringBuffer();
////        if(day > 0) {
////            sb.append(day+"天");
////        }
//        if(hour > 0) {
//            sb.append(hour+"小时");
//        }
//        if(minute > 0) {
//            sb.append(minute+"分");
//        }
//        if(second > 0) {
//            sb.append(second+"秒");
//        }
//        if(milliSecond > 0) {
//            sb.append(milliSecond+"毫秒");
//        }
        String format = "%02d";
        return String.format(format, allHour) + ":" + String.format(format, minute) + ":" + String.format(format, second);
    }
}
