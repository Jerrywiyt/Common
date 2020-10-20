package com.lujunyu.juc.utils;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestCyclicBarrier {
  @Test
  public void test() {
    int n = 10;
    Runnable runnable = () -> System.out.println("end");
    CyclicBarrier cyclicBarrier = new CyclicBarrier(n, runnable);
    for (int i = 0; i < n; i++) {
      new Thread(
              () -> {
                try {
                  LockSupport.parkNanos(
                      TimeUnit.SECONDS.toNanos(ThreadLocalRandom.current().nextInt(3)));
                  cyclicBarrier.await();
                  System.out.println(Thread.currentThread().getName() + " end");
                } catch (InterruptedException | BrokenBarrierException e) {
                  e.printStackTrace();
                }
              })
          .start();
    }
    LockSupport.park();
  }
}
