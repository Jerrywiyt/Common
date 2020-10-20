package com.lujunyu.algorithm.dp;

import org.junit.Test;

/**
 * 一次只能爬一个或者两个台阶，问n楼有多少种方案。 这道题属于dp里面教科书般的题，属于接触dp后，最开始碰到的题。
 *
 * <p>问题分析： 假设n个台阶，有f(n)种走法。 如果最后走一个台阶，那么前面的台阶一共有f(n-1)种走法。 如果最后走两个台阶，那么前面的台阶一共有f(n-2)种走法。
 *
 * <p>最后f(n)=f(n-1)+f(n-2)，状态转移方程也就出来了。
 *
 * <p>使用自底向上的方法性能最优。
 */
public class 爬楼梯 {
  static final int N = 40;

  @Test
  public void test1() {
    System.out.println(find1(N));
  }

  @Test
  public void test2() {
    System.out.println(find2(N));
  }

  @Test
  public void tes3() {
    System.out.println(find3(N));
  }

  /** 递归。 */
  private static int find1(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must > 0");
    }
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    return find1(n - 1) + find1(n - 2);
  }

  /** 备忘录法实现。 */
  private static int find3(int n) {
    int[] cache = new int[n + 1];
    return find3(cache, n);
  }

  private static int find3(int[] cache, int n) {
    if (cache[n] > 0) {
      return cache[n];
    } else {
      if (n == 1) {
        cache[1] = 1;
        return 1;
      }

      if (n == 2) {
        cache[2] = 2;
        return 2;
      }
      int b = find3(cache, n - 1) + find3(cache, n - 2);
      cache[n] = b;
      return b;
    }
  }

  /** 自低向上查找。 */
  private static int find2(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must > 0");
    }
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }
    int a = 1;
    int b = 2;
    int i = 3;
    while (i <= n) {
      int c = a + b;
      a = b;
      b = c;
      i++;
    }
    return b;
  }
}
