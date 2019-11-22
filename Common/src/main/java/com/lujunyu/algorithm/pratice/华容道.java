package com.lujunyu.algorithm.pratice;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * airbnb现场coding第一题。
 *
 * 下列是一个3*3矩形，包含数字0~8，0可以上下左右滑动。
 * 1|2|3
 * 4|5|6
 * 7|8|0
 *
 * 问给定任意的组合，求解是否可以通过调整0的位置得到上述结果。
 *
 */
public class 华容道 {

    public static void main(String args[]){

    }

    public boolean test(String a) {
        if (a == null || a.length() != 9) {
            throw new IllegalArgumentException("参数错误");
        }

        String right = "123456780";
        if (a.equals(right)) {
            return true;
        }

        Set<String> haveList = new HashSet<>();
        Set<String> set = new HashSet<>();
        HashSet<Integer> leftIndexs = Sets.newHashSet(2, 1, 3, 4, 6, 7);
        HashSet<Integer> rightIndexs = Sets.newHashSet(1, 2, 4, 5, 7, 8);
        set.add(a);

        while (set.size() > 0) {
            Set<String> temp = new HashSet<>();
            for (String s : set) {
                int i = s.indexOf("0");
                if (i - 3 >= 0) {
                    String n1 = swap(s, i - 3, i);
                    if (judge(right, haveList, temp, n1)) {
                        return true;
                    }
                }
                if (i + 3 < 9) {
                    String n1 = swap(s, i, i + 3);
                    if (judge(right, haveList, temp, n1)) {
                        return true;
                    }
                }
                if (leftIndexs.contains(i)) {
                    String n1 = swap(s, i, i + 1);
                    if (judge(right, haveList, temp, n1)) {
                        return true;
                    }
                }
                if (leftIndexs.contains(i)) {
                    String n1 = swap(s, i - 1, i);
                    if (judge(right, haveList, temp, n1)) {
                        return true;
                    }
                }
            }
            set = temp;
        }
        return false;
    }

    private boolean judge(String right, Set<String> haveList, Set<String> temp, String n1) {
        if (n1.equals(right)) {
            return true;
        } else if (!haveList.contains(n1)) {
            haveList.add(n1);
            temp.add(n1);
        }
        return false;
    }

    private String swap(String s, int i, int i1) {
        char c1 = s.charAt(i);
        char c2 = s.charAt(i1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(s, 0, i);
        stringBuilder.append(c2);
        stringBuilder.append(s, i + 1, i1);
        stringBuilder.append(c1);
        stringBuilder.append(s, i1 + 1, s.length());
        return stringBuilder.toString();
    }
}
