package com.lujunyu.algorithm.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class Question114 {
  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);

    root.left = node2;
    root.right = node5;
    node2.left = node3;
    node2.right = node4;
    node5.right = node6;

    new Solution().flatten(root);
  }

  static class Solution {
    public void flatten(TreeNode root) {
      Queue<TreeNode> queue = new LinkedList<>();
      list(root, queue);
      TreeNode cur = queue.poll();
      while (!queue.isEmpty()) {
        cur.right = queue.poll();
        cur.left = null;
        cur = cur.right;
      }
    }

    private void list(TreeNode node, Queue<TreeNode> queue) {
      if (node == null) {
        return;
      }
      queue.add(node);
      list(node.left, queue);
      list(node.right, queue);
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
  }
}
