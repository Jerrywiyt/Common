package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/** 题型：约瑟夫环，每次删掉第n个数，然后求最后剩余的树。 */
public class Question_interview62 {
  public static void main(String[] args) {
    test(5, 3);
  }

  /** 时间复杂度:O(mn) */
  public static int test(int n, int m) {
    List<Integer> circle = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      circle.add(i);
    }
    int index = 0;
    while (circle.size() > 1) {
      index = (index + m - 1) % circle.size();
      circle.remove(index);
    }
    return circle.get(0);
  }

  /** f(n,m) = (f(n-1,m)+3)%n */
  public static int test2(int n, int m) {
    int temp = 0;
    for (int i = 2; i <= n; i++) {
      temp = (temp + m) % i;
    }
    return temp;
  }
}
