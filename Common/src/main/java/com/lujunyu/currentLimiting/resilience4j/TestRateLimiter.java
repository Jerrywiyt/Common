package com.lujunyu.currentLimiting.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class TestRateLimiter {
  @Test
  public void test() throws InterruptedException {
    RateLimiter rateLimiter =
        RateLimiter.of(
            "test",
            RateLimiterConfig.custom()
                .limitForPeriod(2)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(1500))
                .build());
    rateLimiter.getEventPublisher().onEvent(event -> log.info(event.toString()));
    // monitor
    RateLimiter.Metrics metrics = rateLimiter.getMetrics();
    int numberOfWaitingThreads = metrics.getNumberOfWaitingThreads();
    int availablePermissions = metrics.getAvailablePermissions();

    Thread t =
        new Thread() {
          @Override
          public void run() {
            while (true) {
              System.out.println(
                  Try.of(RateLimiter.decorateCheckedSupplier(rateLimiter, new Service()::get))
                      .recover(Throwable::getMessage)
                      .get());
            }
          }
        };
    t.start();
    t.join();
  }

  private static class Service {
    public String get() {
      return "hello world";
    }
  }

  private static class MyRunner implements Runnable {
    @Override
    public void run() {
      System.out.println("hello world");
    }
  }
}
