package com.lujunyu.jdk;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/** 1；8新增了java.time.* 类，用于处理时间。了解一下。 */
public class TestDate {

  /** Instant表示时间线上的一个点。参考点是Java纪元（epoch），即1970-01-01 采用一个long保存秒，一个int保存相对于偏移量的纳秒，这样做的目的是时间范围更广。 */
  @Test
  public void testInstant() {
    Instant now = Instant.now();
    Instant parse = Instant.parse("2007-12-03T10:15:30.00Z");
    Instant oneHourLater = now.plusSeconds(TimeUnit.HOURS.toSeconds(1));
    Instant oneDayAgo = now.minus(1, ChronoUnit.DAYS);
    // eq System.currentTimeMills();
    long cur = now.toEpochMilli();
    // the day of begin
    now.truncatedTo(ChronoUnit.DAYS);
    System.out.println(Duration.between(now, oneDayAgo).getSeconds());
  }

  /** LocalDate用于表示不区分时区的日期，该类不保存时间部分，比如用于保存生日。 */
  public void testLocalDate() {
    LocalDate now = LocalDate.now();
    LocalDate oneDayLater = now.plusDays(1);
    LocalDate.parse("2020-01-01");
    LocalDate.of(2010,10,1);
  }

  /**
   * LocalDateTime用于表示不区分时区的日期加时间
   */
  @Test
  public void testLocalDateTime(){
    LocalDateTime.of(2020,1,1,1,1,1);
  }

  /**
   * ZonedDateTime用于表示区分时区的日期和时间。
   */
  @Test
  public void testZonedDateTime(){
    ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
  }

  @Test
  public void testFormat(){
    DateTimeFormatter.ofPattern("yyyy-mm-dd").format(LocalDateTime.now());
  }

  @Test
  public void testClock(){
    Clock clock = Clock.systemUTC();
    System.out.println(clock.toString());
    System.out.println(LocalDate.now(clock).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
  }
}
