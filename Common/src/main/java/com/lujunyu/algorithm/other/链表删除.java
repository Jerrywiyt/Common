package com.lujunyu.algorithm.other;

import lombok.AllArgsConstructor;

/** 1->2->2->3->4 */
public class 链表删除 {
  public static void main(String[] args) {
    ListNode l1 = new ListNode(2, null);
    ListNode l2 = new ListNode(2, null);
    ListNode l3 = new ListNode(2, null);
    ListNode l4 = new ListNode(2, null);
    ListNode l5 = new ListNode(2, null);
    l1.next = l2;
    l2.next = l3;
    l3.next = l4;
    l4.next = l5;

    ListNode del = del(l1);
    print(del);
  }

  private static void print(ListNode listNode) {
    while (listNode != null) {
      System.out.println(listNode.val);
      listNode = listNode.next;
    }
  }

  private static ListNode del(ListNode listNode) {
    if (listNode == null) {
      return null;
    }
    ListNode cur = listNode;
    ListNode head = null;
    ListNode lastNode = null;
    while (cur != null) {
      cur = findFirst(cur);
      if (head == null) {
        head = cur;
        lastNode = head;
      } else {
        lastNode.next = cur;
        lastNode = cur;
      }
      if (cur == null) {
        break;
      }
      cur = cur.next;
    }
    return head;
  }

  private static ListNode findFirst(ListNode cur) {
    ListNode first = cur;
    boolean same = false;
    while (cur.next != null) {
      cur = cur.next;
      if (first.val != cur.val) {
        break;
      } else {
        same = true;
      }
    }
    if (same) {
      if (cur.next == null) {
        return null;
      }
      return cur;
    } else {
      return first;
    }
  }

  @AllArgsConstructor
  private static class ListNode {
    private int val;
    private ListNode next;
  }
}
