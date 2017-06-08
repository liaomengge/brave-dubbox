package com.github.lmg.brave.dubbox.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liaomengge on 17/5/25.
 */
public final class DateUtil {

    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMdd_HHmmss_SSS = "yyyyMMdd_HHmmss_SSS";

    private DateUtil() {
    }

    public static String getNowDate2String() {
        return DateFormatUtils.format(new Date(), yyyy_MM_dd_HH_mm_ss);
    }

    public static String getNowDate2String(String format) {
        return DateFormatUtils.format(new Date(), format);
    }

    public static String getDate2String(Date date) {
        return getDate2String(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static String getDate2String(Date date, String format) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date, format);
    }

    public static Date getString2Date(String date) {
        return getString2Date(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static Date getString2Date(String date, String format) {
        String[] date_format = {format};
        try {
            return DateUtils.parseDate(date, date_format);
        } catch (ParseException e) {
            return null;
        }
    }

    public static long getTime() {
        return new Date().getTime();
    }

    /**
     * 获取今天起始时间字符串
     *
     * @return
     */
    public static String getTodayBegin2String() {
        return DateFormatUtils.format(getTodayBegin(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取今天结束时间字符串
     *
     * @return
     */
    public static String getTodayEnd2String() {
        return DateFormatUtils.format(getTodayEnd(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取今天的开始时刻
     *
     * @return
     */
    public static Date getTodayBegin() {
        return getBegin4Date(new Date());
    }


    /**
     * 获取今天的结束时刻
     *
     * @return
     */
    public static Date getTodayEnd() {
        return getEnd4Date(new Date());
    }

    public static Date getBegin4Date(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getEnd4Date(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }
}
