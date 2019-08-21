package com.lujunyu.algorithm.leetcode;
//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
//
// 示例 1: 
//
// 输入: "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 输入: "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 输入: "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//

import java.util.HashMap;
import java.util.Map;

class Question3 {

    public static void main(String args[]) {
        Question3 question3 = new Question3();
//        System.out.println(question3.lengthOfLongestSubstring("abcabcbb"));
//        System.out.println(question3.lengthOfLongestSubstring("bbbbb"));
        System.out.println(question3.lengthOfLongestSubstring("abba"));
    }

    /**
     * 滑动窗口加上hashmap解题。
     */
    public int lengthOfLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        Map<Character, Integer> cache = new HashMap<>();
        int b = 0;
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            if (cache.containsKey(arr[i])) {
                int l = cache.get(arr[i]);
                if (l >= b) {
                    b = l + 1;
                }
            }
            cache.put(arr[i], i);
            res = Math.max(res, i - b + 1);
        }
        return res;
    }
}