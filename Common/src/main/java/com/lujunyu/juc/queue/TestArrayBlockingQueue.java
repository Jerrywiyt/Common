package com.lujunyu.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class TestArrayBlockingQueue {
  private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

  public static void main(String[] args) {

    new Thread() {
      @Override
      public void run() {
        try {
          while (true) {
            System.out.println(queue.take());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }.start();

    for (int i = 0; i < 10; i++) {
      try {
        queue.put("" + i);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
