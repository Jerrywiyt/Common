package com.lujunyu.juc.utils;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestCountDownLatch {
  @Test
  public void test() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);

    new Thread() {
      @Override
      public void run() {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        latch.countDown();
      }
    }.start();

    latch.await();
  }

  @Test
  public void testMoreWaiter() {
    CountDownLatch latch = new CountDownLatch(1);
    new Thread() {
      @Override
      public void run() {
        try {
          latch.await();
          System.out.println("线程1执行了");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();
    new Thread() {
      @Override
      public void run() {
        try {
          latch.await();
          System.out.println("线程2执行了");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();

    System.out.println("等待中。");
    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
    latch.countDown();
  }
}
