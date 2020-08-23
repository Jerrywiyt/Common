package com.lujunyu.algorithm.leetcode;

public class Question6 {
  public static void main(String[] args) {

    System.out.println(new Solution().convert("LEETCODEISHIRING", 3));
  }

  static class Solution {
    public String convert(String s, int numRows) {
      char[][] arr = new char[numRows][s.length()];

      int circle = 2 * numRows - 2;

      for (int i = 0; i < s.length(); i++) {
        int tmp = i % circle;

        int row = tmp < numRows ? tmp : (2 * numRows - tmp - 2);

        int col_base = (numRows - 1) * (i / circle);
        int col = tmp < numRows ? col_base : col_base + tmp - numRows + 1;

        arr[row][col] = s.charAt(i);
      }

      StringBuilder sb = new StringBuilder();
      for (char[] arr1 : arr) {
        for (char c : arr1) {
          if (c != 0) {
            sb.append(c);
          }
        }
      }
      return sb.toString();
    }
  }
}
