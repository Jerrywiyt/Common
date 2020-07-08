package com.lujunyu.algorithm.leetcode;

import java.util.LinkedList;

/**
 * 实现一个完全二叉树插入器。
 *
 * <p>完全二叉树定义，所有节点的深度不超过1，且优先填充左节点。
 */
public class Question919 {

  public static void main(String[] args) {}

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  /**
   * 思路：</br> 1. 首先想清楚，完全二叉树的结构特点。</br> 2. 新插入的元素的规律：首先找到未完全的节点，然后设置它的右子节点，然后从左到右逐步完善。</br> 3.
   * 采用双端队列保存需要设置子节点的节点，保存的顺序，也就是要设置它们的顺序。</br> 4. 初始化复杂度O(n),插入复杂度O(1)。
   */
  class CBTInserter {
    private TreeNode root;
    // 用于保存包完全包含左右子节点的节点。
    private LinkedList<TreeNode> deque;

    public CBTInserter(TreeNode root) {
      if (root == null) {
        throw new IllegalArgumentException();
      }
      this.root = root;

      deque = new LinkedList<>();

      // BFS
      LinkedList<TreeNode> queue = new LinkedList<>();
      queue.addLast(root);
      while (!queue.isEmpty()) {
        TreeNode head = queue.poll();
        if (head.left == null || head.right == null) {
          deque.addLast(head);
        }
        if (head.left != null) {
          queue.addLast(head.left);
        }
        if (head.right != null) {
          queue.addLast(head.right);
        }
      }
    }

    public int insert(int v) {
      TreeNode treeNode = new TreeNode(v);
      deque.addLast(treeNode);

      // 完善队头的children。
      TreeNode head = deque.peekFirst();
      if (head.left == null) {
        head.left = treeNode;
      } else {
        head.right = treeNode;
        deque.poll();
      }
      return head.val;
    }

    public TreeNode get_root() {
      return root;
    }
  }
}
