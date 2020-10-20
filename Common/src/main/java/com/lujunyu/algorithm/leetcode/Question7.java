package com.lujunyu.algorithm.leetcode;

import com.lujunyu.utils.BitUtil;

/**
 * Given a 32-bit signed integer, reverse digits of an integer. 这道题考察的是计算机中的补码运算规则，现在回忆一下补码：
 * 为啥要用补码：计算机发明者想要简化计算机减法运算的复杂度，把减法当成加法来进行计算。比如2-1实际上是计算2+(-1)。如果是这样计算的话，那么负数就需要一种表现形式。
 * 对于任何负数永远对应一个正数相加等于0，如：a+(-a)=0, 对于-a来说，为了能够使a+(-a)=0，需采用补码的方式进行表示，补码：正数取反+1。通过这种表示形式，计算机就
 * 可以忽略减法的运算，只需做普通的加法即可。
 *
 * <p>误区：发生溢出后并不表示值的符号位一定改变，但是表示的数值肯定会改变。
 */
public class Question7 {
  public static void main(String[] args) {
    System.out.println(BitUtil.toBitString(-1));
    System.out.println(BitUtil.toBitString(2));
    System.out.println(BitUtil.toBitString(-2));
    System.out.println(BitUtil.toBitString(Integer.MAX_VALUE * 10));
  }

  private static class Solution {
    public int reverse(int x) {
      int result = 0;

      while (x != 0) {
        int tail = x % 10;
        int newResult = result * 10 + tail;
        if ((newResult - tail) / 10 != result) {
          return 0;
        }
        result = newResult;
        x = x / 10;
      }
      return result;
    }
  }
}
