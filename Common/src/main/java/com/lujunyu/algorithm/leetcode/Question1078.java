package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Question1078 {
  class Solution {
    public String[] findOcurrences(String text, String first, String second) {
      List<String> res = new ArrayList<>();
      String[] words = text.split(" ");
      for (int i = 0; i < words.length - 2; i++) {
        if (words[i].equals(first) && words[i + 1].equals(second)) {
          res.add(words[i + 2]);
        }
      }
      return res.toArray(new String[0]);
    }
  }
}
