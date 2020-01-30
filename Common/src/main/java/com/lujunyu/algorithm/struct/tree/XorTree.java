package com.lujunyu.algorithm.struct.tree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 1.给定一棵有 N （1 < N < 500000）个节点的树，定义两点之间的异或距离为这两个点之间的路径上的边的长度 L
 *
 * <p>（ 0 < L < 500000）的异或值，问有多少点对的异或距离等于 K （0 < K < 500000）。
 *
 * <p>2.给定一棵有 N （1 < N < 500000）个节点的树，定义两点之间的异或距离为这两个点之间的路径上的边的长度 L
 *
 * <p>（0 < L < 2 的31次方 -1）的异或值，问有多少点对的异或距离小于 K（0 < K < 2 的31次方 -1）。 ————————————————
 */
public class XorTree {
  public static void main(String[] args) {
    BinaryTree<Integer> root = new BinaryTree<>();
    BinaryTree<Integer> l11 = new BinaryTree<>("l11", 10, null, null);
    BinaryTree<Integer> l12 = new BinaryTree<>("l12", 8, null, null);
    BinaryTree<Integer> l21 = new BinaryTree<>("l21", 7, null, null);
    BinaryTree<Integer> l22 = new BinaryTree<>("l22", 4, null, null);

    root.setLeft(l11);
    root.setRight(l12);

    l11.setLeft(l21);
    l11.setRight(l22);

    System.out.println(findTwoNodeLenEqualK(root, 2));
  }

  /**
   * 求解两个节点对异或距离等于k的数据。 a / \ b c / \ e f xor(e,f) = be^bf = (ab^be)^(ab^bf) = xor(a,e)^xor(a,f)
   *
   * @param binaryTree
   * @param k
   * @return
   */
  static int findTwoNodeLenEqualK(BinaryTree<Integer> binaryTree, int k) {
    Map<BinaryTree<Integer>, Integer> c = Maps.newLinkedHashMap();
    Map<Integer, List<BinaryTree<Integer>>> map = findAll(binaryTree, c);
    System.out.println(c);
    System.out.println(map);
    int res = 0;

    for (Map.Entry<BinaryTree<Integer>, Integer> entry : c.entrySet()) {
      int i = entry.getValue() ^ k;
      if (map.containsKey(i)) {
        List<BinaryTree<Integer>> binaryTrees = map.get(i);
        binaryTrees.removeIf(b -> b.equals(entry.getKey()));
        res += binaryTrees.size();
      }
    }
    return res / 2;
  }

  private static Map<Integer, List<BinaryTree<Integer>>> findAll(
      BinaryTree<Integer> binaryTree, Map<BinaryTree<Integer>, Integer> c) {
    Map<Integer, List<BinaryTree<Integer>>> map = Maps.newHashMap();
    LinkedList<BinaryTree<Integer>> queue = new LinkedList<>();
    queue.addLast(binaryTree);
    while (!queue.isEmpty()) {
      BinaryTree<Integer> node = queue.removeFirst();
      int xor_node = Optional.ofNullable(c.get(node)).orElse(0);
      if (node.getLeft() != null) {
        int xor_left = node.getLeft().getElement() ^ xor_node;
        put(map, xor_left, node.getLeft());
        c.put(node.getLeft(), xor_left);
        queue.addLast(node.getLeft());
      }
      if (node.getRight() != null) {
        int xor_right = node.getRight().getElement() ^ xor_node;
        put(map, xor_right, node.getRight());
        c.put(node.getRight(), xor_right);
        queue.addLast(node.getRight());
      }
    }
    return map;
  }

  private static void put(
      Map<Integer, List<BinaryTree<Integer>>> map, int xor_left, BinaryTree<Integer> left) {
    if (map.containsKey(xor_left)) {
      map.get(xor_left).add(left);
    } else {
      List<BinaryTree<Integer>> temp = Lists.newArrayList();
      temp.add(left);
      map.put(xor_left, temp);
    }
  }
}
