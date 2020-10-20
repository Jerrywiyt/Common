package com.lujunyu.juc.thread;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class TestThreadPool {
  /** 此方法证明， t.interrupt方法能够唤醒处于park状态的线程，但不抛出异常，作用相当于调用了unpark方法。 */
  @Test
  public void test() {
    Thread t =
        new Thread(
            () -> {
              LockSupport.park();
              System.out.println("end");
              // 此方法可以清楚中断标识。
              System.out.println(Thread.interrupted());
              LockSupport.park();
              System.out.println("end");
            });
    t.start();
    t.interrupt();

    LockSupport.park();
  }

  @Test
  public void testFutureTask() throws ExecutionException, InterruptedException {
    Future<String> future =
        Executors.newFixedThreadPool(1)
            .submit(
                () -> {
                  LockSupport.park();
                  return "";
                });

    future.get();
  }

  @Test
  public void testScheduledThreadPoolExecutor() {
    ScheduledExecutorService scheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(
        new Runnable() {
          @Override
          public void run() {}
        },
        0L,
        60L,
        TimeUnit.SECONDS);
  }

  @Test
  public void testThreadPool() {
    Executor executor =
        new ThreadPoolExecutor(1, 2, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    executor.execute(new MyRunner());
    executor.execute(() -> System.out.println("start"));
    executor.execute(new MyRunner());
    executor.execute(new MyRunner());
  }

  private class MyRunner implements Runnable {

    @Override
    public void run() {
      LockSupport.park();
    }
  }
}
