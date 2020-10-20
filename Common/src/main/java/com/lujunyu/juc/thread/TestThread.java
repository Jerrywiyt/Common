package com.lujunyu.juc.thread;

import org.junit.Test;

public class TestThread {
  @Test
  public void test() throws InterruptedException {
    Thread t =
        new Thread() {
          @Override
          public void run() {
            try {
              Thread.sleep(10000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        };

    t.join();
  }
}
