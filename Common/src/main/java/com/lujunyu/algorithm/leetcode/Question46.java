package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/** 给定一个 没有重复 数字的序列，返回其所有可能的全排列。 */
public class Question46 {
  public static void main(String[] args) {
    int[] nums = new int[] {1, 2, 3};
    System.out.println(new Solution2().permute(nums));
  }

  /**
   * 全排列展开就是一棵树，所以可以采用DFS或者BFS。
   *
   * <p>采用DFS思路求解。
   */
  static class Solution {
    public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> res = new ArrayList<>();
      boolean[] used = new boolean[nums.length];

      if (nums.length == 0) {
        return res;
      }

      dfs(nums, 0, used, res, new ArrayList<>());
      return res;
    }

    private void dfs(
        int[] nums, int depth, boolean[] used, List<List<Integer>> res, List<Integer> line) {
      if (depth == nums.length) {
        res.add(new ArrayList<>(line));
        return;
      }

      for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
          used[i] = true;
          line.add(nums[i]);
          dfs(nums, depth + 1, used, res, line);
          // 恢复现场。
          line.remove(line.size() - 1);
          used[i] = false;
        }
      }
    }
  }

  /** 采用BFS进行遍历。 */
  static class Solution1 {
    public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> res = new ArrayList<>();

      if (nums.length == 0) {
        return res;
      }
      List<Integer> list = new ArrayList<>();
      for (int num : nums) {
        list.add(num);
      }

      Queue<Node> queue = new LinkedList<>();
      queue.add(new Node(new ArrayList<>(), new HashSet<>(list)));

      while (!queue.isEmpty()) {
        Node node = queue.poll();

        if (node.val.size() == nums.length) {
          res.add(node.val);
        } else {
          for (int num : node.leaves) {
            List<Integer> temp = new ArrayList<>(node.val);
            temp.add(num);
            Set<Integer> temp1 = new HashSet<>(node.leaves);
            temp1.remove(num);
            queue.add(new Node(temp, temp1));
          }
        }
      }
      return res;
    }

    class Node {
      Node(List<Integer> val, Set<Integer> leaves) {
        this.val = val;
        this.leaves = leaves;
      }

      List<Integer> val;

      Set<Integer> leaves;
    }
  }

  /**
   * 通过交换，减少status变量.
   *
   * 因为全排列的思路是，首先找到第一个位置，所有元素都有可能。
   * 然后第二个位置，需要排除第一个，从剩余的元素中寻找，
   * 第三个，排除前两个，从剩余的元素中寻找。
   *
   * 通过交换的方式，我们不需要额外的状态用于保存哪些元素没有被使用，使代码更加的优雅。
   * */
  static class Solution2 {

    public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> res = new ArrayList<>();
      if (nums.length == 0) {
        return res;
      }
      List<Integer> param = new ArrayList<>();
      for (int num : nums) {
        param.add(num);
      }
      dfs(param, res, 0);
      return res;
    }

    private void dfs(List<Integer> param, List<List<Integer>> res, int offset) {
      if (offset == param.size() - 1) {
        res.add(new ArrayList<>(param));
        return;
      }
      for (int i = offset; i < param.size(); i++) {
        Collections.swap(param, offset, i);
        dfs(param, res, offset + 1);
        Collections.swap(param, i, offset);
      }
    }
  }

  static class Solution3 {

    public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> res = new ArrayList<>();
      if (nums.length == 0) {
        return res;
      }
      List<Integer> ints = Arrays.stream(nums).boxed().collect(Collectors.toList());

      dfs(ints,res,new ArrayList<Integer>());
      return res;
    }

    private void dfs(List<Integer> ints, List<List<Integer>> res, ArrayList<Integer> cur) {
      if(ints.size()==0){
        res.add(new ArrayList<>(cur));
      }
      for (int i = 0; i <ints.size(); i++) {
        cur.add(ints.get(i));
        ArrayList<Integer> integers1 = new ArrayList<>(ints);
        integers1.remove(i);
        dfs(integers1,res,cur);
        cur.remove(cur.size()-1);
      }
    }
  }
}
