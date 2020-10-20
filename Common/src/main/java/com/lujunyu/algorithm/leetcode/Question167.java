package com.lujunyu.algorithm.leetcode;

import com.alibaba.fastjson.JSON;

public class Question167 {
  public static void main(String[] args) {
    System.out.println(JSON.toJSONString(new Solution().twoSum(new int[] {2, 7, 11, 22, 43}, 9)));
  }

  static class Solution {
    public int[] twoSum(int[] numbers, int target) {
      if (numbers == null || numbers.length == 0) {
        return null;
      }
      for (int i = 0; i < numbers.length; i++) {
        int leaf = target - numbers[i];
        int j = binarySearch(numbers, i + 1, numbers.length - 1, leaf);
        if (j > 0) {
          return new int[] {i, j};
        }
      }
      return null;
    }

    private int binarySearch(int[] numbers, int l, int r, int t) {
      if (l > r) {
        return -1;
      }
      int mid = (l + r) / 2;

      if (numbers[mid] == t) {
        return mid;
      } else if (numbers[mid] > t) {
        return binarySearch(numbers, l, mid - 1, t);
      } else {
        return binarySearch(numbers, mid + 1, r, t);
      }
    }
  }
}
