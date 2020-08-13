package com.lujunyu.algorithm.leetcode;

public class Question43 {
  public static void main(String[] args) {
    System.out.println(new Solution().multiply("123","456"));
  }

  static class Solution {
    public String multiply(String num1, String num2) {
      String res = "0";
      for (int i = num2.length() - 1; i >= 0; i--) {
        // 6
        int n1 = num2.charAt(i) - '0';
        String temp = "0";

        for (int j = num1.length() - 1; j >= 0; j--) {
          int n2 = num1.charAt(j) - '0';
          temp = add(temp, convert(String.valueOf(n1 * n2), num1.length() - 1 - j));
        }
        res = add(res, convert(temp, num2.length() - 1 - i));
      }
      return res;
    }

    private String convert(String val, int coefficient) {
      StringBuilder sb = new StringBuilder();
      sb.append(val);
      for (int i = 0; i < coefficient; i++) {
        sb.append("0");
      }
      return sb.toString();
    }

    private String add(String num1, String num2) {
      StringBuilder sb = new StringBuilder();
      int i = 0;

      int carry = 0;
      while (i < num1.length() || i < num2.length()) {
        int temp = 0;
        if (i < num1.length()) {
          temp += num1.charAt(num1.length() - i - 1) - '0';
        }
        if (i < num2.length()) {
          temp += num2.charAt(num2.length() - i - 1) - '0';
        }

        temp += carry;

        if (temp >= 10) {
          carry = 1;
        } else {
          carry = 0;
        }
        sb.append(temp % 10);
        i++;
      }

      if (carry > 0) {
        sb.append(1);
      }
      return sb.reverse().toString();
    }
  }
}
