/**
 * 用一句话描述该文件做什么.
 *
 * @title DateUtil.java
 * @package com.sinsoft.android.util
 * @author shimiso
 * @update 2012-6-26 上午9:57:56
 */
package chui.swsd.com.cchui.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import chui.swsd.com.cchui.R;

/**
 * 日期操作工具类.
 *
 * @author shimiso
 */

public class DateUtil {

  private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static Date str2Date(String str) {
    return str2Date(str, null);
  }

  public static Date str2Date(String str, String format) {
    if (str == null || str.length() == 0) {
      return null;
    }
    if (format == null || format.length() == 0) {
      format = FORMAT;
    }
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      date = sdf.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Calendar str2Calendar(String str) {
    return str2Calendar(str, null);
  }

  public static Calendar str2Calendar(String str, String format) {

    Date date = str2Date(str, format);
    if (date == null) {
      return null;
    }
    Calendar c = Calendar.getInstance();
    c.setTime(date);

    return c;
  }

  public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
    return date2Str(c, null);
  }

  public static String date2Str(Calendar c, String format) {
    if (c == null) {
      return null;
    }
    return date2Str(c.getTime(), format);
  }

  public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
    return date2Str(d, null);
  }

  public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
    if (d == null) {
      return null;
    }
    if (format == null || format.length() == 0) {
      format = FORMAT;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    String s = sdf.format(d);
    return s;
  }

  public static String getCurDateStr() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    return c.get(Calendar.YEAR)
        + "-"
        + (c.get(Calendar.MONTH) + 1)
        + "-"
        + c.get(Calendar.DAY_OF_MONTH)
        + "-"
        + c.get(Calendar.HOUR_OF_DAY)
        + ":"
        + c.get(Calendar.MINUTE)
        + ":"
        + c.get(Calendar.SECOND);
  }

  /**
   * 获得当前日期的字符串格式
   */
  public static String getCurDateStr(String format) {
    Calendar c = Calendar.getInstance();
    return date2Str(c, format);
  }

  // 格式到秒
  public static String getMillon(long time) {

    return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);
  }

  // 格式到天
  public static String getDay(long time) {

    return new SimpleDateFormat("yyyy-MM-dd").format(time);
  }
  // 格式到天
  public static String getTime(double time) {

    return new SimpleDateFormat("HH:mm").format(time);
  }
  // 格式到毫秒
  public static String getSMillon(long time) {

    return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);
  }

  public static String getDateDistanceWithNow(String date) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date data1 = df.parse(date);
      Date date2 = new Date();
      long diff = date2.getTime() - data1.getTime();
      long mins = diff / (1000 * 60);
      long lastmins = mins % 60;
      long hours = mins / 60;
      long days = hours / 24;
      if (hours == 0) {
        if (lastmins == 0) {
          return "刚刚";
        } else {
          return lastmins + "分钟前";
        }
      } else if (hours < 24) {
        return hours + "小时前";
      } else if (hours < 48) {
        return days + "天前";
      } else {
        return df2.format(data1);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return "";
  }



  public static String getDayOfWeekChZnString(Date date) {
    if (date == null) {
      return "";
    }

    Calendar c = Calendar.getInstance(Locale.CHINA);
    c.setTime(date); // yourdate is a object of type Date

    final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

    StringBuilder dayOfWeekSb = new StringBuilder("周");

    switch (dayOfWeek) {
      case 1:
        dayOfWeekSb.append("一");
        break;
      case 2:
        dayOfWeekSb.append("二");
        break;
      case 3:
        dayOfWeekSb.append("三");
        break;
      case 4:
        dayOfWeekSb.append("四");
        break;
      case 5:
        dayOfWeekSb.append("五");
        break;
      case 6:
        dayOfWeekSb.append("六");
        break;
      case 7:
        dayOfWeekSb.append("日");
        break;
    }

    return dayOfWeekSb.toString();
  }

  public static String curChinaFormatDate(){
    return new android.text.format.DateFormat().format("yyyy-MM-dd HH:mm:ss", Calendar.getInstance(Locale.CHINA)).toString();
  }


  public static String getTime(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return format.format(date);
  }
  public static String getTime1(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH点");
    return format.format(date);
  }
  public static String getTime2(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    return format.format(date);
  }
  public static String getTime3(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    return format.format(date);
  }
  public static String getTime4(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy-M");
    return format.format(date);
  }
  public static String getTime1(){
    long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
    SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
    Date d1=new Date(time);
    String t1=format.format(d1);
    Log.e("msg", t1);
    return t1;
  }
  public static String getTime5(){
    long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
    SimpleDateFormat format=new SimpleDateFormat("yyyy-M");
    Date d1=new Date(time);
    String t1=format.format(d1);
    return t1;
  }
  public static String beforeTime(){
    Calendar c = Calendar.getInstance();//
    int mYear = c.get(Calendar.YEAR); // 获取当前年份
    int mMonth = c.get(Calendar.MONTH);// 获取当前月份前一个月
    return mYear+"-"+mMonth;
  }
  public static Long getDateToSecond(String dateStr){

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = dateFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date.getTime();

  }
  public static Long getDateToSecond2(String dateStr){

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH点");
    Date date = null;
    try {
      date = dateFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date.getTime();

  }
  public static Long getDateToSecond3(String dateStr){

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    Date date = null;
    try {
      date = dateFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date.getTime();
  }
  public static String getTime4(){
    long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
    Date d1=new Date(time);
    String t1=format.format(d1);
    return t1;
  }
  public static String getDayTime(){
    long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
    SimpleDateFormat format=new SimpleDateFormat("dd");
    Date d1=new Date(time);
    String t1=format.format(d1);
    return t1;
  }
  public static int getDayHour(){
    long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
    SimpleDateFormat format=new SimpleDateFormat("HH");
    Date d1=new Date(time);
    String t1=format.format(d1);
    return Integer.parseInt(t1);
  }
  public static long getCurrent(){
    return System.currentTimeMillis();

  }
  public static long getMonthTime(){
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    return cal.getTimeInMillis();
  }
  public static String getTime5(long time){
    Date d = new Date(time);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    return sdf.format(d);
  }
  public static long getTime6(String time){
    long millionSeconds = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      millionSeconds = sdf.parse(time).getTime();//毫秒
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return millionSeconds;
  }
  public static Long getDateToSecond4(String dateStr){
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Date date = null;
    try {
      date = dateFormat.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date.getTime();
  }

  public static Long getDateToSecond5(String dateStr){
    String[] strs = dateStr.split(":");
    long hour =  Long.parseLong(strs[0])*60*60*1000;
    long min = Long.parseLong(strs[1])*60*1000;
    return hour+min;
  }
  public static String getHour(String time){
    String timestr = null;
    long t = Long.parseLong(time);
    long hour = Math.abs(t/60/60/1000);
    long minute = (t%(60*60*1000))/(60*1000);
    if(minute == 0){
      timestr = hour+":"+"00";
    }else{
      timestr = hour+":"+minute;
    }

    return timestr;
  }
  public static String getHour2(Date date) {
   // long ms = Long.parseLong(time);
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    //formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    String hms = formatter.format(date);
    System.out.println(hms);
    return hms;
  }
  public static String getHour3(String time){
    long t = Long.parseLong(time);
    long hour = Math.abs(t/60/60/1000);
    long minute = (t%(60*60*1000))/(60*1000);
    if(minute == 0){
      minute = 00;
    }
    if(hour>0) {
      return hour + "小时" + minute + "分钟";
    }else{
      return minute + "分钟";
    }
  }
  public static String chaTime(String time){
    long startTime = getTime6(time+":00");
    long currentTime = System.currentTimeMillis();
    long cha = startTime-currentTime;
    if(cha > 0) {
      String time2 = getHour3(cha + "");
      return time2;
    }else{
      return "会议时间已到";
    }

  }
}