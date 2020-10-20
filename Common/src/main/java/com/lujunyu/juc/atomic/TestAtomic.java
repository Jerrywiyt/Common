package com.lujunyu.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class TestAtomic {
  @Test
  public void testAtomicLong() {
    AtomicLong atomicLong = new AtomicLong();
    System.out.println(atomicLong.addAndGet(1));
  }

  @Test
  public void testLongAdder() {
    LongAdder longAdder = new LongAdder();
    longAdder.add(1);
    System.out.println(longAdder.longValue());
  }

  @Test
  public void testLongAccumulator() {
    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x * y, 3);
    longAccumulator.accumulate(10);
    System.out.println(longAccumulator.get());
  }
}
