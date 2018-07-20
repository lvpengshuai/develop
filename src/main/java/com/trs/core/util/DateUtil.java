package com.trs.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期和时间相关工具方法. <BR>
 */
public class DateUtil {

    /**
     * 将使用的毫秒数转化为可读的字符串, 如1天1小时1分1秒. <BR>
     * <code>assertEquals("1天1小时1分1秒", DateUtil.timeToString(90061000));</code>
     *
     * @param msUsed 使用的毫秒数.
     * @return 可读的字符串, 如1天1小时1分1秒.
     */
    public static Map timeToString(long msUsed) {
        Map resultMap = new HashMap();

        if (msUsed < 0) {
            resultMap.put("ms", String.valueOf(msUsed));
            return resultMap;
        }
        if (msUsed < 1000) {
            resultMap.put("ms", String.valueOf(msUsed));
            return resultMap;
        }
        //长于1秒的过程，毫秒不计
        msUsed /= 1000;
        if (msUsed < 60) {
            resultMap.put("sec", String.valueOf(msUsed));
            return resultMap;
        }
        if (msUsed < 3600) {
            long nMinute = msUsed / 60;
            long nSecond = msUsed % 60;

            resultMap.put("min", String.valueOf(nMinute));
            resultMap.put("sec", String.valueOf(nSecond));
            return resultMap;
        }
        //3600 * 24 = 86400
        if (msUsed < 86400) {
            long nHour = msUsed / 3600;
            long nMinute = (msUsed - nHour * 3600) / 60;
            long nSecond = (msUsed - nHour * 3600) % 60;

            resultMap.put("hour", String.valueOf(nHour));
            resultMap.put("min", String.valueOf(nMinute));
            resultMap.put("sec", String.valueOf(nSecond));
            return resultMap;
        }

        long nDay = msUsed / 86400;
        long nHour = (msUsed - nDay * 86400) / 3600;
        long nMinute = (msUsed - nDay * 86400 - nHour * 3600) / 60;
        long nSecond = (msUsed - nDay * 86400 - nHour * 3600) % 60;

        resultMap.put("day", String.valueOf(nDay));
        resultMap.put("hour", String.valueOf(nHour));
        resultMap.put("min", String.valueOf(nMinute));
        resultMap.put("sec", String.valueOf(nSecond));
        return resultMap;
    }

    /**
     * 取本周一.
     *
     * @return 本周一
     */
    public static Calendar getThisMonday() {
        return getThatMonday(Calendar.getInstance());
    }

    /**
     * 获取cal所在周的周一.
     *
     * @param cal 给定日期
     * @return cal所在周的周一
     */
    public static Calendar getThatMonday(Calendar cal) {
        int n = cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        cal.add(Calendar.DATE, n);
        return cal;
    }

    /**
     * 取本周日.
     *
     * @return 本周日
     */
    public static Calendar getThisSunday() {
        return getThatSunday(Calendar.getInstance());
    }

    /**
     * 获取cal所在周的周日.
     *
     * @param cal 给定日期
     * @return cal所在周的周日
     */
    public static Calendar getThatSunday(Calendar cal) {
        int n = (Calendar.SUNDAY + 7) - cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, n);
        return cal;
    }

    /**
     * 获取两个日期相差的天数.
     *
     * @return 两个日期相差的天数.
     */
    public static int minus(Calendar cal1, Calendar cal2) {
        if (cal1.after(cal2)) {
            int nBase = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 365;
            return nBase + cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            return minus(cal2, cal1);
        }
    }

    /**
     * 从Date对象得到Calendar对象. <BR>
     * JDK提供了Calendar.getTime()方法, 可从Calendar对象得到Date对象, 但没有提供从Date对象得到Calendar对象的方法.
     *
     * @param date 给定的Date对象
     * @return 得到的Calendar对象. 如果date参数为null, 则得到表示当前时间的Calendar对象.
     */
    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        return cal;
    }

    /**
     * 完成日期串到日期对象的转换. <BR>
     *
     * @param dateString 日期字符串
     * @param dateFormat 日期格式
     * @return date 日期对象
     */
    public static Date stringToDate(String dateString, String dateFormat) {
        if ("".equals(dateString) || dateString == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(dateFormat).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取和指定cal对象相隔指定天数的cal对象. 大于0表示之后, 小于0表之前.
     *
     * @param cal         指定cal对象
     * @param relativeDay 相隔指定天数
     * @return cal对象
     */
    public static Calendar getCalendar(Calendar cal, int relativeDay) {
        cal.add(Calendar.DATE, relativeDay);
        return cal;
    }

    /**
     * 获取和当天相隔指定天数的Date对象. 大于0表示之后, 小于0表之前.
     *
     * @param relativeDay 相隔指定天数
     * @return Date对象
     * @see #getCalendar(Calendar, int)
     */
    public static Date getDate(int relativeDay) {
        return getCalendar(Calendar.getInstance(), relativeDay).getTime();
    }

    public static String date2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String date2String(Date date) {
        return date2String(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转换成string,生成文件时加的后缀
     *
     * @param date
     * @return
     */
    public static String getYearMonthDate(Date date) {
        if (date != null) {
            SimpleDateFormat ft = null;
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            date = cl.getTime();
            ft = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
            String dateTime = ft.format(date);
            return dateTime;
        } else {
            return null;
        }
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    public static Map getDistanceTimetoMap(String str1, String str2) {
        Map resultMap = new HashMap();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resultMap.put("day", day);
        resultMap.put("hour", hour);
        resultMap.put("min", min);
        resultMap.put("sec", sec);
        return resultMap;
    }

    /**
     * 根据日期获得所在周的日期
     *
     * @param mdate
     * @return
     */
    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a - 1, fdate);
        }
        return list;
    }

    /**
     * 根据当前日期获取本月日期
     *
     * @param date
     * @return
     */
    public static List<Date> getAllTheDateOftheMonth(Date date) {
        List<Date> list = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            list.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个月的开始时间
    public static Date getMonthStartTime(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个月的结束时间
    public static Date getMonthEndTime(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取本年的开始时间
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    //获取本年的结束时间
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //根据年月获取当月最小日期
    public static String getFisrtDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        if(month==0){
            cal.set(Calendar.MONTH, 0);
        }else {
            cal.set(Calendar.MONTH, month-1);
        }

        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());

        return firstDayOfMonth;
    }
    //根据年月获取当月最大日期
    public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        if(month==0){
            cal.set(Calendar.MONTH, 11);
        }else {
            cal.set(Calendar.MONTH, month-1);
        }
        //获取某月最小天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date currentDate = new Date();
//
//        List<Date> week = getAllTheDateOftheMonth(currentDate);
//        for (Date date : week) {
//            System.out.println(sdf.format(getDayStartTime(date)));
//            System.out.println(sdf.format(getDayEndTime(date)));
//        }
//
//        List<Date> days = getAllTheDateOftheMonth(currentDate);
//        System.out.println("今天的日期: " + sdf.format(currentDate));
//        for (Date date : days) {
//            System.out.println(sdf.format(date));
//        }

//        System.out.println(sdf.format(getBeginDayOfYear()));
//        System.out.println(sdf.format(getEndDayOfYear()));
        for (int i = 1; i <= 12; i++) {
            System.out.println(sdf.format(getMonthStartTime(i)));
            System.out.println(sdf.format(getMonthEndTime(i)));
        }

    }
}