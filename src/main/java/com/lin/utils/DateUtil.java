package com.lin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 林炳昌
 * @desc 日期工具类
 * @date 2023年04月14日 22:25
 */
public class DateUtil {


    /**
     * @desc 获取当天是星期几
     * @date 2023/4/14 22:28
     */
    public static String getDayOfWeek() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String dayOfWeek = simpleDateFormat.format(new Date());
        return dayOfWeek;

    }

    /**
     * @desc 以参数date为第一周,计算当天是第几周(默认date为星期一)
     * @date 2023/4/14 22:48
     */
    public static Long getWeekNumber(Date date) {

        Date now = new Date();
        return (now.getTime() - date.getTime()) / (24 * 3600 * 1000) / 7 + 1;

    }

    /**
     * @desc 获取当前时间到秒
     * @date 2023/4/22 11:34
     */
    public static String getDateTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        Date now = new Date();//获取当前时间
        String date=sdf.format(now);
        return date;

    }

}
