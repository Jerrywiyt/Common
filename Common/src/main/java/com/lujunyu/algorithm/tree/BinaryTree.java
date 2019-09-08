package com.lujunyu.algorithm.tree;

import lombok.Data;

/**
 * 二叉树
 */
@Data
public class BinaryTree<T> {
    private T element;
    private BinaryTree<T> left;
    private BinaryTree<T> right;
}
