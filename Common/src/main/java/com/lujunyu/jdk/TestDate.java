package com.lujunyu.jdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.Date;
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
    ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
    System.out.println(Duration.between(now, oneDayAgo).getSeconds());
  }

  /** LocalDate用于表示不区分时区的日期，该类不保存时间部分，比如用于保存生日。 */
  @Test
  public void testLocalDate() {
    LocalDate now = LocalDate.now();
    LocalDate oneDayLater = now.plusDays(1);
    LocalDate.parse("2020-01-01");
    LocalDate.of(2010, 10, 1);
    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    LocalDateTime.now().isAfter(LocalDateTime.now());
  }

  /** LocalDateTime用于表示不区分时区的日期加时间 */
  @Test
  public void testLocalDateTime() {
    LocalDateTime.of(2020, 1, 1, 1, 1, 1);
    LocalDateTime.now();
  }

  /** ZonedDateTime用于表示区分时区的日期和时间。 */
  @Test
  public void testZonedDateTime() {
    System.out.println(ZonedDateTime.now());
    System.out.println(ZonedDateTime.now(ZoneId.of("America/Chicago")));

    Clock clock = Clock.systemDefaultZone();
    Clock clock1 = Clock.system(ZoneId.of("Europe/Paris"));

    System.out.println(LocalDateTime.now(clock));
    System.out.println(LocalDateTime.now(clock1));

    LocalDateTime dateTime = LocalDateTime.of(2020, 3, 26, 0, 0, 0);

    Instant instant = dateTime.toInstant(ZoneOffset.UTC);

    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"));

    System.out.println(zonedDateTime.toLocalDateTime());
  }

  @Test
  public void testFormat() {
    DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    LocalDate parse = LocalDate.parse("1992-03-05", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    System.out.println(parse);
  }

  @Test
  public void testClock() {
    Clock clock = Clock.systemUTC();
    ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    System.out.println(LocalDateTime.now(clock));
    System.out.println(LocalDateTime.now(clock.withZone(zoneId)));

    System.out.println(Instant.now(clock));
    System.out.println(Instant.now(clock.withZone(zoneId)));

    System.out.println(
        LocalDate.now(clock.withZone(zoneId)).atStartOfDay(clock.getZone()).toInstant());
  }

  /** 不同时区的转换。 */
  @Test
  public void testTransferTimeZone() {
    // 输入时间，统一转为UTC时间进行存储：比如用户在东八区时区进行操作，保存时间。
    LocalDateTime dateTime = LocalDateTime.of(2020, 3, 26, 8, 0, 0);

    // 将输入时间转换为UTC时间。
    Instant utc = dateTime.toInstant(ZoneOffset.of("+8"));

    // 转换为LocalDateTime。
    LocalDateTime localDateTime = LocalDateTime.ofInstant(utc, ZoneOffset.UTC);
    System.out.println(localDateTime);

    // 转化为其它时区。
    LocalDateTime localDateTime1 = LocalDateTime.ofInstant(utc, ZoneId.of("America/Phoenix"));
    System.out.println(localDateTime1);
  }

  @Test
  public void testTransferWithDate() throws ParseException {
    // date 是有默认时区的，进行转换的时候需要注意。
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-10");
    Instant instant = date.toInstant();
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    System.out.println(localDateTime);

    System.out.println(new Date(0));
  }

  /** 时间戳都是以UTC时间开始的。Date实际使用了默认的时区，date#getTime返回的是以UTC时间为基准的时间戳。 */
  @Test
  public void testTimestamp() {
    System.out.println(new Date().getTime());
    System.out.println(System.currentTimeMillis());
    System.out.println(Instant.now().toEpochMilli());
  }
}
