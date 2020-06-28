package com.lujunyu.algorithm.leetcode;

import java.util.concurrent.atomic.AtomicInteger;

public class Question91 {
  public static void main(String[] args) {
    System.out.println(new Solution1().numDecodings("10002"));

    System.out.println(new Solution1().numDecodings("12"));

    System.out.println(new Solution1().numDecodings("1234"));
    System.out.println(
        new Solution()
            .numDecodings(
                "1787897759966261825913315262377298132516969578441236833255596967132573482281598412163216914566534565"));
  }

  static class Solution1 {
    public int numDecodings(String s) {
      if (s == null || s.startsWith("0")) {
        return 0;
      }
      if (s.length() == 1) {
        return 1;
      }
      if(Integer.parseInt(s.substring(0,2))<=26){
        return numDecodings(s.substring(1)) + numDecodings(s.substring(2));
      }else {
        return numDecodings(s.substring(1));
      }
    }
  }

  static class Solution {
    public int numDecodings(String s) {
      if (s == null) {
        return 0;
      }
      AtomicInteger res = new AtomicInteger(0);
      listTree(s, res);
      return res.get();
    }

    private void listTree(String s, AtomicInteger res) {
      if (s == null || s.equals("") || s.startsWith("0")) {
        return;
      }

      if (s.length() <= 2 && Integer.parseInt(s) <= 26) {
        res.incrementAndGet();
      }

      if (s.length() == 1) {
        return;
      }

      listTree(s.substring(1), res);
      if (Integer.parseInt(s.substring(0, 2)) <= 26) {
        listTree(s.substring(2), res);
      }
    }
  }
}
