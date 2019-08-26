package com.lujunyu.algorithm.leetcode;

//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
//
// 示例:
//
// 输入: [-2,1,-3,4,-1,2,1,-5,4],
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
//
//
// 进阶:
//
// 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
//

class Question53 {

    /**
     * 解题思路：本题属于最优解问题，一般采用动态规划的思想。
     *
     * 解决动态规划问题的最核心两个步骤：状态、状态转移方程。
     *
     * 这道题dp[i]的定义不能是数组i的最大和，因为这样定义不出状态转移方程。
     *
     * dp[i]应该表示以 nums[i] 结尾的连续子数组的最大和。
     *
     * 状态转移方程：
     * dp[i]= dp[i−1]+nums[i], if dp[i−1]≥0
     *        nums[i],         if dp[i−1]<0
     *
     * 而结果去 max{dp[1],dp[2],...dp[i-1],dp[i]}
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("");
        }
        int res = nums[0];
        int sum = 0;
        for (int c : nums) {
            if (sum > 0) {
                sum += c;
            } else {
                sum = c;
            }
            res = Math.max(sum, res);
        }
        return res;
    }
}