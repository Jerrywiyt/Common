package com.lujunyu.algorithm.leetcode;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Question332 {

  public static void main(String[] args) {
    System.out.println(
        new Question332().new Solution()
            .findItinerary(
                Lists.newArrayList(
                    Lists.newArrayList("JFK", "KUL"),
                    Lists.newArrayList("JFK", "NRT"),
                    Lists.newArrayList("NRT", "JFK"))));
  }

  class Solution {
    private Map<String, PriorityQueue<String>> map = new HashMap<>();

    private List<String> resList = new LinkedList<>();

    public List<String> findItinerary(List<List<String>> tickets) {
      for (List<String> ticket : tickets) {
        String src = ticket.get(0);
        String dst = ticket.get(1);
        if (!map.containsKey(src)) {
          PriorityQueue<String> pq = new PriorityQueue<>();
          map.put(src, pq);
        }
        map.get(src).add(dst);
      }
      dfs("JFK");
      return resList;
    }

    private void dfs(String src) {
      PriorityQueue<String> pq = map.get(src);
      while (pq != null && !pq.isEmpty()) dfs(pq.poll());
      ((LinkedList<String>) resList).addFirst(src);
    }
  }
}
