package com.sf.iactress.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类，日期格式的各种转换
 *
 * @author GINO
 * @date 2014.02.18
 */
public class DateUtil {

    /**
     * 时间格式 如2014-02-24 15:41:41
     */
    public static final SimpleDateFormat DATE_FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式 如2014-02-24 15:41
     */
    public static final SimpleDateFormat DATE_FORMAT_2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 时间格式 如20140224-1541
     */
    public static final SimpleDateFormat DATE_FORMAT_3 = new SimpleDateFormat("yyyyMMdd-HHmmss");

    /**
     * 时间格式 如20140224
     */
    public static final SimpleDateFormat DATE_FORMAT_4 = new SimpleDateFormat("yyyyMMdd");

    /**
     * 时间格式 如2014-02-24 15:41
     */
    public static final SimpleDateFormat DATE_FORMAT_5 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 时间格式 如2014年02月24日 15:41
     */
    public static final SimpleDateFormat DATE_FORMAT_6 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_7 = new SimpleDateFormat("HH:mm");

    /**
     * 时间格式 如2014-02-24 15:41:41
     */
    public static final SimpleDateFormat DATE_FORMAT_8 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");


    /**
     * 获取当前时间格式
     *
     * @param dateFormat 时间格式
     * @return
     */
    public static String getCurrentTime(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTime(), dateFormat);
    }

    /**
     * 获取指定时间
     *
     * @param timeInMillis 要格式的时间
     * @param dateFormat   时间格式
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * 获取当前时间  默认时间格式(如2014-02-24 15:41)
     *
     * @param timeInMillis 要格式的时间
     * @return
     */
    public static String getCurrentTime(long timeInMillis) {
        return getTime(timeInMillis, DATE_FORMAT_1);
    }

    /**
     * 获取当前时间  返回long型
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取指定时间  返回long型
     *
     * @return
     */
    public static long getDateTime(String dateTime) throws ParseException {
        return DATE_FORMAT_1.parse(dateTime).getTime();
    }

    /**
     * 获取指定时间  返回long型
     *
     * @return
     */
    public static long getDateTime(String dateTime, SimpleDateFormat dateFormat) throws ParseException {
        return dateFormat.parse(dateTime).getTime();
    }


    /**
     * 获取指定格式的时间
     *
     * @param dateTime
     * @param dateFormat 时间格式
     * @return
     * @throws ParseException
     */
    public static String getTime(String dateTime, SimpleDateFormat dateFormat) throws ParseException {
        return dateFormat.format(new Date(dateFormat.parse(dateTime).getTime()));
    }

    /**
     * 获取当前时间  返回String型(如2014-02-24 15:41)
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getCurrentTime(getCurrentTime());
    }

    public static String getCurrentTimeInStringAddOnHour() {
        return getCurrentTime(getCurrentTime() + 3600000);
    }

}
