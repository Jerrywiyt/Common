package com.lujunyu.juc.thread;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {

  @Test
  public void test() {
    long l = System.currentTimeMillis();
    ForkJoinPool forkJoinPool = new ForkJoinPool(); // 实现ForkJoin 就必须有ForkJoinPool的支持
    ForkJoinTask<Long> task = new ForkJoinWork(0L, 1000000000L); // 参数为起始值与结束值
    Long invoke = forkJoinPool.invoke(task);
    long l1 = System.currentTimeMillis();
    System.out.println("invoke = " + invoke + "  time: " + (l1 - l));
  }

  @Test
  public void test2() {
    Long x = 0L;
    Long y = 1000000000L;
    long l = System.currentTimeMillis();
    for (Long i = 0L; i <= y; i++) {
      x += i;
    }
    long l1 = System.currentTimeMillis();
    System.out.println("invoke = " + x + "  time: " + (l1 - l));
    // invoke = -5340232216128654848  time: 160939
  }

  @Test
  public void test3() {
    long l = System.currentTimeMillis();
    long reduce = LongStream.rangeClosed(0, 1000000000L).parallel().reduce(0, Long::sum);
    long l1 = System.currentTimeMillis();
    System.out.println("invoke = " + reduce + "  time: " + (l1 - l));
    // invoke = -5340232216128654848  time: 15531
  }
}
