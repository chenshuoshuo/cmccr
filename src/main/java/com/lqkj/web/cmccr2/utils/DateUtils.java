package com.lqkj.web.cmccr2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName
 * @Description 时间工具类
 * @Author ts
 * @Date 2019/6/21 10:23
 * @Version 1.0
 **/
public class DateUtils {

    private static DateUtils dateUtils;

    private DateUtils(){};
    /**
     *
     * 获得一个单一实例
     * @return MD5
     */
    public synchronized static DateUtils getDefaultInstance(){
        if(dateUtils==null){
            dateUtils = new DateUtils();
        }
        return dateUtils;
    }

    /**
     * 获取日期
     * @param date
     * @param formatStr
     * @return
     */
    public String formatDate(Date date,String formatStr){
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 获取日期
     * yyyy-MM-dd格式
     * @return
     */
    public String getDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String day = sdf.format(calendar.getTime());
        return  day;
    }
    /**
     * 获取时间
     * hh:ss格式
     * @return
     */
    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        String time = sdf.format(calendar.getTime());
        return  time;
    }
    /**
     * 获取日期
     * MM-dd格式
     * @return
     */
    public String getToday(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Calendar calendar = Calendar.getInstance();
        String day = sdf.format(calendar.getTime());
        return  day;
    }
    /**
     * 获取今天的开始时间与结束时间
     * @return
     */
    public String[] getTodayArray(){
        String[] timeArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        timeArray[1] = sdf.format(calendar.getTime()) + " 00:00:00";
        return timeArray;
    }
    /**
     * 获取当周的开始时间与结束时间
     * @return
     */
    public String[] getThisWeekArray(){
        String[] timeArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(day_of_week == 0){
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE,7 -day_of_week+1);
        timeArray[1] = sdf.format(calendar.getTime()) + " 00:00:00";
        calendar.add(Calendar.DATE, -7);
        timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";
        return timeArray;
    }

    /**
     * 获取当月的开始时间与结束时间
     * @return
     */
    public String[] getThisMonthArray(){
        String[] timeArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH) -1;
        calendar.add(Calendar.DATE, -day_of_month);
        timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";
        calendar.add(Calendar.MONTH,1);
        timeArray[1] = sdf.format(calendar.getTime()) + " 00:00:00";
        return timeArray;
    }
    /**
     * 获取当年的开始时间与结束时间
     * @return
     */
    public String[] getThisYearArray() throws ParseException{
        String[] timeArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";

        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        timeArray[1] = sdf.format(calendar.getTime()) + " 23:59:59";
        return timeArray;
    }

    /**
     * 根据指定开始日期、结束日期
     * 获取开始时间与结束时间
     * @return
     */
    public String[] getArrayWithDateRange(String startDate, String endDate) throws ParseException {
        String[] timeArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(sdf.parse(startDate));
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";


        calendar.setTime(sdf.parse(endDate));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        timeArray[1] = sdf.format(calendar.getTime()) + " 00:00:00";

        return timeArray;
    }

    /**
     * 根据指定日期、时间跨度
     * 获取开始时间与结束时间
     * @return
     */
    public String[] getArrayWithDateAndRange(String dateString, Integer range) throws ParseException {
        String[] timeArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(dateString));

        if(range > 0){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";

            calendar.add(Calendar.DAY_OF_YEAR, range);
            timeArray[1] = sdf.format(calendar.getTime()) + " 00:00:00";
        } else{
            timeArray[1] = sdf.format(calendar.getTime()) + " 00:00:00";

            calendar.add(Calendar.DAY_OF_YEAR, range);
            timeArray[0] = sdf.format(calendar.getTime()) + " 00:00:00";
        }

        return timeArray;
    }

}
