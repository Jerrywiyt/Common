package com.lujunyu.algorithm.leetcode;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 你现在手里有一份大小为 N x N 的「地图」（网格） grid，上面的每个「区域」（单元格）都用 0 和 1 标记好了。其中 0 代表海洋，1
 * 代表陆地，请你找出一个海洋区域，这个海洋区域到离它最近的陆地区域的距离是最大的。
 *
 * <p>我们这里说的距离是「曼哈顿距离」（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。
 *
 * <p>如果我们的地图上只有陆地或者海洋，请返回 -1。
 *
 * <p>示例 1：
 *
 * <p>输入：[[1,0,1],[0,0,0],[1,0,1]] 输出：2 解释： 海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。 示例 2：
 *
 * <p>输入：[[1,0,0],[0,0,0],[0,0,0]] 输出：4 解释： 海洋区域 (2, 2) 和所有陆地区域之间的距离都达到最大，最大距离为 4。
 *
 * <p>提示：
 *
 * <p>1 <= grid.length == grid[0].length <= 100 grid[i][j] 不是 0 就是 1
 */
public class Question1162 {

  public static void main(String[] args) {
    int res =
        new Solution4()
            .maxDistance(
                new int[][] {
                  {0, 0, 1, 1, 1},
                  {0, 1, 1, 0, 0},
                  {0, 0, 1, 1, 0},
                  {1, 0, 0, 0, 0},
                  {1, 1, 0, 0, 1}
                });
    System.out.println(res);
  }

  // 暴力法。
  static class Solution {
    public int maxDistance(int[][] grid) {
      int max = -1;
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          if (grid[i][j] == 0) {
            max = Math.max(findNearestLandDistance(i, j, grid), max);
          }
        }
      }
      return max;
    }

    // 宽度搜索。
    // 此中是通过找规律实现的。
    int findNearestLandDistance(int i, int j, int[][] grid) {
      int s = 1;
      boolean hasNext = true;
      while (hasNext) {
        hasNext = false;
        for (int k = -1 * s; k <= s; k++) {
          int y = j + k;
          int x1 = i + s - Math.abs(k);
          int x2 = i - (s - Math.abs(k));

          if (y >= 0 && y < grid.length) {
            if (x1 >= 0 && x1 < grid.length) {
              if (grid[x1][y] == 1) {
                return s;
              }
              hasNext = true;
            }
            if (x2 >= 0 && x2 < grid.length) {
              if (grid[x2][y] == 1) {
                return s;
              }
              hasNext = true;
            }
          }
        }
        s++;
      }
      return -1;
    }
  }

  static class Solution1 {
    public int maxDistance(int[][] grid) {
      int max = -1;
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          if (grid[i][j] == 0) {
            max = Math.max(findNearestLandDistance(i, j, grid), max);
          }
        }
      }
      return max;
    }

    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};

    /**
     * 无向图的BFS： 1. 需要一个队列表示要前进的路 2. 然后需要明确前进的方向 3. 需要一个数组用于记录已经走过的路。
     *
     * <p>x,y x+1,y x-1,y x,y+1 x,y-1
     */
    private int findNearestLandDistance(int i, int j, int[][] grid) {
      boolean[][] visit = new boolean[grid.length][grid.length];

      Queue<Tuple> queue = new LinkedList<>();
      queue.add(new Tuple(i, j, 0));

      while (!queue.isEmpty()) {
        Tuple tuple = queue.poll();
        for (int k = 0; k < 4; k++) {
          int x = tuple.x + dx[k];
          int y = tuple.y + dy[k];
          if (x >= 0 && x < grid.length && y >= 0 && y < grid.length && !visit[x][y]) {
            if (grid[x][y] == 1) {
              return tuple.step + 1;
            } else {
              queue.offer(new Tuple(x, y, tuple.step + 1));
            }
            visit[x][y] = true;
          }
        }
      }
      return -1;
    }

    private static class Tuple {
      int x;
      int y;
      int step;

      public Tuple(int x, int y, int step) {
        this.x = x;
        this.y = y;
        this.step = step;
      }
    }
  }

  /** 转换思路：单源最短路径问题转换为多源最短路劲问题。 */
  static class Solution3 {
    public int maxDistance(int[][] grid) {
      int[][] res = new int[grid.length][grid.length];
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          res[i][j] = -1;
        }
      }
      boolean[][] visit = new boolean[grid.length][grid.length];

      int[] dx = {1, -1, 0, 0};
      int[] dy = {0, 0, 1, -1};

      // 由于超级源点与大陆的距离都是0，所以直接跳到了大陆。
      PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          if (grid[i][j] == 1) {
            priorityQueue.add(new Node(i, j, 0));
          }
        }
      }

      while (!priorityQueue.isEmpty()) {
        Node node = priorityQueue.poll();
        if (!visit[node.i][node.j]) {
          if (grid[node.i][node.j] == 0) {
            visit[node.i][node.j] = true;
            res[node.i][node.j] = node.d;
          }
          for (int i = 0; i < 4; i++) {
            int x = node.i + dx[i];
            int y = node.j + dy[i];

            if (x >= 0
                && x < grid.length
                && y >= 0
                && y < grid.length
                && grid[x][y] == 0
                && !visit[x][y]) {
              if (res[x][y] == -1 || node.d + 1 < res[x][y]) {
                priorityQueue.add(new Node(x, y, node.d + 1));
              }
            }
          }
        }
      }

      int max = -1;
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          if (res[i][j] > max) {
            max = res[i][j];
          }
        }
      }
      return max;
    }

    class Node implements Comparable<Node> {
      int i;
      int j;
      int d;

      public Node(int i, int j, int d) {
        this.i = i;
        this.j = j;
        this.d = d;
      }

      @Override
      public int compareTo(Node o) {
        return this.d - o.d;
      }
    }
  }

  /**
   * 利用动态规划实现。
   *
   * <p>f(x,y) 表示最近的陆地距离。
   *
   * <p>f(x,y) = min{f(x-1,y)+1,f(x,y-1)+1}
   */
  static class Solution4 {
    public int maxDistance(int[][] grid) {
      int[][] f = new int[grid.length][grid.length];

      //
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          if (grid[i][j] == 1) {
            f[i][j] = 0;
          } else {
            f[i][j] = Integer.MAX_VALUE - 1;
          }
        }
      }

      // 左上到右下。
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid.length; j++) {
          if (i - 1 >= 0) {
            f[i][j] = Math.min(f[i - 1][j] + 1, f[i][j]);
          }
          if (j - 1 >= 0) {
            f[i][j] = Math.min(f[i][j], f[i][j - 1] + 1);
          }
        }
      }

      // 右下，左上
      for (int i = grid.length - 1; i >= 0; i--) {
        for (int j = grid.length - 1; j >= 0; j--) {
          if (i + 1 < grid.length) {
            f[i][j] = Math.min(f[i][j], f[i + 1][j] + 1);
          }

          if (j + 1 < grid.length) {
            f[i][j] = Math.min(f[i][j], f[i][j + 1] + 1);
          }
        }
      }

      int max = -1;

      for (int[] ints : f) {
        for (int j = 0; j < grid.length; j++) {
          if (ints[j] != 0 && ints[j] != Integer.MAX_VALUE - 1) {
            if (ints[j] > max) {
              max = ints[j];
            }
          }
        }
      }
      return max;
    }
  }
}
