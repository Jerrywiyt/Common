package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Question207 {
  public static void main(String[] args) {
    int numCourses = 2;
    int[][] prerequisites = new int[][] {{0, 1}, {1, 0}};
    System.out.println(new Solution2().canFinish(numCourses, prerequisites));
  }

  // BFS
  static class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
      List<Set<Integer>> courses = new ArrayList<>();
      for (int i = 0; i < numCourses; i++) {
        courses.add(new HashSet<>());
      }

      for (int[] edge : prerequisites) {
        courses.get(edge[0]).add(edge[1]);
      }

      Queue<Integer> queue = new LinkedList<>();

      for (int i = 0; i < courses.size(); i++) {
        if (courses.get(i).size() == 0) {
          queue.add(i);
        }
      }
      int visited = 0;
      while (!queue.isEmpty()) {
        visited++;
        Integer canVisit = queue.poll();
        for (int i = 0; i < courses.size(); i++) {
          if (courses.get(i).size() > 0) {
            courses.get(i).remove(canVisit);
            if (courses.get(i).size() == 0) {
              queue.add(i);
            }
          }
        }
      }
      return numCourses == visited;
    }
  }

  // BFS 优化。
  static class Solution1 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
      List<List<Integer>> edges = new ArrayList<>();
      for (int i = 0; i < numCourses; i++) {
        edges.add(new ArrayList<>());
      }
      int[] degrees = new int[numCourses];
      for (int[] prerequisity : prerequisites) {
        edges.get(prerequisity[1]).add(prerequisity[0]);
        degrees[prerequisity[0]]++;
      }

      Queue<Integer> queue = new LinkedList<>();
      for (int i = 0; i < degrees.length; i++) {
        if (degrees[i] == 0) {
          queue.add(i);
        }
      }

      int visited = 0;

      while (!queue.isEmpty()) {
        Integer poll = queue.poll();
        visited++;

        List<Integer> courses = edges.get(poll);
        for (int course : courses) {
          degrees[course]--;
          if (degrees[course] == 0) {
            queue.add(course);
          }
        }
      }
      return visited == numCourses;
    }
  }

  // DFS
  static class Solution2 {
    private List<List<Integer>> edges = new ArrayList<>();
    private boolean valid = true;
    private int[] visit;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
      for (int i = 0; i < numCourses; i++) {
        edges.add(new ArrayList<>());
      }
      for (int[] p : prerequisites) {
        edges.get(p[1]).add(p[0]);
      }

      visit = new int[numCourses];

      for (int i = 0; i < numCourses; i++) {
        // 0表示未搜索，1表示搜索中，2表示搜索完成。
        if (visit[i] == 0) {
          dfs(i);
          if (!valid) {
            return false;
          }
        }
      }
      return valid;
    }

    private void dfs(int i) {
      if (visit[i] == 0) {
        visit[i] = 1;
        for (int c : edges.get(i)) {
          dfs(c);
          if (!valid) {
            return;
          }
        }
      } else if (visit[i] == 1) {
        valid = false;
        return;
      }
      visit[i] = 2;
    }
  }
}
