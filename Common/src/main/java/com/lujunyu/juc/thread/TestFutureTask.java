package com.lujunyu.juc.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.junit.Test;

public class TestFutureTask {

  @Test
  public void testCancel() throws ExecutionException, InterruptedException {
    Future<?> future =
        Executors.newFixedThreadPool(1)
            .submit(
                () -> {
                  LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
                  System.out.println("end");
                  System.out.println(Thread.currentThread().isInterrupted());
                });

    new Thread(
            () -> {
              try {
                future.get();
              } catch (InterruptedException e) {
                System.out.println("get thread1 interrupt");
              } catch (ExecutionException e) {
                e.printStackTrace();
              }
            })
        .start();
    new Thread(
            () -> {
              try {
                future.get();
              } catch (InterruptedException e) {
                System.out.println("get thread2 interrupt");
              } catch (ExecutionException e) {
                e.printStackTrace();
              }
            })
        .start();

    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));

    future.cancel(false);
    // 该模式会给运行线程设置中断信号。
    // future.cancel(true);

    LockSupport.park();
  }
}
