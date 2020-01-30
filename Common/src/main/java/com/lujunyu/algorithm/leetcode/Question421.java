package com.lujunyu.algorithm.leetcode;

import com.lujunyu.utils.BitUtil;
import java.util.HashSet;
import java.util.Set;

/** 求解一个非空数组中任意两个数异或的最大值。 */
public class Question421 {
  public static void main(String[] args) {
    System.out.println(BitUtil.toBitString(1 << 31));
    System.out.println(BitUtil.toBitString(-1));
    System.out.println(BitUtil.toBitString(-1 >> 31));
    // >>>表示无符号右移
    System.out.println(BitUtil.toBitString(-1 >>> 31));

    System.out.println(new Solution().findMaximumXOR(new int[] {10, 8, 2}));
    System.out.println(new Solution1().findMaximumXOR(new int[] {10, 8, 2}));
    System.out.println(8 ^ 2);
  }

  /** 解法一比较精妙，利用贪婪的思想解决。 从高bit位向低位进行迭代，因为最大值的高bit位为1。 利用了异或的特性：a^b=c => a^b^b=c^b => a=b^c. */
  private static class Solution {
    public int findMaximumXOR(int[] nums) {
      int max = 0, mask = 0;
      // 从左到右，一位一位的判断最大值。
      for (int i = 31; i >= 0; i--) {
        mask |= 1 << i;
        Set<Integer> sets = new HashSet<>(nums.length);
        // 获取i左边的bit位。
        for (int num : nums) {
          sets.add(num & mask);
        }
        // 假设第i位为1，然后看看sets集合中是否有两个数异或等于tmp，如果存在那么max就为tmp。
        int tmp = max | (1 << i);
        for (int num : sets) {
          if (sets.contains(num ^ tmp)) {
            max = tmp;
            break;
          }
        }
      }
      return max;
    }
  }

  /**
   * 解法二：采用字典树来求解。
   *
   * <p>因为整形树固定长度为32，那么就可以使用一个高度为32的字典树来表示所有的整形。
   */
  private static class Solution1 {
    public int findMaximumXOR(int[] nums) {
      // 填充。
      Trie root = new Trie();
      for (int num : nums) {
        Trie curTrie = root;
        for (int i = 31; i >= 0; i--) {
          int tmp = num >> i & 1;
          if (curTrie.children[tmp] == null) {
            curTrie.children[tmp] = new Trie();
          }
          curTrie = curTrie.children[tmp];
        }
      }

      int max = 0;
      for (int num : nums) {
        Trie curTrie = root;
        int t = 0;
        for (int i = 31; i >= 0; i--) {
          int tmp = num >> i & 1;
          if (curTrie.children[tmp ^ 1] != null) {
            t += 1 << i;
            curTrie = curTrie.children[tmp ^ 1];
          } else {
            curTrie = curTrie.children[tmp];
          }
        }
        max = Math.max(max, t);
      }
      return max;
    }

    private static class Trie {
      private Trie[] children;

      public Trie() {
        children = new Trie[2];
      }
    }
  }
}
