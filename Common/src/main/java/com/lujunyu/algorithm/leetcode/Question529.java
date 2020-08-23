package com.lujunyu.algorithm.leetcode;

public class Question529 {
  public static void main(String[] args) {

    char[][] param = new char[][]{{'E', 'E', 'E', 'E', 'E'},
      {'E', 'E', 'M', 'E', 'E'},
      {'E', 'E', 'E', 'E', 'E'},
      {'E', 'E', 'E', 'E', 'E'}};


    System.out.println(new Solution().updateBoard(param,new int[]{3,0}));
  }

  static class Solution {
    int dx[] = new int[] {1, 0, 0, -1, 1, -1, 1, -1};
    int dy[] =
        new int[] {
          0, 1, -1, 0, 1, 1, -1, -1,
        };
    boolean visited[][];

    public char[][] updateBoard(char[][] board, int[] click) {
      if (board == null || board.length == 0) {
        return board;
      }

      if (board[click[0]][click[1]] == 'M') {
        board[click[0]][click[1]] = 'X';
        return board;
      }

      visited = new boolean[board.length][board[0].length];

      dfs(board, click);
      return board;
    }

    private void dfs(char[][] board, int[] next) {
      // 访问过，不在访问。
      visited[next[0]][next[1]] = true;
      // 统计周边雷的数量。
      int num = 0;
      for (int i = 0; i < 8; i++) {
        int x = dx[i] + next[0];
        int y = dy[i] + next[1];
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
          if (board[x][y] == 'M') {
            num++;
          }
        }
      }
      if (num == 0) {
        board[next[0]][next[1]] = 'B';
        // 如果为B则继续往下传递。
        for (int i = 0; i < 8; i++) {
          int x = dx[i] + next[0];
          int y = dy[i] + next[1];
          if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
            if (!visited[x][y]) {
              dfs(board, new int[] {x, y});
            }
          }
        }
      } else {
        board[next[0]][next[1]] = String.valueOf(num).charAt(0);
      }
    }
  }
}
