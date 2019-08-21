package com.lujunyu.algorithm.leetcode;

//给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 示例 1：
//
// 输入: "babad"
//输出: "bab"
//注意: "aba" 也是一个有效答案。
//
//
// 示例 2：
//
// 输入: "cbbd"
//输出: "bb"
//
//

class Question5 {
    public String longestPalindrome(String s) {
        if(s==null||s.length()==0){
            throw new IllegalArgumentException("");
        }
        char[] arr = s.toCharArray();

        return find(arr,arr.length);
    }

    private int find(char[] arr, int length) {
        for(int i=0;i<;i++){
        }
    }
}