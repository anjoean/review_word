/*
 *  新疆电信综合资源管理系统-资源服务模块
 *  版权所有：浩鲸云计算科技股份有限公司
 */
package com.weixin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author linkz
 */
public class DateFormatUtils {

    /**
     * 返回当前时间的yyyy-MM-dd格式
     *
     * @return
     */
    public static String dateFormat() {
        return dateFormat("yyyy-MM-dd", 0);
    }

    /**
     * 返回当前时间的yyyy-MM-dd HH:mm:ss格式
     *
     * @return
     */
    public static String datetimeFormat() {
        return dateFormat("yyyy-MM-dd HH:mm:ss", 0);
    }

    /**
     * 返回当前时间的指定格式，格式字符串参见java类SimpleDateFormat
     *
     * @param format
     * @return
     */
    public static String dateFormat(String format) {
        return dateFormat(format, 0);
    }

    /**
     * 按照指定的时间格式，返回与当前时间间隔了指定天数的日期
     *
     * @param format
     * @param idx
     * @return
     */
    public static String dateFormat(String format, int idx) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, idx);
        return sdf.format(calendar.getTime());
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd", 0);
    }

    public static String dateFormat(Date date, String format) {
        return dateFormat(date, format, 0);
    }

    public static String datetimeFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd HH:mm:ss", 0);
    }

    public static String datetimeFormat(Date date, String format) {
        return dateFormat(date, format, 0);
    }

    public static String dateFormat(Date date, String format, int idx) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, idx);
        return sdf.format(calendar.getTime());
    }

}
