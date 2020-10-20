package com.lujunyu.algorithm.leetcode;

/**
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i,
 * ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains
 * the most water.
 *
 * <p>Note: You may not slant the container and n is at least 2.
 *
 * @author jerry
 */
public class Question11 {
  public static void main(String[] args) {
    System.out.println(maxArea1(new int[] {1, 2, 3, 4}));
  }

  /**
   * Time Limit Exceeded
   *
   * @param height
   * @return
   */
  public static int maxArea(int[] height) {
    int s = 0;
    for (int i = 0; i < height.length; i++) {
      for (int j = i + 1; j < height.length; j++) {
        s = Math.max((j - i) * Math.min(height[i], height[j]), s);
      }
    }
    return s;
  }
  /**
   * 比较好一些的
   *
   * @param height
   * @return
   */
  public static int maxArea1(int[] height) {
    int first = 0;
    int last = height.length - 1;
    int s = 0;
    while (first < last) {
      s = Math.max(Math.min(height[first], height[last]) * (last - first), s);
      if (height[first] < height[last]) {
        first++;
      } else {
        last--;
      }
    }
    return s;
  }
}
