package com.lujunyu.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Question_interview55 {

  public static void main(String[] args) {
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    System.out.println(isBalanced(root));
  }

  public static boolean isBalanced(TreeNode root) {
    Map<TreeNode, Integer> cache = new HashMap<>();
    return !height(root, cache);
  }

  private static boolean height(TreeNode treeNode, Map<TreeNode, Integer> cache) {
    if (treeNode == null) {
      return false;
    }
    boolean left = height(treeNode.left, cache);
    boolean right = height(treeNode.right, cache);
    if (left || right) {
      return true;
    }
    int leftHeight = cache.getOrDefault(treeNode.left, 0);
    int rightHeight = cache.getOrDefault(treeNode.right, 0);

    // 大于1直接结束。
    if (Math.abs(leftHeight - rightHeight) > 1) {
      return true;
    }

    int height = Math.max(leftHeight, rightHeight) + 1;
    cache.put(treeNode, height);
    return false;
  }

  private static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
