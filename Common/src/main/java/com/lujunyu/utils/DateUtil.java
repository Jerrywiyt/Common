package com.lujunyu.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

public final class DateUtil {
  public static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

  private DateUtil() {}
  /**
   * 判断两个日期是否是同一天。
   *
   * @param date1
   * @param date2
   * @return true if they represent the same day
   * @throws IllegalArgumentException if either date is <code>null</code>
   */
  public static boolean isSameDay(Date date1, Date date2) {
    return DateUtils.isSameDay(date1, date2);
  }
  /**
   * 判断两个日期是否是同一时刻。
   *
   * @param date1
   * @param date2
   * @return true if they represent the same day
   * @throws IllegalArgumentException if either date is <code>null</code>
   */
  public static boolean isSameInstant(Date date1, Date date2) {
    return DateUtils.isSameInstant(date1, date2);
  }

  public static String format(Date date, String pattern) {
    DateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }

  public static void main(String[] args) {
    Calendar c = Calendar.getInstance();
    c.set(2015, 0, 30);
    Date date = c.getTime();
    Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println(format.format(date));
    FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    dateFormat.format(date);
  }
}
