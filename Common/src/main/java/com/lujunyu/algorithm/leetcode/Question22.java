package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/** 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 */
public class Question22 {
  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.generateParenthesis(2));
    System.out.println(solution.generateParenthesis(3));
    System.out.println(solution.generateParenthesis(4));
  }

  static class Solution {
    ArrayList[] cache = new ArrayList[100];

    public List<String> generate(int n) {
      if (cache[n] != null) {
        return cache[n];
      }
      ArrayList<String> ans = new ArrayList();
      if (n == 0) {
        ans.add("");
      } else {
        for (int c = 0; c < n; ++c)
          for (String left : generate(c))
            for (String right : generate(n - 1 - c)) ans.add("(" + left + ")" + right);
      }
      cache[n] = ans;
      return ans;
    }

    public List<String> generateParenthesis(int n) {
      return generate(n);
    }
  }

  // 采用DFS解决。
  static class Solution1 {
    List<String> list = new ArrayList<>();

    public List<String> generate(int n) {
      dfs(n, n, "");
      return list;
    }

    private void dfs(int left, int right, String curStr) {
      if (left == 0 || right == 0) {
        list.add(curStr);
        return;
      }
      if (left > 0) {
        dfs(left - 1, right, curStr + "(");
      }

      if (right > left) {
        dfs(left, right - 1, curStr + ")");
      }
    }
  }
}
