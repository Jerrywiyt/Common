package com.lujunyu.algorithm.leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class Question1288 {
    public static void main(String[] args) {
        System.out.println(test2(new int[][]{{1, 4}, {3, 6}, {2, 8}, {2, 10}}));
    }

    /**
     * O(n2)
     *
     * @param arr
     * @return
     */
    public static int test(int[][] arr) {
        int res = arr.length;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i != j && arr[i][0] >= arr[j][0] && arr[i][1] <= arr[j][1]) {
                    res--;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 先排序，在比较，O(NlogN)
     */
    public static int test2(int[][] arr) {
        Arrays.sort(arr, (o1, o2) -> {
            int res = o1[0] - o2[0];
            if (res == 0) {
                return o2[1] - o1[1];
            }
            return res;
        });
        int res = arr.length;
        int max = arr[0][1];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i][1] <= arr[i - 1][1]) {
                res--;
            }
            if (arr[i][0] > arr[i - 1][0]) {
                max = Math.max(max, arr[i][1]);
            }
        }
        return res;
    }
}
