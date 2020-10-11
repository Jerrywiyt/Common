package com.lujunyu.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Test {

  // 3. 题目：贴墙纸
  // 你是一位装修工，根据设计师的要求给客户的客厅背景墙贴墙纸。
  // 假设背景墙面积为 n x m，装修风格为现代极简风格，需要使用尽可能少的 不同颜色的 正方形 墙纸包 来铺满墙面。
  // 假设正方形墙纸包块的规格不限，边长都是整数。
  // 请你帮设计师计算一下，最少需要用到多少块方形墙纸包？
  // 示例 1：
  // 输入：n = 2, m = 3
  // 输出：3
  // 解释：3 块墙纸包就可以铺满墙面。
  // 2 块 1x1 墙纸包
  // 1 块 2x2 墙纸包
  // 示例 2：
  // 输入：n = 5, m = 8
  // 输出：5
  // 示例 3：
  // 输入：n = 11, m = 13
  // 输出：6
  // 提示：
  // 1 <= n <= 13
  // 1 <= m <= 13
  //

  static class Solution {
    public int paintingPlan(int n, int k) {
      if (k < n) {
        return 0;
      }
      if (k == n * n) {
        return 1;
      }

      int res = 0;

      for (int x = 0; x < n; x++) {
        int t1 = k - n * x;
        int t2 = n - x;

        if (t1 % t2 == 0) {
          res += cal(n, x) * cal(n, t1 / t2);
        }
      }

      return res;
    }

    private int cal(int n, int num) {
      return compute(n) / (compute(num) * compute(n - num));
    }

    public int compute(int number) {
      int result = 1;

      for (int i = number; i > 0; i--) {
        result *= i;
      }
      return result;
    }
  }

  public static void main(String[] args) {
    // [[3,3],[1,-1],[5,1],[11,-1],[11,1],[1],[11,1],[5],[11,-1]]

//    ["BlackBox","open","open","open","close","open"]
//[[2,3],[6,-1],[4,-1],[0,-1],[6],[0,-1]]1
/*    BlackBox blackBox = new BlackBox(3, 3);

    System.out.println(blackBox.open(1, -1));
    System.out.println(blackBox.open(5, 1));
    System.out.println(blackBox.open(11, -1));
    System.out.println(blackBox.open(11, 1));
    blackBox.close(1);
    System.out.println(blackBox.open(11, 1));
    blackBox.close(5);
    System.out.println(blackBox.open(11, -1));*/

    BlackBox blackBox = new BlackBox(2, 3);
    System.out.println(blackBox.open(6,-1));
    System.out.println(blackBox.open(4,-1));
  }

  static class BlackBox {
    int m;
    int n;
    boolean[] open;

    public BlackBox(int n, int m) {
      this.m = m;
      this.n = n;
      open = new boolean[2 * (m + n)];
      init(m, n);
    }

    public int open(int index, int direction) {
      open[index] = true;
      while (true) {
        int[] coordinate = find(index, direction);
        index = index2[coordinate[0]][coordinate[1]];
        if (open[index]) {
          return index;
        } else {
          // 如果是是个角，方向不变。
          if ((coordinate[0] == m && coordinate[1] == 0)
              || (coordinate[0] == 0 && coordinate[1] == 0)
              || (coordinate[0] == m && coordinate[1] == n)
              || (coordinate[0] == 0 && coordinate[1] == n)) {
          } else {
            direction = direction * -1;
          }
        }
      }
    }

    private int[] find(int index, int direction) {
      int x0 = index1[index][0];
      int y0 = index1[index][1];

      int x = 0;
      int y = 0;
      if (y0 == n) {
        if (direction == 1) {
          // 在底边。
          if (x0 > n) {
            y = 0;
            x = x0 - y0;
          } else {
            x = 0;
            y = y0 - x0;
          }
        } else {
          if (m - x0 > n) {
            y = 0;
            x = x0 + y0;
          } else {
            x = m;
            y = x0 + y0 - m;
          }
        }
      } else if (y0 == 0) {
        if (direction == 1) {
          if (m - x0 > n) {
            y = n;
            x = n + y0 - x0;
          } else {
            x = m;
            y = m - x0 + y0;
          }
        } else {
          if (x0 > n) {
            y = n;
            x = n - x0 - y0;
          } else {
            x = 0;
            y = x0 + y0;
          }
        }
      } else if (x0 == n) {
        if (direction == 1) {
          if (y0 > m) {
            x = 0;
            y = y0 - x0;
          } else {
            y = 0;
            x = x0 - y0;
          }
        } else {
          if (n - y0 > m) {
            x = 0;
            y = x0 + y0;
          } else {
            y = n;
            x = x0 + y0 - n;
          }
        }
      } else {
        if (direction == 1) {
          if (n - y0 > m) {
            x = m;
            y = m - x0 + y0;
          } else {
            y = n;
            x = n + x0 - y0;
          }
        } else {
          if (y0 > m) {
            x = m;
            y = x0 + y0 - m;
          } else {
            y = 0;
            x = x0 + y0;
          }
        }
      }
      return new int[] {x, y};
    }

    public void close(int index) {
      open[index] = false;
    }

    int[][] index1;
    int[][] index2;

    public void init(int m, int n) {
      index1 = new int[2 * (m + n)][2];
      index2 = new int[m + 1][n + 1];

      int x = 0;
      int y = n;

      int direct = 1;
      for (int i = 0; i < 2 * (m + n); i++) {
        index1[i] = new int[] {x, y};
        index2[x][y] = i;
        if (direct == 1) {
          x = x + 1;
          if (x == m) {
            direct = 2;
          }
        } else if (direct == 2) {
          y = y - 1;
          if (y == 0) {
            direct = 3;
          }
        } else if (direct == 3) {
          x = x - 1;
          if (x == 0) {
            direct = 4;
          }
        } else {
          y = y + 1;
        }
      }
    }
  }
}
