package com.lujunyu.algorithm.struct.tree;

import com.alibaba.fastjson.JSON;
import javafx.scene.control.ListCell;

import java.util.*;

public class ListTree {

    public static void main(String[] args) {
        BinaryTree<Integer> root = new BinaryTree<>();
        root.setElement(1);
        root.setLeft(
                new BinaryTree<>(
                        new BinaryTree<>(null, null, 4), new BinaryTree<>(null, null, 5), 2));
        root.setRight(
                new BinaryTree<>(
                        new BinaryTree<>(null, null, 6), new BinaryTree<>(null, null, 7), 3));
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
    }

    /**
     * 先序
     */
    private void firstList(BinaryTree binaryTree) {
        if (binaryTree == null) {
            return;
        }
        System.out.print(binaryTree.getElement() + " ");
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
        System.out.print(binaryTree.getElement() + " ");
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
        System.out.print(binaryTree.getElement() + " ");
    }

    /**
     * 层序遍历法
     */
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

    /**
     * 分层次进行树的遍历
     */
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

}
