package com.lujunyu.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** N皇后问题。 */
public class Question51 {

  public static void main(String[] args) {
    System.out.println(new Solution().solveNQueens(4));
  }

  static class Solution {
    public List<List<String>> solveNQueens(int n) {
      List<List<String>> result = new ArrayList<>();

      boolean[][] chessboard = new boolean[n][n];

      backTrace(result, chessboard, new ArrayList<>(), 0, 0);

      return result;
    }

    private void backTrace(
        List<List<String>> result, boolean[][] chessboard, List<int[]> queens, int x, int y) {
      if (queens.size() == chessboard.length) {
        print(chessboard, result);
        return;
      }

      for (int i = x; i < chessboard.length; i++) {
        for (int j = y; j < chessboard.length; j++) {
          if (isValid(queens, i, j)) {
            chessboard[i][j] = true;
            queens.add(new int[] {i, j});
            backTrace(result, chessboard, queens, i, j);
            queens.remove(queens.size() - 1);
            chessboard[i][j] = false;
          }
        }
        y = 0;
      }
    }

    private void print(boolean[][] chessboard, List<List<String>> result) {
      List<String> temp = new ArrayList<>();
      for (boolean[] bb : chessboard) {
        StringBuilder sb = new StringBuilder();
        for (boolean b : bb) {
          if (b) {
            sb.append("Q");
          } else {
            sb.append(".");
          }
        }
        temp.add(sb.toString());
      }
      result.add(temp);
    }

    // 判断新的位置，周围是否已经存在皇后。
    private boolean isValid(List<int[]> queens, int row, int col) {
      for (int[] queen : queens) {
        if (row == queen[0] || col == queen[1]) {
          return false;
        }
        if (Math.abs(queen[0] - row) == Math.abs(queen[1] - col)) {
          return false;
        }
      }
      return true;
    }
  }

  static class Solution1 {
    public List<List<String>> solveNQueens(int n) {
      List<List<String>> result = new ArrayList<>();

      int[] queen = new int[n];

      Arrays.fill(queen, -1);

      Set<Integer> columns = new HashSet<>();
      Set<Integer> diagonal1 = new HashSet<>();
      Set<Integer> diagonal2 = new HashSet<>();

      return result;
    }

    private void backTrace(
        List<List<String>> result,
        int row,
        int n,
        int[] queen,
        Set<Integer> columns,
        Set<Integer> diagonal1,
        Set<Integer> diagonal2) {
      // columns值应该是 0~n-1。
      if (columns.size() == n) {
        result.add(combine(queen));
        return;
      }
      for (int col = 0; col < n; col++) {
        if (columns.contains(col)) {
          continue;
        }
        // 一种斜的序号等于 row + col
        if (diagonal1.contains(row + col)) {
          continue;
        }
        // 另一种斜边序号 n - 1 + row -col.
        if (diagonal2.contains(n - 1 + row - col)) {
          continue;
        }

        queen[row] = col;
        columns.add(col);
        diagonal1.add(row + col);
        diagonal2.add(n - 1 + row - col);

        backTrace(result, row + 1, n, queen, columns, diagonal1, diagonal2);

        columns.remove(col);
        diagonal1.remove(row + col);
        diagonal2.remove(n - 1 + row - col);
      }
    }

    private List<String> combine(int[] queen) {
      List<String> result = new ArrayList<>();
      for (int value : queen) {
        char[] c = new char[queen.length];
        Arrays.fill(c, '.');
        c[value] = 'Q';
        result.add(new String(c));
      }
      return result;
    }
  }
}
