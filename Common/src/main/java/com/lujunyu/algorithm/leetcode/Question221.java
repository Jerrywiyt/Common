package com.lujunyu.algorithm.leetcode;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Question221 {
  public static void main(String[] args) throws JsonProcessingException {
    new Solution()
        .maximalSquare(
            new char[][] {
              {'1', '1', '1', '1', '1', '1', '1', '1'},
              {'1', '1', '1', '1', '1', '1', '1', '0'},
              {'1', '1', '1', '1', '1', '1', '1', '0'},
              {'1', '1', '1', '1', '1', '0', '0', '0'},
              {'0', '1', '1', '1', '1', '0', '0', '0'}
            });
  }

  /** 动态规划：dp[i,j] = Math.min(dp[i,j-1],dp[i-1,j],dp[i-1,j-1]) */
  static class Solution1 {
    public int maximalSquare(char[][] matrix) {
      if (matrix == null || matrix.length == 0) {
        return 0;
      }
      int[][] dp = new int[matrix.length][matrix[0].length];
      int max = 0;
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          if (matrix[i][j] == '0') {
            dp[i][j] = 0;
          } else {
            if (i == 0 || j == 0) {
              dp[i][j] = 1;
            } else {
              dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i - 1][j - 1]), dp[i][j - 1]) + 1;
            }
          }
          max = Math.max(max, dp[i][j]);
        }
      }
      return max * max;
    }
  }

  static class Solution {
    public int maximalSquare(char[][] matrix) {
      if (matrix == null || matrix.length == 0) {
        return 0;
      }
      int max = 0;
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          if (matrix[i][j] == '1') {
            int k = 1;
            while (k < Math.min(matrix.length - i, matrix[0].length - j)) {
              boolean isNo1 = true;
              int m = i + k;
              for (int g = 0; g <= k; g++) {
                int n = j + g;
                if (matrix[m][n] == '0') {
                  isNo1 = false;
                }
              }
              int n = j + k;
              for (int g = 0; g <= k; g++) {
                m = i + g;
                if (matrix[m][n] == '0') {
                  isNo1 = false;
                }
              }

              if (isNo1) {
                k++;
              } else {
                break;
              }
            }
            max = Math.max(max, k * k);
          }
        }
      }
      return max;
    }
  }
}
