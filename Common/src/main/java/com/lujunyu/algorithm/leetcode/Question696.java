package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Question696 {
  public static void main(String[] args) {
    System.out.println(new Solution().countBinarySubstrings("00110"));
  }

  static class Solution {
    public int countBinarySubstrings(String s) {
      char curChar = '0';
      int last = 0;
      int cur = 0;
      int res = 0;
      for (char c : s.toCharArray()) {
        if (c == curChar) {
          cur++;
        } else {
          curChar = c;
          last = cur;
          cur = 1;
        }
        if (last > 0) {
          last--;
          res++;
        }
      }
      return res;
    }
  }
}
