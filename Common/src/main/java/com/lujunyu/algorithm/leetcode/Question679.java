package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Question679 {
  public static void main(String[] args) {
    new Question679().new Solution().judgePoint24(new int[]{1,2,3,4});
  }

  class Solution {
    static final int TARGET = 24;
    static final double EPSILON = 1e-6;
    static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

    public boolean judgePoint24(int[] nums) {
      List<Double> list = new ArrayList<Double>();
      for (int num : nums) {
        list.add((double) num);
      }
      return solve(list);
    }

    private boolean solve(List<Double> list) {
      if (list.isEmpty()) {
        return false;
      }

      if (list.size() == 1) {
        return Math.abs(list.get(0) - TARGET) < EPSILON;
      }
      int len = list.size();
      for (int i = 0; i < len; i++) {
        for (int j = 0; j < len; j++) {
          if (i == j) {
            continue;
          }

          List<Double> list2 = new ArrayList<>();
          for (int k = 0; k < len; k++) {
            if (k != i && k != j) {
              list2.add(list.get(k));
            }
          }

          //
          for (int k = 0; k < 4; k++) {
            if (k == ADD) {
              list2.add(list.get(i) + list.get(j));
            } else if (k == MULTIPLY) {
              list2.add(list.get(i) * list.get(j));
            } else if (k == SUBTRACT) {
              list2.add(list.get(i) - list.get(j));
            } else {
              if (list.get(j) < EPSILON) {
                continue;
              } else {
                list2.add(list.get(i) / list.get(j));
              }
            }

            //
            if (solve(list2)) {
              return true;
            }

            list2.remove(list2.size() - 1);
          }
        }
      }
      return false;
    }
  }
}
