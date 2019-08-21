package com.lujunyu.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import lombok.Data;

public class Question2 {

    public static void main(String args[]){
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        System.out.println(JSON.toJSONString(new Question2().addTwoNumbers(l1,l2)));
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode t1 = l1;
        ListNode t2 = l2;
        ListNode res = new ListNode(0);
        ListNode cur = res;
        int h = 0;
        while (t1 != null || t2 != null) {
            int val = (t1 != null ? t1.val : 0) + (t2 != null ? t2.val : 0);
            val = val + h;
            if (val >= 10) {
                val = val - 10;
                h = 1;
            }else{
                h = 0;
            }
            cur.next = new ListNode(val);
            cur = cur.next;
            t1 = t1 != null ? t1.next : null;
            t2 = t2 != null ? t2.next : null;
        }

        if (h > 0) {
            cur.next = new ListNode(h);
        }
        return res.next;
    }


    @Data
    private static class ListNode {
        private int val;
        private ListNode next;

        private ListNode(int x) {
            val = x;
        }
    }
}
