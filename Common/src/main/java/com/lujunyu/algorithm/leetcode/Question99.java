package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Question99 {

  public static void main(String[] args) {
    //
    TreeNode root = new TreeNode(new TreeNode(null, new TreeNode(null, null, 2), 3), null, 1);

    new Solution().recoverTree(root);
  }

  static class Solution {
    public void recoverTree(TreeNode root) {
      List<TreeNode> list = new ArrayList<>();

      // 正常的二叉搜索树，中序遍历的结果会是递增的。
      listTree(root, list);

      int left = -1;
      int right = -1;

      for (int i = 0; i < list.size() - 1; i++) {
        if (list.get(i).val > list.get(i + 1).val) {
          if (left > 0) {
            right = i;
          } else {
            left = i;
          }
        }
      }

      //
      if (right < 0) {
        right = left + 1;
      }
      int temp = list.get(left).val;
      list.get(left).val = list.get(right).val;
      list.get(right).val = temp;
    }

    private void listTree(TreeNode node, List<TreeNode> list) {
      if (node == null) {
        return;
      }
      listTree(node.left, list);
      list.add(node);
      listTree(node.right, list);
    }
  }

  @NoArgsConstructor
  @AllArgsConstructor
  static class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;
  }
}
