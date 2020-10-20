package com.lujunyu.currentLimiting.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestCase {
  /** 证明guava的限流器是令牌桶实现 */
  @Test
  public void test1() throws InterruptedException {
    RateLimiter rateLimiter = RateLimiter.create(2);
    for (int i = 0; i < 4; i++) {
      System.out.println(rateLimiter.acquire());
    }

    Thread.sleep(5000);

    for (int i = 0; i < 4; i++) {
      System.out.println(rateLimiter.acquire());
    }
  }

  /** */
  @Test
  public void test2() throws InterruptedException {
    RateLimiter rateLimiter = RateLimiter.create(2, 2, TimeUnit.SECONDS);
    for (int i = 0; i < 6; i++) {
      System.out.println(rateLimiter.acquire());
    }

    Thread.sleep(3000);

    for (int i = 0; i < 6; i++) {
      System.out.println(rateLimiter.acquire());
    }
  }
}
