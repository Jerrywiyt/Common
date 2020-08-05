package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Question491 {

  public static void main(String[] args) {
    //
    System.out.println(new Solution().findSubsequences(new int[] {4, 6, 7, 7}));
  }

  static class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
      boolean used[] = new boolean[nums.length];
      List<List<Integer>> res = new ArrayList<>();

      dfs(nums, used, res, new ArrayList<Integer>(), 0);
      return res;
    }

    private void dfs(
        int[] nums, boolean[] used, List<List<Integer>> res, List<Integer> line, int offset) {
      if (line.size() >= 2) {
        res.add(new ArrayList<>(line));
      }
      if (offset == nums.length) {
        return;
      }

      // 用于保证同样不能出现相同的数字。
      Set<Integer> selected = new HashSet<>();

      // 每一次迭代，相当于给offset那个位置选值。
      for (int i = offset; i < nums.length; i++) {
        if (!used[i]
            && !selected.contains(nums[i])
            && (line.isEmpty() || nums[i] >= line.get(line.size() - 1))) {
          line.add(nums[i]);
          used[i] = true;
          selected.add(nums[i]);
          //
          dfs(nums, used, res, line, i + 1);
          // 恢复现场
          used[i] = false;
          line.remove(line.size() - 1);
        }
      }
    }
  }
}
