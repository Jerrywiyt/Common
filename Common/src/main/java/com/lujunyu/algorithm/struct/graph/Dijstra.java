package com.lujunyu.algorithm.struct.graph;

import com.alibaba.fastjson.JSON;
import java.util.PriorityQueue;

public class Dijstra {
  public static void main(String[] args) {
    int[][] graph =
        new int[][] {
          {0, 3, 4, 0, 0, 0, 0},
          {0, 0, 5, 6, 10, 0, 0},
          {0, 0, 0, 2, 0, 8, 0},
          {0, 0, 0, 0, 7, 9, 16},
          {0, 0, 0, 0, 0, 0, 12},
          {0, 0, 0, 0, 0, 0, 14},
          {0, 0, 0, 0, 0, 0, 0}
        };

    System.out.println(JSON.toJSONString(new Solution2().singleSuperShortDistance(graph)));
  }

  /** 采用N*N维数组表示图。graph[i][j]==0表示i，j不直接相连。 */
  static class Solution1 {
    public int[] singleSuperShortDistance(int[][] graph) {
      // 表示顶点到各个点的距离，0表示距离还没有得出。
      int s[] = new int[graph.length];
      for (int i = 1; i < s.length; i++) {
        s[i] = Integer.MAX_VALUE;
      }
      // 表示是否已求出最短路径。
      boolean[] visit = new boolean[graph.length];
      visit[0] = true;
      // 表示当前判断的出具有最短路径的点。
      int i = 0;
      while (true) {
        int k = 0;
        int min = Integer.MAX_VALUE;
        for (int j = 0; j < graph.length; j++) {
          // 找出与i相邻的点，同时忽略已经判断出来的点。
          if (graph[i][j] > 0 && !visit[j]) {
            s[j] = Math.min((graph[i][j] + s[i]), s[j]);
            if (s[j] < min) {
              min = s[j];
              k = j;
            }
          }
        }

        // 表示没有找到下一个具有最短路径的点，无法继续迭代了。
        if (k == 0) {
          break;
        }

        visit[k] = true;
        i = k;
      }
      return s;
    }
  }

  /** 采用优先队列，替代不必要的循环。 */
  static class Solution2 {

    public int[] singleSuperShortDistance(int[][] graph) {
      PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
      int[] s = new int[graph.length];
      for (int i = 1; i < graph.length; i++) {
        s[i] = Integer.MAX_VALUE;
      }
      boolean[] visit = new boolean[graph.length];
      priorityQueue.add(new Node(0, 0));

      while (!priorityQueue.isEmpty()) {
        Node node = priorityQueue.poll();
        if (!visit[node.index]) {
          visit[node.index] = true;
          for (int j = 0; j < graph.length; j++) {
            if (graph[node.index][j] > 0 && !visit[j]) {
              s[j] = Math.min(graph[node.index][j] + s[node.index], s[j]);
              priorityQueue.add(new Node(j, s[j]));
            }
          }
        }
      }
      return s;
    }

    class Node implements Comparable<Node> {
      int index;

      public Node(int index, int val) {
        this.index = index;
        this.val = val;
      }

      int val;

      @Override
      public int compareTo(Node o) {
        return this.val - o.val;
      }
    }
  }
}
