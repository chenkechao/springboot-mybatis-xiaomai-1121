package com.niu.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ami on 2018/11/21.
 */
public class DateUtils {

    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static String dateToString(Date date, String pattern) {
        if (date == null || StringUtils.isEmpty(pattern)) {
            return "";
        }
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date stringToDate(String dateStr, String pattern) {
        Date date = null;
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return date;
    }

    /**
     * 将日期字符串转换为Date类型
     * String类型的日期格式和要转化的format格式必须一样
     *
     * @param dateStr    日期字符串
     * @param dateFormat 日期格式  yyyy-MM-dd
     * @return date
     */
    public static Date transformStringToDate(String dateStr, String dateFormat) {
        Date date = null;
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return date;
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date       日期字符串
     * @param dateFormat 日期格式
     * @return dateStr
     */
    public static String transformDateToString(Date date, String dateFormat) {
        if (date == null || StringUtils.isEmpty(dateFormat)) {
            return "";
        }
        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

}
