package com.lujunyu.algorithm.leetcode;

public class Question1386 {
  public static void main(String[] args) {

    System.out.println(new Solution().maxNumberOfFamilies(3,new int[][] {{1,2},{1,3},{1,8},{2,6},{3,1},{3,10}}));
  }

  static class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
      int[] p = new int[n + 1];

      for (int[] seat : reservedSeats) {
        p[seat[0]] = p[seat[0]] | (1 << seat[1]);
      }
      int s1 = 0xF << 2;
      int s2 = 0xF << 6;
      int s3 = 0xF << 4;

      int res = 0;
      for (int i = 1; i <= n; i++) {
        int pos = p[i];
        if (pos == 0) {
          res += 2;
        } else {
          int a = pos ^ s1;
          if ((pos & s1) == 0 || (pos & s2) == 0) {
            res += 1;
            if ((pos & s1) == 0 && (pos & s2) == 0) {
              res += 1;
            }
          } else {
            if ((pos & s3) == 0) {
              res += 1;
            }
          }
        }
      }
      return res;
    }
  }
}
