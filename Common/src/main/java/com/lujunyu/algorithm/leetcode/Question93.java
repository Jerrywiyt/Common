package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Question93 {

  public static void main(String[] args) {
    System.out.println(new Solution().restoreIpAddresses("010010"));
  }

  static class Solution {
    public List<String> restoreIpAddresses(String s) {
      // BFS
      List<String> result = new ArrayList<>();
      Queue<String> queue = new LinkedList<>();
      queue.add(s);

      while (!queue.isEmpty()) {
        String poll = queue.poll();
        // 不存在则 -1。
        int lastIndex = poll.lastIndexOf(".");
        // 已经分配的.
        int total = poll.split("\\.").length;
        String before = lastIndex == -1 ? "" : poll.substring(0, lastIndex + 1);
        String after = poll.substring(lastIndex + 1);
        if (after.length() > 0) {
          for (int i = 1; i <= 3 && i <= after.length(); i++) {
            String next = after.substring(0, i);
            String left = after.substring(i);
            String nextPoll = before + next + "." + left;
            if (valid(next)) {
              if (total == 3) {
                if (valid(left)) {
                  result.add(nextPoll);
                }
              } else {
                queue.add(nextPoll);
              }
            }
          }
        }
      }
      return result;
    }

    private boolean valid(String s) {
      if (s.length() > 0 && s.length() <= 3) {
        if (s.length() > 1 && s.startsWith("0")) {
          return false;
        }
        return Integer.parseInt(s) <= 255;
      }
      return false;
    }
  }
}
