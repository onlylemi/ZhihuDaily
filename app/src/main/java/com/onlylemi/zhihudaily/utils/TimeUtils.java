package com.onlylemi.zhihudaily.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 *
 * @author: onlylemi
 * @time: 2016-06-26 12:25
 */
public class TimeUtils {

    private TimeUtils() {
    }

    public static int currentTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String timestampToDate(long timestamp, String format) {
        if (null == format || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timestamp * 1000));
    }

    public static long dateToTimestamp(String date, String format) {
        if (null == format || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
