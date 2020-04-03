package com.lujunyu.algorithm.struct.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 二叉树 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinaryTree<T> {
  private String name;
  private T element;
  private BinaryTree<T> left;
  private BinaryTree<T> right;

  public BinaryTree(BinaryTree<T> left, BinaryTree<T> right, T element) {
    this.left = left;
    this.right = right;
    this.element = element;
  }

  public String toString() {
    return name;
  }
}
