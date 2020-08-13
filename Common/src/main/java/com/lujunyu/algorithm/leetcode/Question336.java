package com.lujunyu.algorithm.leetcode;

import java.util.*;

/**
 * 给定一组唯一的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
 *
 * <p>解题思路： 空间换时间的转换，利用字典存储已有的字符串。
 *
 * <p>对单个词的判断主要分为四种情况。 1. 如果自己本身是回文，那么回文和空字符串随意组合都是回文 2. 如果自己本身不是回文，但是自己的逆序在字典中存在，那么可以和存在的结果组成回文 3.
 * 自己的前半部分是回文，后半部分不是。那么后半部分的逆序如果在字典中存在，也能够组成回文。 4. 自己的后半部分是回文，前半部分不是。那么前半部分的逆序如果在字典中存在，也能够组成回文。
 */
public class Question336 {

  public static void main(String args[]) {
    System.out.println(
        new Solution().palindromePairs(new String[] {"abcd", "dcba", "lls", "s", "sssll"}));
  }

  public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> result = new ArrayList<>();
    Map<String, Integer> c = new HashMap<>();
    for (int i = 0; i < words.length; i++) {
      c.put(words[i], i);
    }
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      if (word != null && word.length() > 0) {
        // 1.自身是回文
        if (isPalindrome(word)) {
          if (c.containsKey("")) {
            result.add(Arrays.asList(c.get(""), i));
            result.add(Arrays.asList(i, c.get("")));
          }
        } else {
          String reverse = new StringBuffer().append(word).reverse().toString();
          if (c.containsKey(reverse)) {
            result.add(Arrays.asList(i, c.get(reverse)));
          }
        }

        // 前半部分是回文
        int left = 1;
        while (left < word.length()) {
          String substring = word.substring(0, left);
          if (isPalindrome(substring)) {
            String s = new StringBuilder().append(word, left, word.length()).reverse().toString();
            if (c.containsKey(s)) {
              if (c.get(s) != i) {
                result.add(Arrays.asList(c.get(s), i));
              }
            }
          }
          left++;
        }
        // 后半部分是回文
        int right = 1;
        int len = word.length();
        while (right < word.length()) {
          String substring = word.substring(right, len);
          if (isPalindrome(substring)) {
            String s = new StringBuilder().append(word, 0, right).reverse().toString();
            if (c.containsKey(s)) {
              if (c.get(s) != i) {
                result.add(Arrays.asList(i, c.get(s)));
              }
            }
          }
          right++;
        }
      }
    }
    return result;
  }

  private boolean isPalindrome(String word) {
    int len = word.length();
    for (int i = 0; i < len / 2; i++) {
      if (word.charAt(i) != word.charAt(len - i - 1)) {
        return false;
      }
    }
    return true;
  }

  static class Solution {

    /** 按照四种情况逐个分析 */
    public List<List<Integer>> palindromePairs(String[] words) {
      Map<String, Integer> all = new HashMap<>();
      for (int i = 0; i < words.length; i++) {
        all.put(words[i], i);
      }
      List<List<Integer>> result = new ArrayList<>();

      // 1. 如果自身是回文且数组中包含空字符串。
      if (all.containsKey("")) {
        int i = all.get("");
        for (String word : words) {
          if (!word.equals("") && isPalindrome(word)) {
            int j = all.get(word);
            add(i, j, true, result);
          }
        }
      }

      // 2. 如果自己翻转后在数组中，需注意翻转后不能是自己。
      for (int i = 0; i < words.length; i++) {
        String reverse = new StringBuilder(words[i]).reverse().toString();
        if (!words[i].equals(reverse) && all.containsKey(reverse)) {
          add(i, all.get(reverse), false, result);
        }
      }

      // 3. 前半段是回文，后半段翻转存在列表中。
      // 4. 后半段是回文，前端翻转存在列表中。
      for (int i = 0; i < words.length; i++) {
        for (int j = 1; j < words[i].length(); j++) {
          String before = words[i].substring(0, j);
          String after = words[i].substring(j);

          if (isPalindrome(before)) {
            String reverse = new StringBuilder(after).reverse().toString();
            if (all.containsKey(reverse)) {
              add(all.get(reverse), i, false, result);
            }
          }

          if (isPalindrome(after)) {
            String reverse = new StringBuilder(before).reverse().toString();
            if (all.containsKey(reverse)) {
              add(i, all.get(reverse), false, result);
            }
          }
        }
      }
      return result;
    }

    private void add(int i, int j, boolean all, List<List<Integer>> result) {
      List<Integer> temp = new ArrayList<>();
      temp.add(i);
      temp.add(j);
      result.add(temp);
      if (all) {
        List<Integer> temp1 = new ArrayList<>();
        temp1.add(j);
        temp1.add(i);
        result.add(temp1);
      }
    }

    private boolean isPalindrome(String word) {
      return word.equals(new StringBuilder(word).reverse().toString());
    }
  }
}
