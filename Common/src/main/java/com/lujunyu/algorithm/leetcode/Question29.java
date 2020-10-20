package com.lujunyu.algorithm.leetcode;

public class Question29 {
  public static void main(String[] args) {
    System.out.println(new Question29().new Solution().divide(7, -3));
  }

  class Solution {
    public int divide(int dividend, int divisor) {
      long a = dividend;
      long b = divisor;
      a = Math.abs(a);
      b = Math.abs(b);

      long temp = b;

      long cnt = 0;

      // 被除数向左移，找到临界点。
      while (temp << 1 < a) {
        cnt++;
        temp = temp << 1;
      }
      long res = 1 << cnt;
      // 通过加法，不断的向结果逼近。
      while (temp + b <= a) {
        temp = temp + b;
        res++;
      }

      if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) {
        return res > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) res;
      } else {
        return res > ((long) Integer.MAX_VALUE + 1) ? Integer.MIN_VALUE : (int) -res;
      }
    }
  }
}
