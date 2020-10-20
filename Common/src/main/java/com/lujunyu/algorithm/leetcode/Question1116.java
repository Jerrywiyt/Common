package com.lujunyu.algorithm.leetcode;

import java.util.concurrent.Semaphore;

public class Question1116 {

  private int n;

  public Question1116(int n) {
    this.n = n;
  }

  private Semaphore semaphore1 = new Semaphore(1);
  private Semaphore semaphore2 = new Semaphore(0);
  private Semaphore semaphore3 = new Semaphore(0);
  private volatile int i = 0;

  // printNumber.accept(x) outputs "x", where x is an integer.
  public void zero(IntConsumer printNumber) throws InterruptedException {
    semaphore1.acquire();
    if (i >= 2 * n) {
      return;
    }
    printNumber.accept(0);
    if (++i % 4 == 0) {
      semaphore3.release();
    } else {
      semaphore2.release();
    }
  }

  public void even(IntConsumer printNumber) throws InterruptedException {
    semaphore2.acquire();
    printNumber.accept(++i / 2);
    semaphore1.release();
  }

  public void odd(IntConsumer printNumber) throws InterruptedException {
    semaphore3.acquire();
    printNumber.accept(++i / 2);
    semaphore1.release();
  }

  private static class IntConsumer {
    public void accept(int a) {
      System.out.println(a);
    }
  }

  public static void main(String args[]) {
    IntConsumer intConsumer = new IntConsumer();
    Question1116 question1116 = new Question1116(10);
    new Thread(
            () -> {
              try {
                while (true) {
                  question1116.zero(intConsumer);
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
    new Thread(
            () -> {
              try {
                while (true) {
                  question1116.even(intConsumer);
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
    new Thread(
            () -> {
              try {
                while (true) {
                  question1116.odd(intConsumer);
                }
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            })
        .start();
  }
}
