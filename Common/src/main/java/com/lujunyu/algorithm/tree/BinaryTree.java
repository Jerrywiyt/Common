package com.lujunyu.algorithm.tree;

/**
 * 二叉树
 * @author jerry
 *
 */
public class BinaryTree {

	private Object element;
	private BinaryTree left;
	private BinaryTree right;
	
	
	
	public static void main(String[] args) {
		BinaryTree left = new BinaryTree("left", new BinaryTree("a", null, null), new BinaryTree("b", null, null));
		BinaryTree right = new BinaryTree("right", new BinaryTree("c", null, null), new BinaryTree("d", null, null));
		BinaryTree root = new BinaryTree("root", left, right);
		
		ListByFirst(root);
		System.out.println();
		ListByCenter(root);
		System.out.println();
		ListByLast(root);
	}

	public BinaryTree(Object element, BinaryTree left, BinaryTree right) {
		super();
		this.element = element;
		this.left = left;
		this.right = right;
	}

	/**
	 * 先序遍历
	 * 
	 * @param t
	 */
	public static void ListByFirst(BinaryTree t) {
		if (t != null) {
			System.out.print(t +" ");
			ListByFirst(t.left);
			ListByFirst(t.right);
		}
	}

	/**
	 * 中序遍历
	 * 
	 * @param t
	 */
	public static void ListByCenter(BinaryTree t) {
		if (t != null) {
			ListByCenter(t.left);
			System.out.print(t + " ");
			ListByCenter(t.right);
		}
	}

	/**
	 * 后序遍历
	 * 
	 * @param t
	 */
	public static void ListByLast(BinaryTree t) {
		if (t != null) {
			ListByLast(t.left);
			ListByLast(t.right);
			System.out.print(t + " ");
		}
	}

	/**
	 * 计算树的深度
	 * 
	 * @param t
	 * @return
	 */
	public static int deep(BinaryTree t) {
		if (t == null) {
			return 0;
		}
		int left = deep(t.left);
		int right = deep(t.right);
		if (left > right) {
			return left + 1;
		} else {
			return right + 1;
		}
	}

	/**
	 * 清空树
	 * 
	 * @param t
	 */
	public static void clear(BinaryTree t) {
		if (t != null) {
			clear(t.left);
			clear(t.right);
			t = null;
		}
	}

	public String toString() {
		return this.element.toString();
	}
}
