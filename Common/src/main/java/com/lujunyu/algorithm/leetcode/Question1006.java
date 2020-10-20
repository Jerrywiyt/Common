package com.lujunyu.algorithm.leetcode;

public class Question1006 {
  public static void main(String[] args) {
    //
    int clumsy = new Solution().clumsy(10);
    System.out.println(clumsy);
  }

  static class Solution {
    public int clumsy(int N) {
      int res = 0;
      int a = N - 3;
      while (a > 0) {
        res += a;
        a = a - 4;
      }
      a = N;
      while (a - 2 > 0) {
        int temp = a * (a - 1) / (a - 2);
        if (a == N) {
          res += temp;
        } else {
          res -= temp;
        }
        a = a - 4;
      }
      int temp = 0;
      if (a == 2) {
        temp = 2;
      }
      if (a == 1) {
        temp = 1;
      }
      if (a == N) {
        res = temp;
      } else {
        res -= temp;
      }
      return res;
    }
  }
}
