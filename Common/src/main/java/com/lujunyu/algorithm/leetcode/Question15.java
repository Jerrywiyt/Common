package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question15 {
  public static void main(String[] args) {
    System.out.println(new Solution().threeSum(new int[]{-1,0,1,0}));
  }

  static class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
      List<List<Integer>> list = new ArrayList<>();
      if (nums == null || nums.length == 0) {
        return list;
      }

      Arrays.sort(nums);

      for (int i = 0; i < nums.length; i++) {
        // 去重
        if (i > 0 && nums[i] == nums[i - 1]) {
          continue;
        }
        int left = i + 1;
        int right = nums.length - 1;

        while (left < right) {
          if (left > i + 1 && nums[left] == nums[left - 1]) {
            left++;
            continue;
          }
          if (nums[i] + nums[left] + nums[right] > 0) {
            right--;
          } else if (nums[i] + nums[left] + nums[right] < 0) {
            left++;
          } else {
            List<Integer> temp = new ArrayList<>();
            temp.add(nums[i]);
            temp.add(nums[left]);
            temp.add(nums[right]);
            list.add(temp);
            left++;
          }
        }
      }
      return list;
    }
  }
}
