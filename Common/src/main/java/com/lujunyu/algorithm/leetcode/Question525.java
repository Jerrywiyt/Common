package com.lujunyu.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个二进制数组, 找到含有相同数量的 0 和 1 的最长连续子数组（的长度）。
 *
 * <p>
 *
 * <p>示例 1:
 *
 * <p>输入: [0,1] 输出: 2 说明: [0, 1] 是具有相同数量0和1的最长连续子数组。 示例 2:
 *
 * <p>输入: [0,1,0] 输出: 2 说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
 *
 * <p>来源：力扣（LeetCode） 链接：https://leetcode-cn.com/problems/contiguous-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Question525 {
  public static void main(String[] args) {
    System.out.println(new Solution().findMaxLength(new int[] {0, 1, 0}));
    System.out.println(new Solution().findMaxLength(new int[] {0, 1, 0, 1, 0, 1}));
    System.out.println(
        new Solution()
            .findMaxLength(
                new int[] {
                  0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1,
                  0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0,
                  1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0,
                  1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1
                }));
  }

  /** 属于技巧类的题。采用一个变量记录count值，如果两个count值相同，则表示两点间的0和1个数相同。 */
  static class Solution {
    public int findMaxLength(int[] nums) {
      Map<Integer, Integer> cache = new HashMap<>();
      cache.put(0, -1);

      int count = 0;
      int max = 0;

      for (int i = 0; i < nums.length; i++) {
        count += nums[i] == 0 ? -1 : 1;

        if (cache.containsKey(count)) {
          max = Math.max(max, i - cache.get(count));
        } else {
          cache.put(count, i);
        }
      }
      return max;
    }
  }
}
