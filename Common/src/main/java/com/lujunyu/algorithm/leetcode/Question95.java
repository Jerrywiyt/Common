package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question95 {
  public static void main(String[] args) {
    System.out.println(new Solution().generateTrees(3));
  }

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  static class Solution {
    public List<TreeNode> generateTrees(int n) {
      return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int i, int n) {
      if (i > n) {
        return Collections.emptyList();
      }
      List<TreeNode> list = new ArrayList<>();
      for (int j = i; j <= n; j++) {
        List<TreeNode> lefts = generateTrees(i, j - 1);
        List<TreeNode> rights = generateTrees(j + 1, n);
        for (TreeNode left : lefts) {
          for (TreeNode right : rights) {
            TreeNode root = new TreeNode(j, left, right);
            list.add(root);
          }
        }
        if (lefts.isEmpty()) {
          for (TreeNode right : rights) {
            list.add(new TreeNode(j, null, right));
          }
        }
        if (rights.isEmpty()) {
          for (TreeNode left : lefts) {
            list.add(new TreeNode(j, left, null));
          }
        }
        if (lefts.isEmpty() && rights.isEmpty()) {
          list.add(new TreeNode(j, null, null));
        }
      }
      return list;
    }
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    @Override
    public String toString() {
      return "TreeNode{" + "val=" + val + '}';
    }
  }
}
