package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question632 {

  /** 滑动窗口。 */
  class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
      List<Pair> list = new ArrayList<>();
      for (int i = 0; i < nums.size(); i++) {
        for (Integer key : nums.get(i)) {
          list.add(new Pair(key, i));
        }
      }

      Collections.sort(list);

      int start = 0;
      int end = 0;

      for(Pair pair : list){

      }
      return null;
    }

    class Pair implements Comparable<Pair> {
      Integer key;
      Integer value;

      Pair(Integer key, Integer value) {
        this.key = key;
        this.value = value;
      }

      @Override
      public int compareTo(Pair o) {
        return this.key - o.key;
      }
    }
  }
}
