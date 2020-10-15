package com.lujunyu.algorithm.leetcode;

import java.util.PriorityQueue;

public class Question264 {

  public static void main(String[] args) {
    new Solution().nthUglyNumber(1600);

    System.out.println(859963392 * 3);
  }

  static class Solution {
    public int nthUglyNumber(int n) {
      PriorityQueue<Long> heap = new PriorityQueue<>();

      heap.add(1L);

      long num = 0;
      long lastUglyNumber = 0;
      while (num < n) {
        if (num == 1499) {
          System.out.println();
        }
        long curUglyNumber = heap.poll();
        if (curUglyNumber != lastUglyNumber) {
          num++;
          lastUglyNumber = curUglyNumber;
          if (2 * curUglyNumber <= Integer.MAX_VALUE) {
            heap.add(2 * curUglyNumber);
          }
          if (3 * curUglyNumber <= Integer.MAX_VALUE) {
            heap.add(3 * curUglyNumber);
          }
          if (5 * curUglyNumber <= Integer.MAX_VALUE) {
            heap.add(5 * curUglyNumber);
          }
        }
      }
      return (int) lastUglyNumber;
    }
  }
}
