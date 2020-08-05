package com.lujunyu.algorithm.leetcode;

public class Question415 {

  public static void main(String[] args) {
    String s = new Solution().addStrings("119", "111");
    System.out.println(s);
  }

  static class Solution {
    // 48 ~ 57
    public String addStrings(String num1, String num2) {
      StringBuilder res = new StringBuilder();
      int s = 0;
      int index = 0;
      while (index < Math.max(num1.length(), num2.length())) {
        int cur = take(num1, index) + take(num2, index) - '0' + s;
        char cc = (char) cur;
        if (cur > 57) {
          cc = (char) (cur - 10);
          s = 1;
        } else {
          s = 0;
        }
        res.insert(0, cc);
        index++;
      }
      if (s == 1) {
        res.insert(0, '1');
      }
      return res.toString();
    }

    private char take(String s, int index) {
      if (index >= s.length()) {
        return '0';
      }
      return s.charAt(s.length() - index - 1);
    }
  }
}
