package com.lujunyu.algorithm.leetcode;

import com.alibaba.fastjson.JSON;

public class Question1111 {
  public static void main(String[] args) {
    System.out.println(JSON.toJSONString(maxDepthAfterSplit("(()())")));
  }

  /**
   * 此道题的思路：左右均分，左边也不要太多，右边也不要太多。通过奇偶数来判断也可以。
   *
   * @param seq
   * @return
   */
  public static int[] maxDepthAfterSplit(String seq) {
    if (seq == null || seq.length() == 0) {
      return new int[0];
    }
    int left = 0;
    int right = 0;
    int[] res = new int[seq.length()];
    for (int i = 0; i < seq.length(); i++) {
      char c = seq.charAt(i);
      if ('(' == c) {
        if (left <= right) {
          left++;
          res[i] = 0;
        } else {
          right++;
          res[i] = 1;
        }
      } else {
        if (left >= right) {
          left--;
          res[i] = 0;
        } else {
          right--;
          res[i] = 1;
        }
      }
    }
    return res;
  }

  private static int[] test(String seq) {
    if (seq == null || seq.length() == 0) {
      return new int[0];
    }
    int[] res = new int[seq.length()];
    for (int i = 0; i < seq.length(); i++) {
      char c = seq.charAt(i);
      res[i] = '(' == c ? i & 1 : (i - 1) & 1;
    }
    return res;
  }
}
