package com.lujunyu.algorithm.struct.tree;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/** TODO 需要重新进行整理，整理为统一的风格，方便以后复习。 */
public class ListTree {

  public static void main(String[] args) {
    BinaryTree<Integer> root = new BinaryTree<>();
    root.setElement(1);
    root.setLeft(
        new BinaryTree<>(new BinaryTree<>(null, null, 4), new BinaryTree<>(null, null, 5), 2));
    root.setRight(
        new BinaryTree<>(new BinaryTree<>(null, null, 6), new BinaryTree<>(null, null, 7), 3));
    ListTree listTree = new ListTree();
    listTree.firstList(root);
    System.out.println("");
    listTree.midList(root);
    System.out.println("");
    listTree.lastList(root);
    System.out.println("");
    listTree.layerList(root);
    System.out.println("");
    System.out.println(JSON.toJSONString(listTree.layerList1(root)));

    System.out.println("fist list by stack:" + listTree.firstListByStack(root));
    System.out.println("mid list by stack :" + listTree.midListByStack(root));
    System.out.println("last list by stack:" + listTree.lastListByStack(root));
  }

  /** 先序 */
  private void firstList(BinaryTree binaryTree) {
    if (binaryTree == null) {
      return;
    }
    System.out.print(binaryTree.getElement() + " ");
    firstList(binaryTree.getLeft());
    firstList(binaryTree.getRight());
  }

  /** 中序 */
  private void midList(BinaryTree binaryTree) {
    if (binaryTree == null) {
      return;
    }
    midList(binaryTree.getLeft());
    System.out.print(binaryTree.getElement() + " ");
    midList(binaryTree.getRight());
  }

  /** 后序 */
  private void lastList(BinaryTree binaryTree) {
    if (binaryTree == null) {
      return;
    }
    lastList(binaryTree.getLeft());
    lastList(binaryTree.getRight());
    System.out.print(binaryTree.getElement() + " ");
  }

  /** 层序遍历法 */
  private void layerList(BinaryTree binaryTree) {
    if (binaryTree == null) {
      return;
    }
    Queue<BinaryTree> queue = new LinkedList<>();
    queue.add(binaryTree);
    while (queue.size() > 0) {
      BinaryTree node = queue.poll();
      System.out.print(node.getElement().toString() + " ");
      if (node.getLeft() != null) {
        queue.add(node.getLeft());
      }
      if (node.getRight() != null) {
        queue.add(node.getRight());
      }
    }
  }

  /** 分层次进行树的遍历 */
  private List<List<Integer>> layerList1(BinaryTree<Integer> root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<List<Integer>> res = new ArrayList<>();
    Queue<BinaryTree<Integer>> queue = new LinkedList<>();
    queue.add(root);
    int len = 0;
    while (queue.size() > 0) {
      len = queue.size();
      List<Integer> temp = new ArrayList<>(len);
      while (len-- > 0) {
        BinaryTree<Integer> node = queue.poll();
        temp.add(node.getElement());
        if (node.getLeft() != null) {
          queue.add(node.getLeft());
        }
        if (node.getRight() != null) {
          queue.add(node.getRight());
        }
      }
      res.add(temp);
    }
    return res;
  }

  /** 通过stack的方式进行前序遍历 */
  public List<Integer> firstListByStack(BinaryTree<Integer> root) {
    if (root == null) {
      return Collections.emptyList();
    }

    List<Integer> list = new ArrayList<>();

    Stack<BinaryTree<Integer>> stack = new Stack<>();

    BinaryTree<Integer> cur = root;

    while (cur != null || !stack.isEmpty()) {
      while (cur != null) {
        stack.push(cur);
        list.add(cur.getElement());
        cur = cur.getLeft();
      }
      cur = stack.pop().getRight();
    }

    return list;
  }

  /**
   * 通过stack进行中序遍历。
   *
   * @param root
   * @return
   */
  public List<Integer> midListByStack(BinaryTree<Integer> root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<Integer> result = new ArrayList<>();

    Stack<BinaryTree<Integer>> stack = new Stack<>();
    BinaryTree<Integer> cur = root;
    while (cur != null || !stack.isEmpty()) {
      while (cur != null) {
        stack.push(cur);
        cur = cur.getLeft();
      }
      BinaryTree<Integer> pop = stack.pop();
      result.add(pop.getElement());
      cur = pop.getRight();
    }

    return result;
  }

  /**
   * 通过stack进行后续遍历。
   *
   * @param root
   * @return
   */
  public List<Integer> lastListByStack(BinaryTree<Integer> root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<Integer> result = new ArrayList<>();

    Stack<BinaryTree<Integer>> stack = new Stack<>();
    stack.push(root);

    BinaryTree<Integer> last = null;

    while (!stack.isEmpty()) {
      // 首先将所有的左节点都压入栈。
      while (stack.peek().getLeft() != null) {
        stack.push(stack.peek().getLeft());
      }

      while (!stack.isEmpty()) {
        if (stack.peek().getRight() == null || stack.peek().getRight() == last) {
          BinaryTree<Integer> pop = stack.pop();
          result.add(pop.getElement());
          last = pop;
        } else if (stack.peek().getRight() != null) {
          stack.push(stack.peek().getRight());
          break;
        }
      }
    }

    return result;
  }

  /** 按照层次输出树。 */
  public static List<List<Integer>> listEveryLayer1(TreeNode root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<List<Integer>> res = new ArrayList<>();
    dfs(root, 0, res);
    return res;
  }

  // 利用DFS实现层序遍历要比BFS效率高。
  private static void dfs(TreeNode root, int level, List<List<Integer>> res) {
    if (root == null) {
      return;
    }
    if (level == res.size()) {
      res.add(new ArrayList<>());
    }
    res.get(level).add(root.val);
    dfs(root.left, level + 1, res);
    dfs(root.right, level + 1, res);
  }

  /** 使用队列实现遍历输出树的每一层。 */
  public List<List<Integer>> listEveryLayer2(TreeNode root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<List<Integer>> res = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int index = 0;
      int size = queue.size();
      List<Integer> temp = new ArrayList<Integer>();
      while (index++ < size) {
        TreeNode cur = queue.poll();
        temp.add(cur.val);
        if (cur.left != null) queue.add(cur.left);
        if (cur.right != null) queue.add(cur.right);
      }
      res.add(temp);
    }
    return res;
  }

  private static class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;
  }
}
