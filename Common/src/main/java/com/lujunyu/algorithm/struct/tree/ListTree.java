package com.lujunyu.algorithm.struct.tree;

import java.util.LinkedList;

public class ListTree {

  public static void main(String[] args) {
    BinaryTree<String> root = new BinaryTree<>();
    root.setElement("1");
    root.setLeft(
        new BinaryTree<>(
            new BinaryTree<>(null, null, "4"), new BinaryTree<>(null, null, "5"), "2"));
    root.setRight(
        new BinaryTree<>(
            new BinaryTree<>(null, null, "6"), new BinaryTree<>(null, null, "7"), "3"));

    ListTree listTree = new ListTree();
    listTree.firstList(root);
    System.out.println("");
    listTree.midList(root);
    System.out.println("");
    listTree.lastList(root);
    System.out.println("");
    listTree.layerList(root);
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
    LinkedList<BinaryTree> linkedList = new LinkedList<>();

    linkedList.add(binaryTree);
    while (linkedList.size() > 0) {
      BinaryTree first = linkedList.removeFirst();
      System.out.print(first.getElement() + " ");
      if (first.getLeft() != null) {
        linkedList.addLast(first.getLeft());
      }
      if (first.getRight() != null) {
        linkedList.addLast(first.getRight());
      }
    }
  }
}
