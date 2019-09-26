package com.lujunyu.algorithm.struct.tree;

import java.util.LinkedList;

public class ListTree {
    /**
     * 先序
     */
    private void firstList(BinaryTree binaryTree) {
        if (binaryTree == null) {
            return;
        }
        System.out.println(binaryTree.getElement());
        firstList(binaryTree.getLeft());
        firstList(binaryTree.getRight());
    }

    /**
     * 中序
     */
    private void midList(BinaryTree binaryTree) {
        if (binaryTree == null) {
            return;
        }
        midList(binaryTree.getLeft());
        System.out.println(binaryTree.getElement());
        midList(binaryTree.getRight());
    }

    /**
     * 后序
     */
    private void lastList(BinaryTree binaryTree) {
        if (binaryTree == null) {
            return;
        }
        lastList(binaryTree.getLeft());
        lastList(binaryTree.getRight());
        System.out.println(binaryTree.getElement());
    }

    /**
     * 层序遍历法
     */
    private void layerList(BinaryTree binaryTree) {
        if (binaryTree == null) {
            return;
        }
        LinkedList<BinaryTree> linkedList = new LinkedList<>();

        linkedList.addLast(binaryTree);
        while (linkedList.size() > 0) {
            BinaryTree first = linkedList.removeFirst();
            System.out.println(binaryTree.getElement());
            if (first.getLeft() != null) {
                linkedList.addLast(first.getLeft());
            }
            if (first.getRight() != null) {
                linkedList.addLast(first.getRight());
            }
        }
    }
}
